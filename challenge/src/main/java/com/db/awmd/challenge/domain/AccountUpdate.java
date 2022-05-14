package com.db.awmd.challenge.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountUpdate {
	
	  private final String accountId;
	  private final BigDecimal amount;


}
