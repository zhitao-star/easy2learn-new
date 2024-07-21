package top.wangzhitao.easy2learn.service;

import com.alibaba.fastjson2.JSONArray;
import top.wangzhitao.easy2learn.vo.CardVo;

import java.util.List;
import java.util.Map;

/**
 * @author 王志涛 2024/7/20
 */
public interface AnkiService {
    /**
     * 获取所有的卡片组
     *
     * @return 卡片组
     */
    Map<String, Object> getAllDeck();

    Map<String, JSONArray> findCardIds(List<String> deckNames);

    List<CardVo> cardsInfoByCardIds(List<Long> cardIds);

    String retrieveMediaFile(String mediaName);

}
