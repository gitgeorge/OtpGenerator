package com.otp.dao;

import javax.validation.constraints.NotNull;

public class ValidateOtpRequest {

	@NotNull(message = "accountNumber cannot be missing or empty")
	private String accountNumber;

	@NotNull(message = "amount cannot be missing or empty")
	private double amount;

	@NotNull(message = "otp cannot be missing or empty")
	private String otp;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAcountNumber(String acountNumber) {
		this.accountNumber = acountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "ValidateOtpRequest [acountNumber=" + accountNumber + ", amount=" + amount + ", otp=" + otp + "]";
	}

}
