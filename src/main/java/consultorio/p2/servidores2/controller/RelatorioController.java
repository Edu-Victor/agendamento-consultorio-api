package consultorio.p2.servidores2.controller;


import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import consultorio.p2.servidores2.dto.report.RelatorioRequest;
import consultorio.p2.servidores2.dto.report.RelatorioResponse;
import consultorio.p2.servidores2.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class RelatorioController {

  private final RelatorioService service;

  public RelatorioController(RelatorioService service) {
    this.service = service;
  }

  // listagem por paciente (ADMIN/USER)
  @GetMapping("/api/pacientes/{pacienteId}/reports")
  public List<RelatorioResponse> listByPaciente(@PathVariable Long pacienteId) {
    return service.listByPaciente(pacienteId);
  }

  // criar relatório (ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/api/pacientes/{pacienteId}/reports")
  public RelatorioResponse create(@PathVariable Long pacienteId, @RequestBody @Valid RelatorioRequest req) {
    return service.create(pacienteId, req);
  }

  // editar relatório (ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/api/reports/{reportId}")
  public RelatorioResponse update(@PathVariable Long reportId, @RequestBody @Valid RelatorioRequest req) {
    return service.update(reportId, req);
  }

  // apagar relatório (ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/api/reports/{reportId}")
  public void delete(@PathVariable Long reportId) {
    service.delete(reportId);
  }
}
