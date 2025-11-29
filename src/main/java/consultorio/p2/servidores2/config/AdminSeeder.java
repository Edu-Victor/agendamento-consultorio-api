package consultorio.p2.servidores2.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import consultorio.p2.servidores2.model.Role;
import consultorio.p2.servidores2.model.Usuarios;
import consultorio.p2.servidores2.repository.UsuarioRepository;

@Component
public class AdminSeeder implements CommandLineRunner {

  private final UsuarioRepository repo;
  private final PasswordEncoder encoder;

  @Value("${app.admin.email:}")
  private String adminEmail;

  @Value("${app.admin.password:}")
  private String adminPassword;

  public AdminSeeder(UsuarioRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  @Override
  public void run(String... args) {
    if (adminEmail == null || adminEmail.isBlank()) return;
    if (repo.existsByEmail(adminEmail)) return;

    Usuarios admin = Usuarios.builder()
        .email(adminEmail)
        .password(encoder.encode(adminPassword))
        .role(Role.ROLE_ADMIN)
        .build();

    repo.save(admin);
    System.out.println("ADMIN criado: " + adminEmail);
  }
}