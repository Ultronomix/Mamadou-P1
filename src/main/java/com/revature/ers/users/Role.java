package com.revature.ers.users;

import java.util.Objects;

public class Role {

    private String roleId;
    private String name;

    public Role(String roleId, String role) {
        this.roleId = roleId;
        this.name = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
        Role role1 = (Role) o;
        return Objects.equals(roleId, role1.roleId) && Objects.equals(name, role1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + roleId + '\'' +
                ", role='" + name + '\'' +
                '}';
    }

}