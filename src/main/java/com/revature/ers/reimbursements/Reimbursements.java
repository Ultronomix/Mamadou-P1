package com.revature.ers.reimbursements;

import java.io.Serializable;
import java.util.Objects;


public class Reimbursements implements Serializable{

    private String reimbId;
    private int amount;
    private String submitted;
    private String resolved;
    private String description;
    private String paymentId;
    private String authorId; // ? links to user_id
    private String resolverId; // ? links to user_id
    private ReimbursementStatus status; // ? links to reimbursement statuses
    private ReimbursementType type; // ? links to reimbursement type

    public Reimbursements(Reimbursement subject) {
        this.reimbId = subject.getReimbursementId();
        this.amount = subject.getAmount();
        this.submitted = subject.getSubmitted();
        this.resolved = subject.getResolved();
        this.description = subject.getDescription();
        this.paymentId = subject.getPaymentId();
        this.authorId = subject.getAuthorId();
        this.resolverId = subject.getResolverId();
        this.status = subject.getStatus();
        this.type = subject.getType();
    }

    public String getReimbId() {
        return reimbId;
    }

    public void setReimbId(String reimbId) {
        this.reimbId = reimbId;
    }

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

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
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

    public String getResolverId() {
        return resolverId;
    }

    public void setResolverId(String resolverId) {
        this.resolverId = resolverId;
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    public ReimbursementType getType() {
        return type;
    }

    public void setType(ReimbursementType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null ||getClass() != o.getClass()) {return false;}
        Reimbursements reimbursements = (Reimbursements) o;
        return Objects.equals(reimbId, reimbursements.reimbId) && Objects.equals(amount, reimbursements.amount)
                && Objects.equals(submitted, reimbursements.submitted) && Objects.equals(resolved, reimbursements.resolved)
                && Objects.equals(description, reimbursements.description) && Objects.equals(paymentId, reimbursements.paymentId)
                && Objects.equals(authorId, reimbursements.authorId) && Objects.equals(resolverId, reimbursements.resolverId)
                && Objects.equals(status, reimbursements.status) && Objects.equals(type, reimbursements.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbId, amount, submitted, resolved, description, paymentId, authorId,
                resolverId, status, type);
    }

    @Override
    public String toString() {
        return "Reimbursement {" +
                "reimb_id = '" + reimbId + "' " +
                "amount = '" + amount + "' " +
                "submitted = '" + submitted + "' " +
                "resolved = '" + resolved + "' " +
                "description = '" + description + "' " +
                "payment_id = '" + paymentId + "' " +
                "author_id = '" + authorId + "' " +
                "resolver_id = '" + resolverId + "' " +
                "status = '" + status + "' " +
                "type = '" + type + "'}";
    }
}