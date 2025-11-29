package consultorio.p2.servidores2.controller;


import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import consultorio.p2.servidores2.dto.paciente.PacienteRequest;
import consultorio.p2.servidores2.dto.paciente.PacienteResponse;
import consultorio.p2.servidores2.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Paciente", description = "Operações de cadastro, consulta e administração de pacientes.")
public class PacienteController {

  private final PacienteService service;

  public PacienteController(PacienteService service) {
    this.service = service;
  }

 // ADMIN e USER podem visualizar
  @GetMapping
  @Operation(summary = "Listar paciente", description = "Retorna uma lista de pacientes cadastrados.")
  public List<PacienteResponse> list() {
    return service.list();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar paciente por ID", description = "Retorna os dados de um paciente específico pelo ID.")
  public PacienteResponse get(@PathVariable Long id) {
    return service.get(id);
  }

// Só ADMIN cria
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(summary = "Cadastrar paciente", description = "Cria um novo paciente e retorna os dados cadastrados (Role admin necessária).")
  public PacienteResponse create(@RequestBody @Valid PacienteRequest req) {
    return service.create(req);
  }

// Só ADMIN edita
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  @Operation(summary = "Atualizar paciente", description = "Atualiza os dados de um paciente pelo ID e retorna o registro atualizado (Role admin necessária).")
  public PacienteResponse update(@PathVariable Long id, @RequestBody @Valid PacienteRequest req) {
    return service.update(id, req);
  }

// Só ADMIN deleta
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  @Operation(summary = "Remover paciente", description = "Remove um paciente pelo ID (Role admin necessária).")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
