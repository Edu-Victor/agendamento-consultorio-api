package consultorio.p2.servidores2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import consultorio.p2.servidores2.model.Relatorios;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorios, Long> {
  List<Relatorios> findByPacienteIdOrderByDataDesc(Long pacienteId);
}
