package com.cse.summer.domain;

import javax.persistence.Table;

/**
 * @author 王振琦
 */
@javax.persistence.Entity
@Table(name = "cse_user")
public class User extends Entity {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
