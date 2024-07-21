package top.wangzhitao.easy2learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王志涛 2024/7/20
 */

@Configuration
public class AnkiConfig {

    @Value("${anki.server}")
    public String ankiServer;
}
