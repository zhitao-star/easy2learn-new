package top.wangzhitao.easy2learn.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.wangzhitao.easy2learn.config.AnkiConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王志涛 2024/7/20
 */

@Component
@Slf4j
public class AnkiHttpUtils {

    @Resource
    private AnkiConfig ankiConfig;

    public Map<String, Object> invoke(String action) {
        return invoke(action, new HashMap<>());
    }

    public Map<String, Object> invoke(String action, Map<String, Object> params) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("action", action);
            requestBody.put("version", 6);
            requestBody.put("params", params);

            HttpResponse response = HttpRequest.post(ankiConfig.ankiServer)
                    .contentType("application/json")
                    .body(JSONObject.toJSONString(requestBody))
                    .execute();

            String responseBody = response.body();

            JSONObject jsonResponse;

            try {
                jsonResponse = JSONObject.parseObject(responseBody);
            } catch (Exception e) {
                e.printStackTrace();
                JSONObject object = new JSONObject();
                object.put("result", responseBody);
                return object;
            }
            if (jsonResponse.getString("error") != null) {
                throw new RuntimeException(jsonResponse.getString("error"));
            }
            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
