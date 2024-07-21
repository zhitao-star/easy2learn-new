package top.wangzhitao.easy2learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 王志涛 2024/7/19
 */
@SpringBootApplication
@MapperScan("top.wangzhitao.easy2learn.mapper")
@EnableAsync // 允许使用异步方法
public class Easy2learnApplication {
    public static void main(String[] args) {
        SpringApplication.run(Easy2learnApplication.class, args);
    }

}
