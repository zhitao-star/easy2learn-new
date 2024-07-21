package top.wangzhitao.easy2learn;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.wangzhitao.easy2learn.mapper.CardMapper;
import top.wangzhitao.easy2learn.service.DeckService;
import top.wangzhitao.easy2learn.vo.CardVo;

;import java.util.List;

@SpringBootTest
@Slf4j
class Easy2learnApplicationTests {

    @Resource
    private DeckService deckService;

    @Resource
    private CardMapper cardMapper;

    @Test
    void contextLoads() {
        deckService.updateAllDecks();
    }


    @Test
    void testUpdateAllCards() {
        deckService.updateAllCards();
    }

    @Test
    void testUpdateAllDecks() {
        List<CardVo> cardVos = cardMapper.selectList(null);
        cardVos.forEach(cardVo -> log.info("cardVo: {}", JSONObject.toJSONString(cardVo)));
    }
}
