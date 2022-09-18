package com.revature.ers.reimbursements;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.ers.common.exceptions.InvalidRequestException;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public Reimbursements generate(NewReimbursementRequest reimbImport) throws InvalidRequestException{


        reimbImport.setTimeSub(Timestamp.valueOf(LocalDateTime.now()).toString());
        reimbImport.setStatusID("1");

        if(reimbImport == null){
            throw new InvalidRequestException("ERROR");
        }
        if(reimbImport.getAmount() <= 0){
            throw new InvalidRequestException("ERROR");
        }
        if(reimbImport.getDescription() == null || reimbImport.getDescription().length() <= 0 ){
            throw new InvalidRequestException("ERROR");
        }
        if(reimbImport.getTypeID() == null || reimbImport.getTypeID().length() <= 0 ){
            throw new InvalidRequestException("ERROR");
        }

        Reimbursement target = reimbursementDAO.create(reimbImport).orElse(null);
        Reimbursements result = new Reimbursements(target);
        return result;
    }

    public List<Reimbursements> getAllReimbs(String usernameImport){
        List<Reimbursements> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getAll(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new Reimbursements(transfer));
        }

        return result;
    }

    public List<Reimbursements> getAllPendingReimbs(String usernameImport){
        List<Reimbursements> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getAllPending(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new Reimbursements(transfer));
        }

        return result;
    }

    public List<Reimbursements> getOwnedReimbs(String usernameImport){
        List<Reimbursements> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getOwned(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new Reimbursements(transfer));
        }

        return result;
    }

    public List<Reimbursements> pendingReimb(String usernameImport){
        List<Reimbursements> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getOwnedPending(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new Reimbursements(transfer));
        }

        return result;
    }

    public Reimbursements getReimbs(int reimbIDImport){
        if(reimbIDImport <= 0){
            throw new InvalidRequestException(
                    "ERROR");
        }

        try{
            Reimbursement target = reimbursementDAO.getSingleByReimbID(reimbIDImport).orElse(null);
            if (target == null){
                throw new IllegalArgumentException("ERROR");
            }

            Reimbursements result = new Reimbursements(target);
            return result;
        }catch(IllegalArgumentException e){
            throw new InvalidRequestException("ERROR");
        }
    }

    public Reimbursements updateReimb(ReimbursementStatus alterationImport){
        if(alterationImport == null){
            throw new InvalidRequestException("ERROR");
        }

        try{
            Reimbursement target = reimbursementDAO.getSingleByReimbID(alterationImport.getReimbID()).orElse(null);


            if (target == null){
                throw new InvalidRequestException("ERROR");
            }else if (!target.getStatusID().equals("1")){
                throw new InvalidRequestException("ERROR");
            }else{
                target = reimbursementDAO.updateReimbursementStatus(alterationImport.getReimbID(), alterationImport.getReimbStatus()).orElse(null);
            }



            Reimbursements result = new Reimbursements(target);
            return result;
        }catch(IllegalArgumentException e){
            throw new InvalidRequestException("ERROR");
        }

    }
}
