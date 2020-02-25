package com.otp.dao;

import javax.validation.constraints.NotNull;

public class GenerateOtpRequest {

	@NotNull(message = "accountNumber cannot be missing or empty")
	private String accountNumber;

	@NotNull(message = "msisdn cannot be missing or empty")
	private String msisdn;

	@NotNull(message = "amount cannot be missing or empty")
	private double amount;

	@NotNull(message = "channel cannot be missing or empty")
	private String channel;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAcountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "GenerateOtpRequest [acountNumber=" + accountNumber + ", msisdn=" + msisdn + ", amount=" + amount
				+ ", channel=" + channel + "]";
	}

}
