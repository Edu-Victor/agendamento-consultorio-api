package consultorio.p2.servidores2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cors = new CorsConfiguration();
    cors.setAllowedOrigins(List.of(
      "http://localhost:5173",
      "https://agendamento-consultorio-web.vercel.app"
    ));
    cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
    cors.setAllowedHeaders(List.of("*"));
    cors.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cors);
    return source;
  }
}