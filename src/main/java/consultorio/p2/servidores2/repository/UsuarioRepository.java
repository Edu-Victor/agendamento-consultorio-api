package consultorio.p2.servidores2.repository;

import consultorio.p2.servidores2.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
  Optional<Usuarios> findByEmail(String email);
  boolean existsByEmail(String email);
}