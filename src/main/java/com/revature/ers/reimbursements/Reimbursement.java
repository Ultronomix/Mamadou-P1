package com.revature.ers.reimbursements;

import com.revature.ers.common.datasource.ConnectionFactory;
import com.revature.ers.common.exceptions.DataSourceException;
import com.revature.ers.common.exceptions.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Reimbursement {
    private String reimbursementId;
    private int amount;
    private String submitted;
    private String resolved;
    private String description;
    private String paymentId;
    private String authorId;
    private String resolverId;
    private ReimbursementStatus status;
    private ReimbursementType type;

    final static public ReimbursementType getTypeFromName(String type_name) {
        String sql = "SELECT type_id, type FROM ers_reimbursement_types WHERE type = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, type_name);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                // TODO: fix me
                throw new ResourceNotFoundException();
            }
            return new ReimbursementType(rs.getString("type_id"), rs.getString("type"));
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    final static public ReimbursementStatus getStatusFromName(String status_name) {
        String sql = "SELECT status_id, status FROM ers_reimbursement_statuses WHERE status = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, status_name);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                // TODO: fix me
                throw new ResourceNotFoundException();
            }
            return new ReimbursementStatus(rs.getString("status_id"), rs.getString("status"));
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public String getReimbursementId() {
        return this.reimbursementId;
    }
    public void setReimbursementId(String reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return this.submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getResolved() {
        return this.resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getResolverId() {
        return this.resolverId;
    }

    public void setResolverId(String resolverId) {
        this.resolverId = resolverId;
    }

    public ReimbursementStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status  = status;
    }

    public ReimbursementType getType() {
        return this.type;
    }

    public void setType(ReimbursementType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reimbursement reimbursement = (Reimbursement) o;
        return Objects.equals(reimbursementId, reimbursement.reimbursementId) && Objects.equals(amount, reimbursement.amount)
                && Objects.equals(submitted, reimbursement.submitted) && Objects.equals(resolved, reimbursement.resolved)
                && Objects.equals(description, reimbursement.description) && Objects.equals(paymentId, reimbursement.paymentId)
                && Objects.equals(authorId, reimbursement.authorId) && Objects.equals(resolverId, reimbursement.resolverId)
                && Objects.equals(status, reimbursement.status) && Objects.equals(type, reimbursement.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbursementId, amount, submitted, resolved, description, paymentId, authorId,
                resolverId, status, type);
    }

    @Override
    public String toString() {
        return "Reimbursement {" +
                "reimb_id = '" + reimbursementId + "' " +
                "amount = '" + amount + "' " +
                "submitted = '" + submitted + "' " +
                "resolved = '" + resolved + "' " +
                "description = '" + description + "' " +
                "payment_id = '" + paymentId + "' " +
                "author_id = '" + authorId + "' " +
                "resolver_id = '" + resolverId + "' " +
                "status = '" + status.getName() + "' " +
                "type = '" + type.getName() + "'}";
    }
}