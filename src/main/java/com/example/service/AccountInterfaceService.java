package com.example.service;

import java.util.List;

import com.example.entity.Account;

public interface AccountInterfaceService {
     Account addAccount(Account account);
     List<Account> getAllAccounts();

}
