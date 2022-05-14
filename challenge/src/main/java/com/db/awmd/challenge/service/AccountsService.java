package com.db.awmd.challenge.service;

import java.math.BigDecimal;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AccountUpdate;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.InSufficientFundsException;
import com.db.awmd.challenge.exception.TransferBetweenSameAccountException;
import com.db.awmd.challenge.repository.AccountsRepository;

import lombok.Getter;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;
  
  @Autowired
  private TransferValidator transferValidator;
  
  @Getter
  private final NotificationService notificationService;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository,  NotificationService notificationService) {
    this.accountsRepository = accountsRepository;
    this.notificationService = notificationService;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

  public void makeTransfer(Transfer transfer)  throws AccountNotFoundException, InSufficientFundsException, TransferBetweenSameAccountException{
	  
	  final Account fromAccount = accountsRepository.getAccount(transfer.getFromAccount());
      final Account toAccount = accountsRepository.getAccount(transfer.getToAccount());
      final BigDecimal amount = transfer.getAmount();
	  transferValidator.validate(fromAccount,toAccount,transfer);
	  
	
      boolean successful = accountsRepository.updateAccountsBatch(Arrays.asList(
              new AccountUpdate(fromAccount.getAccountId(), amount.negate()),
              new AccountUpdate(toAccount.getAccountId(), amount)
              ));
      
      if (successful){
          notificationService.notifyAboutTransfer(fromAccount, "The transfer to the account with ID " + toAccount.getAccountId() + " is now complete for the amount of " + transfer.getAmount() + ".");
          notificationService.notifyAboutTransfer(toAccount, "The account with ID + " + fromAccount.getAccountId() + " has transferred " + transfer.getAmount() + " into your account.");
      }
	
  }
}
