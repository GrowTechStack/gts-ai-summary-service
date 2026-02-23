package com.gts.aisummary.global.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Spring AI ChatClient 빈 설정
 */
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    /**
     * Groq API는 extra_body 필드를 지원하지 않으므로 요청 전 제거합니다.
     */
    @Bean
    public RestClientCustomizer groqCompatibilityCustomizer() {
        return builder -> builder.requestInterceptor(new ExtraBodyRemovingInterceptor());
    }

    static class ExtraBodyRemovingInterceptor implements ClientHttpRequestInterceptor {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            if (body.length > 0) {
                try {
                    JsonNode root = objectMapper.readTree(body);
                    if (root instanceof ObjectNode objectNode && objectNode.has("extra_body")) {
                        objectNode.remove("extra_body");
                        body = objectMapper.writeValueAsBytes(root);
                        request.getHeaders().setContentLength(body.length);
                    }
                } catch (Exception ignored) {}
            }
            return execution.execute(request, body);
        }
    }
}
