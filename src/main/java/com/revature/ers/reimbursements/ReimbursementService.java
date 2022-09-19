package com.revature.ers.reimbursements;

import com.revature.ers.common.ResourceCreationResponse;
import com.revature.ers.common.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;



public class ReimbursementService {

    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public List<Reimbursements> getAllReimb() {
        List<Reimbursements> result = new ArrayList<>();
        List<Reimbursement> reimbursements = reimbursementDAO.getAllReimb();

        for (Reimbursement reimbursement : reimbursements) {
            result.add(new Reimbursements(reimbursement));
        }
        return result;
    }

    public Reimbursements getReimbById(String id) {
        if (id == null || id.trim().length() <= 0) {
            throw new RuntimeException("A user's id must be provided");
        }
        return reimbursementDAO.getReimbById(id).map(Reimbursements::new).orElseThrow(ResourceNotFoundException::new);
    }

    public ResourceCreationResponse approveDeny(String reimb_id, String resolver_id, String status) {
        if (reimb_id == null || status == null || resolver_id == null) {
            throw new RuntimeException("Provided request payload was null.");
        }
        ReimbursementStatus reimb_status = Reimbursement.getStatusFromName(status);
        // TODO: validate status
        String newApproveDeny = reimbursementDAO.approveDeny(reimb_id, resolver_id, reimb_status);
        return new ResourceCreationResponse(newApproveDeny);
    }

    public ResourceCreationResponse createReimb(NewReimbursementRequest newReimb) {
        if (newReimb == null) {
            throw new RuntimeException("Provided request payload was null.");
        }
        // TODO: Check reimb criteria make sure the type exists

        Reimbursement reimbursementToPersist = newReimb.extractEntity();
        String newRiemb = reimbursementDAO.create(reimbursementToPersist);
        return new ResourceCreationResponse(newRiemb);
    }
}