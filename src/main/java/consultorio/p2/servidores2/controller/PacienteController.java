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
public class PacienteController {

  private final PacienteService service;

  public PacienteController(PacienteService service) {
    this.service = service;
  }

 // ADMIN e USER podem visualizar
  @GetMapping
  public List<PacienteResponse> list() {
    return service.list();
  }

  @GetMapping("/{id}")
  public PacienteResponse get(@PathVariable Long id) {
    return service.get(id);
  }

// Só ADMIN cria
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public PacienteResponse create(@RequestBody @Valid PacienteRequest req) {
    return service.create(req);
  }

// Só ADMIN edita
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public PacienteResponse update(@PathVariable Long id, @RequestBody @Valid PacienteRequest req) {
    return service.update(id, req);
  }

// Só ADMIN deleta
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
