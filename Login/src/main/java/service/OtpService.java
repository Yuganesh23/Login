package service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.LoginEntity;

@Service
public class OtpService {

	   @Autowired
	    private LoginService loginService; // Use existing LoginService

	    public String generateOtp() {
	        SecureRandom random = new SecureRandom();
	        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
	        return String.valueOf(otp);
	    }

	    public LoginEntity createOtpForUser(String email) {
	        LoginEntity user = loginService.getByEmail(email);
	        if (user == null) {
	            throw new RuntimeException("User not found");
	        }

	        String otp = generateOtp();
	        user.setOtp(otp);
	        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP expires in 5 min

	        return loginService.save(user);
	    }

	    public boolean verifyOtp(String email, String otp) {
	        LoginEntity user = loginService.getByEmail(email);
	        if (user == null || user.getOtp() == null || user.getOtpExpiryTime() == null) {
	            return false;
	        }

	        boolean isValid = user.getOtp().equals(otp) && user.getOtpExpiryTime().isAfter(LocalDateTime.now());

	        if (isValid) {
	            user.setOtp(null);
	            user.setOtpExpiryTime(null);
	            loginService.save(user);
	        }

	        return isValid;
	    }
}