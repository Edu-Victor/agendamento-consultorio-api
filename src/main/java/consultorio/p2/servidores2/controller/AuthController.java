package consultorio.p2.servidores2.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import consultorio.p2.servidores2.dto.auth.AuthResponse;
import consultorio.p2.servidores2.dto.auth.LoginRequest;
import consultorio.p2.servidores2.dto.auth.RegisterRequest;
import consultorio.p2.servidores2.model.Usuarios;
import consultorio.p2.servidores2.repository.UsuarioRepository;
import consultorio.p2.servidores2.security.JwtService;
import consultorio.p2.servidores2.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UsuarioService userService;
  private final UsuarioRepository userRepo;
  private final JwtService jwtService;
  private final PasswordEncoder encoder;

  public AuthController(UsuarioService userService, UsuarioRepository userRepo, JwtService jwtService,
                        PasswordEncoder encoder) {
    this.userService = userService;
    this.userRepo = userRepo;
    this.jwtService = jwtService;
    this.encoder = encoder;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req) {
    Usuarios u = userService.register(req, false);
    return ResponseEntity.ok().body(java.util.Map.of("id", u.getId(), "email", u.getEmail(), "role", u.getRole().name()));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
    Usuarios u = userRepo.findByEmail(req.email())
        .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

    if (!encoder.matches(req.password(), u.getPassword()))
      throw new BadCredentialsException("Credenciais inválidas");

    String token = jwtService.generateToken(u.getEmail(), u.getRole().name());
    return ResponseEntity.ok(new AuthResponse(token, u.getRole().name()));
  }

}
