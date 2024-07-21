package top.wangzhitao.easy2learn.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.wangzhitao.easy2learn.service.AnkiService;

import java.util.List;

/**
 * @author 王志涛 2024/7/19
 */

@RestController("/openai")
@Slf4j
public class OpenaiController {

    private final ChatClient client;

    @Resource
    private AnkiService ankiService;

    public OpenaiController(ChatClient.Builder client) {
        this.client = client.build();
    }

    @PostMapping("/question")
    public String question(@RequestBody JSONObject params) {
        ChatResponse chatResponse = client.prompt().user("Tell me a joke").call().chatResponse();
        ChatResponseMetadata metadata = chatResponse.getMetadata();
        log.info(JSONObject.toJSONString(metadata));
        List<Generation> results = chatResponse.getResults();
        log.info(JSONObject.toJSONString(results));
        return null;
    }
}
