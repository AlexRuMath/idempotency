package edu.mipt.accounts.impl;

import edu.mipt.accounts.AccountResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transactions {
    @Id
    private String rqUid;
    private AccountResponse response;

    public Transactions() {
    }

    public Transactions(String rqUid, AccountResponse response) {
        this.rqUid = rqUid;
        this.response = response;
    }
}