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
@Tag(name = "Relatório", description = "Operações de criação, consulta e manutenção de relatórios vinculados a pacientes.")
public class RelatorioController {

  private final RelatorioService service;

  public RelatorioController(RelatorioService service) {
    this.service = service;
  }

  // listagem por paciente (ADMIN/USER)
  @GetMapping("/api/pacientes/{pacienteId}/reports")
  @Operation(summary = "Listar relatórios do paciente", description = "Retorna os relatórios referente ao paciente específico pelo ID.")
  public List<RelatorioResponse> listByPaciente(@PathVariable Long pacienteId) {
    return service.listByPaciente(pacienteId);
  }

  // criar relatório (ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/api/pacientes/{pacienteId}/reports")
  @Operation(summary = "Cadastrar relatório do paciente", description = "Cria um novo relatório associado ao paciente informado pelo ID (Role admin necessária).")
  public RelatorioResponse create(@PathVariable Long pacienteId, @RequestBody @Valid RelatorioRequest req) {
    return service.create(pacienteId, req);
  }

  // editar relatório (ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/api/reports/{reportId}")
  @Operation(summary = "Atualizar relatório", description = "atualiza os dados do relatório informado pelo ID e retorna o registro atualizado (Role admin necessária).")
  public RelatorioResponse update(@PathVariable Long reportId, @RequestBody @Valid RelatorioRequest req) {
    return service.update(reportId, req);
  }

  // apagar relatório (ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/api/reports/{reportId}")
  @Operation(summary = "Remover relatório", description = "Remove um relatório pelo ID (Role admin necessária).")
  public void delete(@PathVariable Long reportId) {
    service.delete(reportId);
  }
}
