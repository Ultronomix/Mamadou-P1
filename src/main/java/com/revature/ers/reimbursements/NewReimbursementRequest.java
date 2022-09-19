package com.revature.ers.reimbursements;

import com.revature.ers.common.Request;

import java.util.UUID;

public class NewReimbursementRequest implements Request<Reimbursement> {
    private int amount;
    private String submitted;

    private String resolved;
    private String description;
    private String paymentId;
    private String authorId;

    private String resolver_id;
    private ReimbursementStatus status;
    private ReimbursementType type;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public ReimbursementType getType() {
        return type;
    }

    public void setType(String type_name) {
        this.type = Reimbursement.getTypeFromName(type_name);
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public void setStatus(String status_name) {
        this.status = Reimbursement.getStatusFromName(status_name);
    }

    @Override
    public String toString() {
        return "NewReimbRequest {" +
                "amount = '" + amount + "' " +
                "submitted = " + submitted + "' " +
                "description = '" + description + "' " +
                "payment_id = '" + paymentId + "' " +
                "author_id = '" + authorId + "' " +
                "status = '" + status + "' " +
                "type = '" + type + "'}";
    }

    @Override
    public Reimbursement extractEntity() {
        Reimbursement extractEntity = new Reimbursement();
        extractEntity.setReimbursementId(UUID.randomUUID().toString());
        extractEntity.setAmount(this.amount);
        extractEntity.setSubmitted(this.submitted);
        extractEntity.setDescription(this.description);
        extractEntity.setPaymentId(this.paymentId);
        extractEntity.setAuthorId(this.authorId);
        extractEntity.setStatus(this.status);
        extractEntity.setType(this.type);
        return extractEntity;
    }
}