package com.asleepyfish;

import io.github.asleepyfish.annotation.EnableChatGPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author asleepyfish
 */
@SpringBootApplication
@EnableChatGPT
public class WxPusherApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxPusherApplication.class, args);
    }
}
