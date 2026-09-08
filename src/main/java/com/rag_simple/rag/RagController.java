package com.rag_simple.rag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rag")
class RagController {

    private final RagIngestionService ingestionService;
    private final RagQueryService queryService;

    RagController(RagIngestionService ingestionService, RagQueryService queryService) {
        this.ingestionService = ingestionService;
        this.queryService = queryService;
    }

    @PostMapping("/ingest")
    @ResponseStatus(HttpStatus.CREATED)
    IngestResponse ingest(@Valid @RequestBody IngestRequest request) {
        return ingestionService.ingest(request);
    }

    @PostMapping("/ask")
    RagAskResponse ask(@Valid @RequestBody RagAskRequest request) {
        return queryService.ask(request);
    }
}
