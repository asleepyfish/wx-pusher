package com.asleepyfish.test;

import io.github.asleepyfish.util.OpenAiUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-05 00:24
 * @Description: SpringBoot的Test
 */
@SpringBootTest
public class SpringTest {
    @Test
    public void testCompletion() {
        OpenAiUtils.createCompletion("use c++ write QuickSort").forEach(System.out::println);
    }

    @Test
    public void testChatCompletion() {
        OpenAiUtils.createChatCompletion("Java效率高嘛").forEach(System.out::println);
        OpenAiUtils.createChatCompletion("还有比它更高的吗").forEach(System.out::println);
    }

    @Test
    public void testGenerateImage() {
        OpenAiUtils.createImage("white cat").forEach(System.out::println);
    }
}
