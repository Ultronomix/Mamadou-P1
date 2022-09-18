package com.revature.ers.reimbursements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.ers.common.datasource.ConnectionFactory;
import com.revature.ers.common.exceptions.DataSourceException;

public class ReimbursementDAO {
    String baseSelect =
            "SELECT REIMB_ID, AMOUNT, SUBMITTED, RESOLVED, DESCRIPTION, AUTHOR_ID, RESOLVER_ID, STATUS_ID, TYPE_ID  " +
                    "FROM ERS_REIMBURSEMENTS ER " ;

    private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException{
        List<Reimbursement> reimbursements = new ArrayList<>();

        while(rs.next()){
            Reimbursement reimbursement = new Reimbursement();

            reimbursement.setReimbID(rs.getString("REIMB_ID"));
            reimbursement.setAmount(rs.getDouble("AMOUNT"));
            reimbursement.setTimeSub(rs.getString("SUBMITTED"));
            reimbursement.setDescription(rs.getString("DESCRIPTION"));
            reimbursement.setAuthorID(rs.getInt("AUTHOR_ID"));
            reimbursement.setStatusID(rs.getString("STATUS_ID"));
            reimbursement.setTypeID(rs.getString("TYPE_ID"));

            reimbursements.add(reimbursement);
        }

        return reimbursements;
    }
    public Optional<Reimbursement> create(NewReimbursementRequest reimbImport) throws DataSourceException{
        String sql =
                "INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID) "+
                        "VALUES "+
                        "(?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?)";

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, reimbImport.getAmount());
            pstmt.setString(2, reimbImport.getTimeSub());
            pstmt.setString(3, reimbImport.getDescription());
            pstmt.setInt(4, reimbImport.getAuthorID());
            pstmt.setString(5, reimbImport.getStatusID());
            pstmt.setString(6, reimbImport.getTypeID());
            pstmt.executeUpdate();

            sql = baseSelect +
                    "WHERE AUTHOR_ID = ? AND SUBMITTED = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reimbImport.getAuthorID());
            pstmt.setString(2, reimbImport.getTimeSub());
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        }catch(SQLException e){
            //TODO add error log per 9/9
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }

    public List<Reimbursement> getAll(String usernameImport){

        List<Reimbursement> ownedReimb = new ArrayList<>();

        try(Connection conn =ConnectionFactory.getInstance().getConnection()){
            String sql = "ORDER BY reimb_id ";

            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            ResultSet rs = pstmt.executeQuery();

            ownedReimb = mapResultSet(rs);

        }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
        }

        return ownedReimb;
    }
    public List<Reimbursement> getAllPending(String usernameImport){
        String sql = "WHERE STATUS_ID = '1' "+
                "ORDER BY REIMB_ID ";

        List<Reimbursement> ownedReimb = new ArrayList<>();

        try(Connection conn =ConnectionFactory.getInstance().getConnection()){

            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            ResultSet rs = pstmt.executeQuery();

            ownedReimb = mapResultSet(rs);

        }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
        }

        return ownedReimb;
    }

    public List<Reimbursement> getOwned(String usernameImport){
        String sql = "WHERE AUTHOR_ID = ? "+
                "ORDER BY REIMB_ID ";

        List<Reimbursement> ownedReimb = new ArrayList<>();

        try(Connection conn =ConnectionFactory.getInstance().getConnection()){


            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            pstmt.setString(1, usernameImport);
            ResultSet rs = pstmt.executeQuery();

            ownedReimb = mapResultSet(rs);

        }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error");
            e.printStackTrace();
        }

        return ownedReimb;
    }

    public List<Reimbursement> getOwnedPending(String usernameImport){
        String sql = "WHERE AUTHOR_ID = ? AND WHERE STATUS_ID = '1' "+
                "ORDER BY REIMB_ID ";

        List<Reimbursement> ownedReimb = new ArrayList<>();

        try(Connection conn =ConnectionFactory.getInstance().getConnection()){

            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            pstmt.setString(1, usernameImport);
            ResultSet rs = pstmt.executeQuery();

            ownedReimb = mapResultSet(rs);

        }catch(SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }

        return ownedReimb;
    }
    public Optional<Reimbursement> getSingleByReimbID(int reimbIDImport){
        String sql = "WHERE reimb_id = ? ORDER BY reimb_id ";


        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            pstmt.setInt(1, reimbIDImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        }catch(SQLException e){
            e.printStackTrace();
            throw new DataSourceException (e);
        }

    }
    public Optional<Reimbursement> updateReimbursementStatus(int reimbIDImport, boolean newStatusImport){
        String sql = "UPDATE ers_reimbursements SET status_id = ? WHERE reimb_id = ? ";
        String sql02 = "WHERE reimb_id = ? ";

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            int statusUpdate;
            if(newStatusImport){
                statusUpdate = 3;
            }else{
                statusUpdate = 2;
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, statusUpdate);
            pstmt.setInt(2, reimbIDImport);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(baseSelect + sql02);
            pstmt.setInt(1, reimbIDImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }
}