package consultorio.p2.servidores2.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import consultorio.p2.servidores2.dto.auth.RegisterRequest;
import consultorio.p2.servidores2.model.Role;
import consultorio.p2.servidores2.model.Usuarios;
import consultorio.p2.servidores2.repository.UsuarioRepository;

@Service
public class UsuarioService {

  private final UsuarioRepository userRepository;
  private final PasswordEncoder encoder;

  public UsuarioService(UsuarioRepository userRepository, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  public Usuarios register(RegisterRequest request, boolean isAdmin) {
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("Email já cadastrado");
    }

    Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_USER;

    Usuarios user = Usuarios.builder()
        .email(request.email())
        .password(encoder.encode(request.password()))
        .role(role)
        .build();

    return userRepository.save(user);
  }
}
