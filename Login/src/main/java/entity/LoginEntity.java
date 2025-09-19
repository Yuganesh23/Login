package entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="logins")
public class LoginEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	   @Column
	    private String otp;

	    @Column
	    private LocalDateTime otpExpiryTime;
	    
	
	public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

		public LocalDateTime getOtpExpiryTime() {
			return otpExpiryTime;
		}

		public void setOtpExpiryTime(LocalDateTime otpExpiryTime) {
			this.otpExpiryTime = otpExpiryTime;
		}

	public LoginEntity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	 
}