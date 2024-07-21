package top.wangzhitao.easy2learn.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wangzhitao.easy2learn.service.AnkiService;
import top.wangzhitao.easy2learn.mapper.CardMapper;
import top.wangzhitao.easy2learn.mapper.DeckMapper;
import top.wangzhitao.easy2learn.service.DeckService;
import top.wangzhitao.easy2learn.service.MediaService;
import top.wangzhitao.easy2learn.vo.CardVo;
import top.wangzhitao.easy2learn.vo.DeckVo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 王志涛 2024/7/20
 */
@Service
@Slf4j
public class DeckServiceImpl extends ServiceImpl<DeckMapper, DeckVo> implements DeckService, IService<DeckVo> {

    @Resource
    private AnkiService ankiService;

    @Resource
    private CardMapper cardMapper;

    @Resource
    private MediaService mediaService;

    @Override
    public void updateAllDecks() {
        ArrayList<DeckVo> deckVos = new ArrayList<>();
        HashMap<String, Long> deckMap = new HashMap<>();
        Map<String, Object> allDeck = ankiService.getAllDeck();
        JSONObject result = JSONObject.from(allDeck.get("result"));
        for (String deckName : result.keySet()) {
            String[] deckArr = deckName.split("::");
            deckMap.put(deckArr[deckArr.length - 1], result.getLong(deckName));
        }
        for (String deckName : result.keySet()) {
            String[] deckArr = deckName.split("::");
            DeckVo deckVo;
            if (deckArr.length == 1) {
                deckVo = new DeckVo(result.getLong(deckName), deckName, -1L, deckName);
            } else {
                deckVo = new DeckVo(result.getLong(deckName), deckArr[deckArr.length - 1], deckMap.get(deckArr[deckArr.length - 2]), deckName);
            }
            deckVos.add(deckVo);
        }
        this.saveOrUpdateBatch(deckVos);
        Set<Long> collect = deckVos.stream().map(DeckVo::getDeckId).collect(Collectors.toSet());
        List<DeckVo> list = this.list();
        List<DeckVo> deleteDecks = list.stream().filter(deckVo -> !collect.contains(deckVo.getDeckId())).collect(Collectors.toList());
        if (!deleteDecks.isEmpty()) {
            this.removeBatchByIds(deleteDecks);
        }
    }

    @Override
    public void updateAllCards() {
        this.updateAllDecks();
        List<DeckVo> vos = this.list();
        Map<String, Long> deckMap = vos.stream().collect(Collectors.toMap(DeckVo::getFillDeckPath, DeckVo::getDeckId));
        Map<String, JSONArray> cardIds = ankiService.findCardIds(vos.stream().map(DeckVo::getFillDeckPath).toList());
        HashSet<String> mediaNames = new HashSet<>();
        String regex = "<img\\s+[^>]*src=\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        for (String deckName : cardIds.keySet()) {
            // TODO: 2024/7/21 采用多线程进行更新
            List<CardVo> cardList = ankiService.cardsInfoByCardIds(cardIds.get(deckName).toList(Long.class)).stream().peek(cardVo -> cardVo.setDeckId(deckMap.get(deckName))).toList();
            cardMapper.insertOrUpdate(cardList);
            cardList.forEach(cardVo -> {
                String answer = cardVo.getAnswer();
                Matcher matcher = pattern.matcher(answer);

                while (matcher.find()) {
                    mediaNames.add(matcher.group(1));
                }
            });
            Set<Long> cardIdsFromApi = cardList.stream().map(CardVo::getCardId).collect(Collectors.toSet());
            List<CardVo> deleteCard = cardMapper.selectList(new QueryWrapper<CardVo>().eq("deck_name", deckName))
                    .stream()
                    .filter(cardVo -> !cardIdsFromApi.contains(cardVo.getCardId())).toList();
            if (!deleteCard.isEmpty()) {
                cardMapper.deleteByIds(deleteCard);
            }
        }
        // TODO: 2024/7/21 优化存入ali-oss
        mediaService.saveMedia(mediaNames);
    }
}
