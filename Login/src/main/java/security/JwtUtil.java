package security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key - should be 256-bit for HS256 algorithm
    private final String jwtSecret = "MyJwtSecretKey123456789012345678901234567890"; // 48+ chars
    
    // Token validity time in milliseconds (1 hour)
    private final long jwtExpirationMs = 3600000;
    
    // Generate signing key from secret
    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    // Generate JWT token using the subject (usually username or email)
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)                   // who is the token for
                .setIssuedAt(new Date())               // issued time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // expiry
                .signWith(key, SignatureAlgorithm.HS256) 
                .compact();
    }

    // Extract the subject (username/email) from a JWT token
    public String getSubjectFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)        // set the signing key
                .build()
                .parseClaimsJws(token)     // parse the token
                .getBody()
                .getSubject();             // extract the subject claim
    }

    // Validate the token: checks signature, expiration, structure
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token invalid, expired, or malformed
            return false;
        }
    }
}
