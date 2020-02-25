package com.otp.app;

import com.otp.configurations.OtpProperties;
import com.otp.dao.GenerateOtpRequest;
import com.otp.dao.Response;
import com.otp.dao.ValidateOtpRequest;
import com.otp.entity.Otp;

public interface ServiceInterface {

	Response generateOtp(GenerateOtpRequest request, OtpProperties props);

	Response validateOtp(ValidateOtpRequest request, OtpProperties props);

	Otp saveOtp(Otp manager);

}
