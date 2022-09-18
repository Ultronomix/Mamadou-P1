package com.revature.ers.reimbursements;

public class ReimbursementStatus {
    private int reimbID;
    private boolean reimbStatus;

    public ReimbursementStatus(){
        super();
    }

    public ReimbursementStatus(int reimbIDImport, boolean statusUpdateImport){
        this.reimbID = reimbIDImport;
        this.reimbStatus = statusUpdateImport;
    }


    public int getReimbID() {
        return reimbID;
    }

    public void setReimbID(int reimbID) {
        this.reimbID = reimbID;
    }

    public boolean getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(boolean reimbStatus) {
        this.reimbStatus = reimbStatus;
    }


}

