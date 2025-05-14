package org.yasmani.io.apprestwithtoken.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yasmani.io.apprestwithtoken.model.Transaction;
import org.yasmani.io.apprestwithtoken.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public String process(@RequestBody Transaction tx) {
        return transactionService.handle(tx);
    }

    @GetMapping("/sample")
    public String sampleJson() {
        return """
            {
              "id": "tx123",
              "amount": 100.0,
              "type": "DEPOSIT"
            }
            """;
    }

    @PostAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }

}
