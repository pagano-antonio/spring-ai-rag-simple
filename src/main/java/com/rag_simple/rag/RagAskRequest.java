package com.rag_simple.rag;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RagAskRequest(
        @NotBlank String question,
        @Min(1) @Max(20) Integer topK
) {

    int effectiveTopK() {
        return topK == null ? 4 : topK;
    }
}
