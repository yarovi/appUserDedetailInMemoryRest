package org.yasmani.io.apprestwithtoken.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yasmani.io.apprestwithtoken.model.Transaction;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);
    /**
     * This method handles the transaction based on its type.
     * It uses a switch expression to determine the appropriate processor.
     *
     * @param tx The transaction to be processed.
     * @return A string message indicating the result of the transaction.
     */
    public String handle(Transaction tx){

        TransactionProcessor processor = switch (tx.type()) {
            case DEPOSIT -> new DepositProcessor();
            case WITHDRAWAL -> new WithdrawalProcessor();
            case TRANSFER -> new TransferProcessor();
        };

        if (processor instanceof DepositProcessor) {
            logger.info("DepositProcessor implementation is deposit");
        }
        return processor.process(tx);
    }
}
