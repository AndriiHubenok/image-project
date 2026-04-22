package org.example.textmoderationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.ai.openai.api-key=test-key",
        "spring.rabbitmq.listener.simple.auto-startup=false"
})
class TextModerationServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
