package org.yasmani.io.apprestwithtoken.model;

import org.yasmani.io.apprestwithtoken.TransactionType;

public record Transaction(String id, double amount, TransactionType type) {
}
