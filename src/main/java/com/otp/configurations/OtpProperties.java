package com.otp.configurations;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "otp-engine")
public class OtpProperties {

	/**
	 * Expire period in cache
	 */
	@NotBlank
	private int expireMins;

	/**
	 * Minimum random number range
	 */
	@NotBlank
	private int minRange;

	/**
	 * Maximum random number range
	 */
	@NotBlank
	private int maxRange;

	/**
	 * Status after successfully generating/validating otp
	 */
	@NotBlank
	private String successStatus;

	/**
	 * Status after failing while generating/validating otp
	 */
	@NotBlank
	private String failedStatus;

	/**
	 * Status for an expired otp
	 */
	@NotBlank
	private String expiredStatus;

	/**
	 * Encryption key
	 */
	@NotBlank
	private String secretKey;

	/**
	 * Salt
	 */
	@NotBlank
	private String salt;

	public int getExpireMins() {
		return expireMins;
	}

	public void setExpireMins(Integer expireMins) {
		this.expireMins = expireMins;
	}

	public int getMinRange() {
		return minRange;
	}

	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}

	public String getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(String successStatus) {
		this.successStatus = successStatus;
	}

	public String getFailedStatus() {
		return failedStatus;
	}

	public void setFailedStatus(String failedStatus) {
		this.failedStatus = failedStatus;
	}

	public String getExpiredStatus() {
		return expiredStatus;
	}

	public void setExpiredStatus(String expiredStatus) {
		this.expiredStatus = expiredStatus;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "OtpProperties [expireMins=" + expireMins + ", minRange=" + minRange + ", maxRange=" + maxRange
				+ ", successStatus=" + successStatus + ", failedStatus=" + failedStatus + ", expiredStatus="
				+ expiredStatus + ", secretKey=" + secretKey + ", salt=" + salt + "]";
	}

}
