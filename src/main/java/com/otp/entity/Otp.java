package com.otp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "OtpManager")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Otp {

	@Id
	@Column(name = "otpId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long otpId;

	@Column(name = "otp")
	private String otp;

	@Column(name = "accountNumber")
	@NotNull(message = "accountNumber cannot be missing or empty")
	private String accountNumber;

	@Column(name = "msisdn")
	@NotNull(message = "msisdn cannot be missing or empty")
	private String msisdn;

	@Column(name = "amount")
	@NotNull(message = "amount cannot be missing or empty")

	private Double amount;

	@Column(name = "channel")
	@NotNull(message = "channel cannot be missing or empty")
	private String channel;

	@Column(name = "dateCreated")
	@NotNull(message = "dateCreated cannot be missing or empty")
	private String dateCreated;

	public long getOtpId() {
		return otpId;
	}

	public void setOtpId(long otpId) {
		this.otpId = otpId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "OtpManager [otpId=" + otpId + ", otp=" + otp + ", accountNumber=" + accountNumber + ", msisdn=" + msisdn
				+ ", amount=" + amount + ", channel=" + channel + ", dateCreated=" + dateCreated + "]";
	}

}
