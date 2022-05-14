package com.db.awmd.challenge.service;

import java.math.BigDecimal;


import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.InSufficientFundsException;
import com.db.awmd.challenge.exception.TransferBetweenSameAccountException;
import org.springframework.stereotype.Component;

@Component
public class TransferValidatorImpl implements TransferValidator {

	@Override
	public void validate(Account fromAccount, Account toAccount, Transfer transfer)
			throws AccountNotFoundException, InSufficientFundsException, TransferBetweenSameAccountException  {
		// TODO Auto-generated method stub
		
		 if (fromAccount == null){
	            throw new AccountNotFoundException("Account " + transfer.getFromAccount() + " not found.");
	        }

	        if (toAccount == null) {
	            throw new AccountNotFoundException("Account " + transfer.getToAccount() + " not found.");
	        }

	        if (transfer.getFromAccount().equals(transfer.getToAccount())){
	            throw new TransferBetweenSameAccountException("From and To accounts can't be same");
	        }

	        if (!sufficientFunds(fromAccount, transfer.getAmount())){
	            throw new InSufficientFundsException("Insufficient funds on account " + fromAccount.getAccountId() + " balance="+fromAccount.getBalance());
	        }
	}
	
	  private boolean sufficientFunds(final Account account, final BigDecimal amount) {
	        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
	    }

}
