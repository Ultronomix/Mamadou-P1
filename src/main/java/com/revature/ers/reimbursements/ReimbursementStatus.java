package com.revature.ers.reimbursements;

import java.util.Objects;

public class ReimbursementStatus {
    private String reimbursementId;
    private String name;

    public ReimbursementStatus(String id, String status) {
        this.reimbursementId = id;
        this.name = status;
    }

    public String getReimbursementId() {
        return reimbursementId;
    }
    public void setReimbursementId(String reimbursementId) {
        this.reimbursementId = reimbursementId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementStatus role1 = (ReimbursementStatus) o;
        return Objects.equals(reimbursementId, role1.reimbursementId) && Objects.equals(name, role1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbursementId, name);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id='" + reimbursementId + '\'' +
                ", status='" + name + '\'' +
                '}';
    }
}

