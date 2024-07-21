package top.wangzhitao.easy2learn.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wangzhitao.easy2learn.service.DeckService;
import top.wangzhitao.easy2learn.utils.R;

/**
 * @author 王志涛 2024/7/20
 */

@RestController("/deck")
public class DeckController {

    @Resource
    private DeckService deckService;

    // TODO: 2024/7/21 所有操作 增加后置处理器 同步到anki服务器

    @PutMapping("/updateAllDecks")
    public R updateAllDecks() {
        deckService.updateAllDecks();
        return R.ok();
    }

    @PutMapping("/updateAllCards")
    public R updateAllCards() {
        deckService.updateAllCards();
        return R.ok();
    }
}
