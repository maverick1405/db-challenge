package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Transfer {
	
	@NotNull
    @NotEmpty
	private String fromAccount;
	
	@NotNull
    @NotEmpty
	private String toAccount;
	
	@NotNull
    @Min(value = 1, message = "Transfer amount must be positive.")
	private BigDecimal amount;
	
	@JsonCreator
    public Transfer(@JsonProperty("fromAccount") String fromAccount,
                    @JsonProperty("toAccount") String toAccount,
                    @JsonProperty("amount") BigDecimal amount){
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
	}
}
