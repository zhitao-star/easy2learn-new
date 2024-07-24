package top.wangzhitao.easy2learn.controller;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 王志涛 2024/7/19
 */

@RestController("/openai")
@Slf4j
public class OpenAIController {

    private final ChatClient client;

//    @Resource
//    private AnkiService ankiService;
//
//    @Resource
//    private ChatModel chatModel;

    public OpenAIController(ChatClient.Builder client) {
        this.client = client.build();
    }

    @Value("classpath:/data.pdf")
    private Resource systemResource;

    @PostMapping("/question")
    public String question(@RequestBody JSONObject params) {
//        SystemMessage systemMessage = new SystemMessage("Hi");
//        UserMessage userMessage = new UserMessage("Hello");
//        String response = chatModel.call(systemMessage, userMessage);
//        log.info(response);
//        String userText = "Tell me about three famous pirates from the Golden Age of Piracy and why they did. Write at least a sentence for each pirate.";
//        Message userMessage = new UserMessage(userText);
//
//        String systemText = "You are a helpful AI assistant that helps people find information. Your name is {name}. You should reply to the user's request with your name and also in the style of a {voice}.";
//        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
//        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", "wangzhitao", "voice", "dog"));
//
//        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
//
//        String content = client.prompt(prompt).call().content();
//        log.info(content);

//        ChatResponse chatResponse = client.prompt().user("Tell me a joke").call().chatResponse();
//        ChatResponseMetadata metadata = chatResponse.getMetadata();
//        log.info(JSONObject.toJSONString(metadata));
//        List<Generation> results = chatResponse.getResults();
//        log.info(JSONObject.toJSONString(results));

        try (InputStream inputStream = systemResource.getInputStream()) {
            // 处理文件内容
            // 例如，将其转换为字节数组
            byte[] fileContent = inputStream.readAllBytes();
            System.out.println("File content length: " + fileContent.length);
        } catch (IOException e) {
            e.printStackTrace();
        }


        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
        Message message = systemPromptTemplate.createMessage();
        UserMessage userMessage = new UserMessage("请根据这个文档，生成问答对。");
        Prompt prompt = new Prompt(List.of(message, userMessage));
        ChatResponse chatResponse = client.prompt(prompt).call().chatResponse();
        String content = chatResponse.getResult().getOutput().getContent();
        log.info(content);
        return null;
    }
}
