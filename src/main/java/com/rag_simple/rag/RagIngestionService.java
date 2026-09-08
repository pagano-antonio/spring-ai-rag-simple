package com.rag_simple.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
class RagIngestionService {

    private final VectorStore vectorStore;
    private final TokenTextSplitter textSplitter;

    RagIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.textSplitter = new TokenTextSplitter();
    }

    IngestResponse ingest(IngestRequest request) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        if (request.metadata() != null) {
            metadata.putAll(request.metadata());
        }
        if (request.source() != null && !request.source().isBlank()) {
            metadata.put("source", request.source());
        }

        Document document = new Document(request.text(), metadata);
        List<Document> chunks = textSplitter.apply(List.of(document));
        vectorStore.add(chunks);

        return new IngestResponse(chunks.size());
    }
}
