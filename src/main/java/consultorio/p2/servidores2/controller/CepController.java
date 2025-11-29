package consultorio.p2.servidores2.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import consultorio.p2.servidores2.dto.cep.CepResponse;
import consultorio.p2.servidores2.service.CepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cep")
public class CepController {

  private final CepService service;

  public CepController(CepService service) {
    this.service = service;
  }

// Só ADMIN precisa usar isso na tela de cadastro
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{cep}")
  public CepResponse getCep(@PathVariable String cep) {
    return service.fetchCep(cep);
  }
}
