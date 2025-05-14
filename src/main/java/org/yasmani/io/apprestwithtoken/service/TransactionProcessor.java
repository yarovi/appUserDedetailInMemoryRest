package org.yasmani.io.apprestwithtoken.service;

import org.yasmani.io.apprestwithtoken.model.Transaction;

public sealed interface TransactionProcessor
        permits DepositProcessor, WithdrawalProcessor , TransferProcessor {
    String process(Transaction tx);
}

final class DepositProcessor implements TransactionProcessor {
    @Override
    public String process(Transaction tx) {
        return "Deposited $" + tx.amount();
    }
}

final class WithdrawalProcessor implements TransactionProcessor {
    @Override
    public String process(Transaction tx) {
        return "Withdrew $" + tx.amount();
    }
}

final class TransferProcessor implements TransactionProcessor {
    @Override
    public String process(Transaction tx) {
        return "Transferred $" + tx.amount();
    }
}