package consultorio.p2.servidores2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final SecretKey key;
  private final long expirationMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expiration-minutes}") long expirationMinutes
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMinutes = expirationMinutes;
  }

  public String generateToken(String email, String role) {
    Instant now = Instant.now();
    Instant exp = now.plusSeconds(expirationMinutes * 60);

    return Jwts.builder()
        .subject(email)
        .claims(Map.of("role", role))
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .signWith(key, Jwts.SIG.HS256) // recebo a key aqui e passo pela hash para a assinatura.
        .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parser()
        .verifyWith(key) // a secretkey vai direto, sem precisar passar por hash/assinatura.
        .build()
        .parseSignedClaims(token);
  }

  public String getEmail(String token) {
    return parse(token).getPayload().getSubject();
  }

  public String getRole(String token) {
    Object v = parse(token).getPayload().get("role");
    return v == null ? null : v.toString();
  }
}