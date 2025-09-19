package controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import entity.JwtResponse;
import entity.LoginEntity;
import security.JwtUtil;
import service.LoginService;
import service.OtpService;

@RestController
@RequestMapping("/logins")
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OtpService otpService;

    @PostMapping
    public ResponseEntity<LoginEntity> createLogin(@RequestBody LoginEntity login) {
        LoginEntity savedLogin = loginService.save(login);
        return ResponseEntity.ok(savedLogin);
    }

    @GetMapping
    public ResponseEntity<List<LoginEntity>> getAllLogins() {
        List<LoginEntity> logins = loginService.getAll();
        return ResponseEntity.ok(logins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginEntity> getLoginById(@PathVariable Integer id) {
        LoginEntity login = loginService.getById(id);
        if (login != null) {
            return ResponseEntity.ok(login);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginEntity> updateLogin(@PathVariable Integer id, @RequestBody LoginEntity login) {
        LoginEntity updatedLogin = loginService.update(id, login);
        if (updatedLogin != null) {
            return ResponseEntity.ok(updatedLogin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Integer id) {
        loginService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<LoginEntity> getLoginByEmail(@PathVariable String email) {
        LoginEntity login = loginService.getByEmail(email);
        if (login != null) {
            return ResponseEntity.ok(login);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginEntity loginRequest) {
        // Check credentials (email, password)
        LoginEntity user = loginService.getByEmail(loginRequest.getEmail());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // Return the token in response (could also include user details)
        return ResponseEntity.ok(new JwtResponse(token));
    }

//otpService 
    
    @PostMapping("/generate")
    public String generateOtp(@RequestParam String email) {
        LoginEntity user = otpService.createOtpForUser(email);
        // 🔥 TODO: Send OTP via email/SMS here
        return "OTP sent to " + user.getEmail();
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String otp = payload.get("otp");
        boolean isValid = otpService.verifyOtp(email, otp);
        return isValid ? "✅ OTP verified successfully!" : "❌ Invalid or expired OTP!";
    }
}
