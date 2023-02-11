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
    public void chatGPTTest() {
        OpenAiUtils.createCompletion("use c++ write QuickSort").forEach(System.out::println);
    }
}
