package com.revature.ers.reimbursements;

public class Reimbursements {
    private String reimbID;
    private double amount;
    private String timeSub;
    private String timeResolved;
    private String description;
    private int creatorID;
    private String resolverID;
    private String statusID;
    private String typeID;


    public Reimbursements(){
        super();
    }

    public Reimbursements(Reimbursement reimbImport) {
        this.reimbID = reimbImport.getReimbID();
        this.amount = reimbImport.getAmount();
        this.timeSub = reimbImport.getTimeSub();
        this.timeResolved = reimbImport.getTimeResolved();
        this.description = reimbImport.getDescription();
        this.creatorID = reimbImport.getAuthorID();
        this.resolverID = reimbImport.getResolverID();
        this.statusID = reimbImport.getStatusID();
        this.typeID = reimbImport.getTypeID();

    }

    //getters and setters
    public String getReimbID() {
        return reimbID;
    }

    public void setReimbID(String reimbID) {
        this.reimbID = reimbID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimeSub() {
        return timeSub;
    }

    public void setTimeSub(String timeSub) {
        this.timeSub = timeSub;
    }

    public String getTimeResolved() {
        return timeResolved;
    }

    public void setTimeResolved(String timeResolved) {
        this.timeResolved = timeResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getResolverID() {
        return resolverID;
    }

    public void setResolverID(String resolverID) {
        this.resolverID = resolverID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    @Override
    public String toString() {
        return "ReimbursementDTO{" +
                "\nreimbID= " + reimbID +
                ", \namount= " + amount +
                ", \ntimeSub= " + timeSub +
                ", \ntimeResolved= " + timeResolved +
                ", \ndescription= " + description +
                ", \nauthorID= " + creatorID +
                ", \nresolverID= " + resolverID +
                ", \nstatusID= " + statusID +
                ", \ntypeID= " + typeID + '}';
    }
}