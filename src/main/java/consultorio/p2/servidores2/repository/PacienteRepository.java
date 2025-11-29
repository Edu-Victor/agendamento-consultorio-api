package consultorio.p2.servidores2.repository;

import consultorio.p2.servidores2.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
