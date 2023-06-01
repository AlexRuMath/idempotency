package edu.mipt.accounts;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import static edu.mipt.accounts.AccountResponseStatus.OK;

@AllArgsConstructor
@Embeddable
@Data
public class AccountResponse {
    private AccountResponseStatus status;
    private long balance;

    public AccountResponse(){ }

    public static AccountResponse okResponse(long balance) {
        return new AccountResponse(OK, balance);
    }
}