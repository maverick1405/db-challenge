package com.db.awmd.challenge.service;


import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.InSufficientFundsException;
import com.db.awmd.challenge.exception.TransferBetweenSameAccountException;

public interface TransferValidator {
	
	void validate(final Account accountFrom, final Account accountTo, final Transfer transfer) throws AccountNotFoundException, TransferBetweenSameAccountException, InSufficientFundsException;

}
