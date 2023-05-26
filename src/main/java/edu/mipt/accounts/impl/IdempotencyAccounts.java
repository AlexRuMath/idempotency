package edu.mipt.accounts.impl;

import edu.mipt.accounts.AccountResponse;
import edu.mipt.accounts.Accounts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static edu.mipt.accounts.AccountResponse.okResponse;

@Service
@Transactional(noRollbackFor = AccountException.class)
@RequiredArgsConstructor
public class IdempotencyAccounts implements Accounts {
    private final AccountRepository accountRepository;

    private final Map<String, AccountResponse> processes_requests = new HashMap<>();

    @Override
    public AccountResponse withdraw(String rqUid, long accountId, long amount) {
        return ProcessRequest(rqUid, accountId, acc -> acc.withdraw(amount));
    }

    @Override
    public AccountResponse deposit(String rqUid, long accountId, long amount) {
        return ProcessRequest(rqUid, accountId, acc -> acc.deposit(amount));
    }

    private AccountResponse ProcessRequest(String rqUid, long accountId, Consumer<Account> processing){
        if(processes_requests.containsKey(rqUid)){
            return processes_requests.get(rqUid);
        }

        AccountResponse response = process(accountId, processing);
        processes_requests.put(rqUid, response);
        return response;
    }

    private AccountResponse process(long accountId, Consumer<Account> processing) {
        var account = accountRepository.findById(accountId);
        try {
            processing.accept(account);
            accountRepository.saveAndFlush(account);
            return okResponse(account.getBalance());
        } catch (AccountException e) {
            return e.toAccountResponse();
        }
    }
}