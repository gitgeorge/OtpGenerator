package com.otp.app;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.otp.configurations.OtpProperties;
import com.otp.dao.GenerateOtpRequest;
import com.otp.dao.Response;
import com.otp.dao.ValidateOtpRequest;

@RestController
public class Controller {

	@Autowired
	private ServiceInterface service;

	@Autowired
	private OtpProperties properties;

	Logger log = LoggerFactory.getLogger(Controller.class);

	@RequestMapping(method = RequestMethod.POST, value = "/otpManager/generateOtp")
	public Response save(@Valid @RequestBody GenerateOtpRequest request) throws MethodArgumentNotValidException {
		log.info("Otp request received " + request.toString());
		return service.generateOtp(request, properties);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/otpManager/vaidateOtp")
	public Response fetch(@Valid @RequestBody ValidateOtpRequest request) throws MethodArgumentNotValidException {
		return service.validateOtp(request, properties);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("status", properties.getFailedStatus());
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;

	}

}
