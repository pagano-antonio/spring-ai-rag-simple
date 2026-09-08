package com.rag_simple.rag;

import java.util.List;
import java.util.Map;

public record RagAskResponse(
        String answer,
        List<RetrievedDocument> documents
) {

    public record RetrievedDocument(
            String id,
            double score,
            String text,
            Map<String, Object> metadata
    ) {
    }
}
