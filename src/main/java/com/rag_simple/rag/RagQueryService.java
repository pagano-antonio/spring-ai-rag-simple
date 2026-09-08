package com.rag_simple.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RagQueryService {

    private static final String USER_PROMPT = """
            Contesto:
            {context}

            Domanda:
            {question}
            """;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    RagQueryService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    RagAskResponse ask(RagAskRequest request) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(request.question())
                .topK(request.effectiveTopK())
                .build());

        String context = documents.stream()
                .map(Document::getText)
                .reduce((left, right) -> left + "\n\n---\n\n" + right)
                .orElse("Nessun contesto recuperato.");

        String answer = chatClient.prompt()
                .user(user -> user.text(USER_PROMPT)
                        .param("context", context)
                        .param("question", request.question()))
                .call()
                .content();

        return new RagAskResponse(answer, toRetrievedDocuments(documents));
    }

    private List<RagAskResponse.RetrievedDocument> toRetrievedDocuments(List<Document> documents) {
        return documents.stream()
                .map(document -> new RagAskResponse.RetrievedDocument(
                        document.getId(),
                        document.getScore() == null ? 0.0 : document.getScore(),
                        document.getText(),
                        document.getMetadata()))
                .toList();
    }
}
