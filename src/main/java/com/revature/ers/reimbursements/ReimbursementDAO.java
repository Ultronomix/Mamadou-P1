package com.revature.ers.reimbursements;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.ers.common.datasource.ConnectionFactory;
import com.revature.ers.common.exceptions.DataSourceException;

public class ReimbursementDAO {
    private final String select = "SELECT er.reimb_id, er.amount, er.submitted, er.resolved, " +
            "er.description, er.payment_id, er.author_id, er.resolver_id, " +
            "ers.status, ers.status_id, ert.type, ert.type_id " +
            "FROM ers_reimbursements er " +
            "JOIN ers_reimbursement_statuses ers ON er.status_id = ers.status_id " +
            "JOIN ers_reimbursement_types ert ON er.type_id = ert.type_id ";

    public String create(Reimbursement reimbursement){
        String sql = "INSERT INTO ers_reimbursements (reimb_id, amount, submitted, description, author_id, status_id, type_id)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reimbursement.getReimbursementId());
            pstmt.setDouble(2, reimbursement.getAmount());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(4, reimbursement.getDescription().trim());
            pstmt.setString(5, reimbursement.getAuthorId());
            pstmt.setString(6, Reimbursement.getStatusFromName("PENDING").getReimbursementId());
            pstmt.setString(7, reimbursement.getType().getReimbursementId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException(e);
        }
        return "hi";
//        return user.getUsername() + " added.";
    }

    public List<Reimbursement> getAllReimb () {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            return mapResultSet(rs);
        } catch (SQLException e) {
            // TODO add log
            throw new DataSourceException(e);
        }

    }
    public String approveDeny(String reimb_id, String resolver_id, ReimbursementStatus reimb_status) {
        String sql = "UPDATE ers_reimbursements SET (resolver_id, status_id, resolved)= (?,?,?) WHERE reimb_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, resolver_id);
            pstmt.setString(2, reimb_status.getReimbursementId());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(4, reimb_id);
            pstmt.executeUpdate();
            return "reimb changed"; //TODO change
        } catch (SQLException e) {
            //TODO log exception
            throw new DataSourceException(e);
        }
    }

    public Optional<Reimbursement> getReimbById (String id) {
        String sqlId = select + "WHERE er.reimb_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlId);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        while (rs.next()) {
            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setReimbursementId(rs.getString("reimb_id"));
            reimbursement.setAmount(rs.getInt("amount"));
            reimbursement.setSubmitted(rs.getString("submitted"));
            reimbursement.setResolved(rs.getString("resolved"));
            reimbursement.setDescription(rs.getString("description"));
            reimbursement.setPaymentId(rs.getString("payment_id"));
            reimbursement.setAuthorId(rs.getString("author_id"));
            reimbursement.setResolverId(rs.getString("resolver_id"));
            reimbursement.setStatus(new ReimbursementStatus(rs.getString("status_id"),rs.getString("status")));
            reimbursement.setType(new ReimbursementType(rs.getString("type_id"),rs.getString("type")));
            reimbursements.add(reimbursement);
        }
        return reimbursements;
    }
}
