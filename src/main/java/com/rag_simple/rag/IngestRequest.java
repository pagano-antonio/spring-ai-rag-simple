package com.rag_simple.rag;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record IngestRequest(
        @NotBlank String text,
        String source,
        Map<String, Object> metadata
) {
}
