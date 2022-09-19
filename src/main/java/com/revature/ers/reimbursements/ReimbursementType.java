package com.revature.ers.reimbursements;

import java.util.Objects;

public class ReimbursementType {
    private String reimbursementId;
    private String name;

    public ReimbursementType(String id, String type) {
        this.reimbursementId = id;
        this.name = type;
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
        ReimbursementType role1 = (ReimbursementType) o;
        return Objects.equals(reimbursementId, role1.reimbursementId) && Objects.equals(name, role1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbursementId, name);
    }

    @Override
    public String toString() {
        return "Type{" +
                "id='" + reimbursementId + '\'' +
                ", type='" + name + '\'' +
                '}';
    }

}