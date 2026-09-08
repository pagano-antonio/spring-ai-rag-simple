package com.rag_simple.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RagConfig {

    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                        Sei un assistente RAG.
                        Rispondi solo usando il contesto fornito.
                        Se il contesto non contiene la risposta, dillo chiaramente.
                        """)
                .build();
    }
}
