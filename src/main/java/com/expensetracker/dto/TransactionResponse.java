package com.expensetracker.dto;

public class TransactionResponse {
    private Long transactionId;
    private String message;

    public TransactionResponse(Long transactionId, String message) {
        this.transactionId = transactionId;
        this.message = message;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
