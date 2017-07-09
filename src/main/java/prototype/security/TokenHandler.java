package prototype.security;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenHandler {

	private static final long EXPIRATION_TIME = 180000;
	private static final String SECRET = "JWTSecretString";

	public String parseUserFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET)
							.parseClaimsJws(token)
							.getBody()
							.getSubject();
    }

	public String createTokenForUser(String userEmail) {
		return Jwts.builder()
				.setSubject(userEmail)
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
    
}
