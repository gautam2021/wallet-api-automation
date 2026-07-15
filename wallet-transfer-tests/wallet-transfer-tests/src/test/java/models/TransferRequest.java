package models;

public class TransferRequest {

    private Long sourceWalletId;
    private Long destinationWalletId;
    private Double amount;
    private String reference;

    public TransferRequest() {
    }

    public TransferRequest(Long sourceWalletId,
                           Long destinationWalletId,
                           Double amount,
                           String reference) {

        this.sourceWalletId = sourceWalletId;
        this.destinationWalletId = destinationWalletId;
        this.amount = amount;
        this.reference = reference;
    }

    public Long getSourceWalletId() {
        return sourceWalletId;
    }

    public void setSourceWalletId(Long sourceWalletId) {
        this.sourceWalletId = sourceWalletId;
    }

    public Long getDestinationWalletId() {
        return destinationWalletId;
    }

    public void setDestinationWalletId(Long destinationWalletId) {
        this.destinationWalletId = destinationWalletId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}