package com.otp.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.otp.configurations.OtpProperties;
import com.otp.dao.GenerateOtpRequest;
import com.otp.dao.OtpManagerDao;
import com.otp.dao.Response;
import com.otp.dao.ValidateOtpRequest;
import com.otp.entity.Otp;
import com.otp.security.Encryption;

import net.minidev.json.JSONObject;

@org.springframework.stereotype.Service
public class Implementation implements ServiceInterface {

	private final LoadingCache<String, Otp> otpCache;

	Encryption encryption = new Encryption();

	@Autowired
	private OtpManagerDao dao;

	Logger log = LoggerFactory.getLogger(Implementation.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	String message;

	public Implementation(OtpProperties properties) {
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(properties.getExpireMins(), TimeUnit.MINUTES)
				.build(new CacheLoader<String, Otp>() {
					@Override
					public Otp load(String key) {
						return new Otp();
					}
				});
	}

	@Override
	public Otp saveOtp(Otp request) {
		return dao.save(request);
	}

	@Override
	public Response generateOtp(GenerateOtpRequest request, OtpProperties properties) {
		JSONObject data = new JSONObject();
		String key = otpKey(request);
		Otp otp = requestEntity(request, properties);
		// persist the data
		log.info("Data to be persisted " + otp.toString());
		Otp savedData = saveOtp(otp);
		String decryptedOtp = encryption.decrypt(savedData.getOtp(),properties);
		savedData.setOtp(decryptedOtp);
		data.put("Data", savedData);
		// cache the request to used later
		otpCache.put(key, otp);
		message = "Successfully generated otp";
		return new Response(properties.getSuccessStatus(), data, message);
	}

	@Override
	public Response validateOtp(ValidateOtpRequest request, OtpProperties properties) {
		log.info("Validating otp " + request.toString());
		JSONObject data = new JSONObject();
		try {
			String key = otpKey(request);
			log.info("Retrieving the opt using the key " + key);
			Otp cachedData = otpCache.get(key);
			if (validateOtpData(cachedData)) {
				log.info("Data already cleared from the cache ");
				log.info("Checking for the record from the db ");
				// if we are unable to fetch from cache revert back and fetch from database
				Otp databaseData = dao.fetchOtpByAccount(request.getAccountNumber(), request.getAmount());
				if (validateOtpData(databaseData)) {
					message = "Invalid key used to validate otp";
					data.put("Data", "");
					return new Response(properties.getFailedStatus(), data, message);
				}
				String decryptedOtp = encryption.decrypt(databaseData.getOtp(),properties);
				databaseData.setOtp(decryptedOtp);
				return otpCheck(request, databaseData, properties);
			} else {
				return otpCheck(request, cachedData, properties);
			}
		} catch (ExecutionException e) {
			message = e.getMessage();
			data.put("Data", "");
			e.printStackTrace();
			return new Response(properties.getFailedStatus(), data, message);
		}
	}

	/**
	 * 
	 * @param otp
	 * @return
	 */
	private Boolean validateOtpData(Otp otp) {
		if (otp == null || otp.getAccountNumber() == null
				|| otp.getAccountNumber().isEmpty() && otp.getAmount() == null && otp.getOtp() == null
				|| otp.getOtp().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private String otpKey(GenerateOtpRequest request) {
		return request.getAccountNumber() + request.getAmount();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private String otpKey(ValidateOtpRequest request) {
		return request.getAccountNumber() + request.getAmount();
	}

	/**
	 * 
	 * @return
	 */
	private String randNumbereGenerator(OtpProperties properties) {
		Random random = new Random();
		int otp = properties.getMinRange() + random.nextInt(properties.getMaxRange());
		log.info("Generating one time password " + otp);
		return String.valueOf(otp);
	}

	/**
	 * 
	 * @param manager
	 * @return
	 * @throws ParseException
	 */
	private Boolean hasOtpExpired(Otp request, OtpProperties properties) {
		try {
			Date dateCreated = sdf.parse(request.getDateCreated());
			Date systemDate = new Date();
			log.info("Otp generation date and time " + dateCreated);
			log.info("System date and time " + systemDate);
			long diff = systemDate.getTime() - dateCreated.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			log.info("Diffrence between time of generation and request time " + (int) diffMinutes);
			log.info("Configured cache time " + properties.getExpireMins());
			if ((int) diffMinutes > properties.getExpireMins()) {
				log.info("Otp has expired ");
				return true;
			}
		} catch (ParseException e) {
			message = "Exception caught " + e.getMessage();
			log.error(message);
			e.printStackTrace();
			return false;
		}
		return false;

	}

	/**
	 * 
	 * @param request
	 * @param otp
	 * @param properties
	 * @return
	 */
	private Response otpCheck(ValidateOtpRequest request, Otp otp, OtpProperties properties) {
		JSONObject data = new JSONObject();
		data.put("Data", otp);

		if (hasOtpExpired(otp, properties)) {
			message = "Expired otp ";
			return new Response(properties.getExpiredStatus(), data, message);
		}

		if (request.getOtp().equalsIgnoreCase(otp.getOtp())) {
			message = "Successfully validated otp  ";
			return new Response(properties.getSuccessStatus(), data, message);
		}

		message = "Invalid otp supplied ";
		return new Response(properties.getFailedStatus(), data, message);
	}

	/**
	 * 
	 * @param request
	 * @param properties
	 * @return
	 * @throws ParseException
	 */
	private Otp requestEntity(GenerateOtpRequest request, OtpProperties properties) {
		Otp entity = new Otp();
		String dateCreated = sdf.format(new Date());
		String otp = randNumbereGenerator(properties);
		String encryptedOtp = encryption.encrypt(otp,properties);
		entity.setAccountNumber(request.getAccountNumber());
		entity.setAmount(request.getAmount());
		entity.setMsisdn(request.getMsisdn());
		entity.setChannel(request.getChannel());
		entity.setOtp(encryptedOtp);
		entity.setDateCreated(dateCreated);
		return entity;
	}

}
