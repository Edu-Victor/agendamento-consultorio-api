package consultorio.p2.servidores2.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import consultorio.p2.servidores2.dto.report.RelatorioRequest;
import consultorio.p2.servidores2.dto.report.RelatorioResponse;
import consultorio.p2.servidores2.erros.NotFoundException;
import consultorio.p2.servidores2.model.Paciente;
import consultorio.p2.servidores2.model.Relatorios;
import consultorio.p2.servidores2.repository.PacienteRepository;
import consultorio.p2.servidores2.repository.RelatorioRepository;

import java.util.List;

@Service
public class RelatorioService {

  private final RelatorioRepository reportRepo;
  private final PacienteRepository pacienteRepo;

  public RelatorioService(RelatorioRepository reportRepo, PacienteRepository pacienteRepo) {
    this.reportRepo = reportRepo;
    this.pacienteRepo = pacienteRepo;
  }

  public List<RelatorioResponse> listByPaciente(Long pacienteId) {
    if (!pacienteRepo.existsById(pacienteId)) throw new NotFoundException("Paciente não encontrado");
    return reportRepo.findByPacienteIdOrderByDataDesc(pacienteId).stream().map(this::toResponse).toList();
  }

  @Transactional
  public RelatorioResponse create(Long pacienteId, RelatorioRequest req) {
    Paciente paciente = pacienteRepo.findById(pacienteId).orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

    Relatorios r = Relatorios.builder()
        .paciente(paciente)
        .data(req.data())
        .tipoSessao(req.tipoSessao())
        .humor(req.humor())
        .observacoes(req.observacoes())
        .build();
    return toResponse(reportRepo.save(r));
  }

  @Transactional
  public RelatorioResponse update(Long reportId, RelatorioRequest req) {
    Relatorios r = reportRepo.findById(reportId).orElseThrow(() -> new NotFoundException("Relatório não encontrado"));
    r.setData(req.data());
    r.setTipoSessao(req.tipoSessao());
    r.setHumor(req.humor());
    r.setObservacoes(req.observacoes());
    return toResponse(r);
  }

  @Transactional
  public void delete(Long reportId) {
    if (!reportRepo.existsById(reportId)) throw new NotFoundException("Relatório não encontrado");
    reportRepo.deleteById(reportId);
  }

  private RelatorioResponse toResponse(Relatorios r) {
    return new RelatorioResponse(
        r.getId(),
        r.getPaciente().getId(),
        r.getData(),
        r.getTipoSessao(),
        r.getHumor(),
        r.getObservacoes()
    );
  }
}
