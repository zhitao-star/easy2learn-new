package top.wangzhitao.easy2learn.service.impl;

import com.alibaba.fastjson2.JSONArray;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wangzhitao.easy2learn.service.AnkiService;
import top.wangzhitao.easy2learn.utils.AnkiHttpUtils;
import top.wangzhitao.easy2learn.vo.CardVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 王志涛 2024/7/20
 */
@Service
@Slf4j
public class AnkiServiceImpl implements AnkiService {


    @Resource
    private AnkiHttpUtils ankiHttpUtils;


    @Override
    public Map<String, Object> getAllDeck() {
        return ankiHttpUtils.invoke("deckNamesAndIds");
    }

    @Override
    public Map<String, JSONArray> findCardIds(List<String> deckNames) {
        return deckNames.stream().collect(Collectors.toMap(deckName -> deckName, deckName -> {
            Map<String, Object> invoke = ankiHttpUtils.invoke("findCards", Map.of("query", "deck:" + deckName));
            return JSONArray.from(invoke.get("result"));
        }));
    }

    @Override
    public List<CardVo> cardsInfoByCardIds(List<Long> cardIds) {
        return JSONArray.from(ankiHttpUtils.invoke("cardsInfo", Map.of("cards", cardIds)).get("result")).toJavaList(CardVo.class);
    }

    @Override
    public String retrieveMediaFile(String mediaName) {
        return String.valueOf(ankiHttpUtils.invoke("retrieveMediaFile", Map.of("filename", mediaName)).get("result"));
    }
}
