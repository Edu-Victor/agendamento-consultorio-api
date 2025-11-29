package consultorio.p2.servidores2.security;

import consultorio.p2.servidores2.repository.UsuarioRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UsuarioRepository userRepository;

  public JwtAuthFilter(JwtService jwtService, UsuarioRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (auth == null || !auth.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = auth.substring(7);

    try {
      String email = jwtService.getEmail(token);
      String role = jwtService.getRole(token);

      if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        // Confere se usuário ainda existe
        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
          filterChain.doFilter(request, response);
          return;
        }

        var authorities = List.of(new SimpleGrantedAuthority(role));
        var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }

    } catch (Exception ignored) {
      
    }

    filterChain.doFilter(request, response);
  }
}
