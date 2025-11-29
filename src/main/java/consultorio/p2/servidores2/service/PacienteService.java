package consultorio.p2.servidores2.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import consultorio.p2.servidores2.dto.paciente.PacienteRequest;
import consultorio.p2.servidores2.dto.paciente.PacienteResponse;
import consultorio.p2.servidores2.erros.NotFoundException;
import consultorio.p2.servidores2.model.Paciente;
import consultorio.p2.servidores2.repository.PacienteRepository;

import java.util.List;

@Service
public class PacienteService {

  private final PacienteRepository repo;

  public PacienteService(PacienteRepository repo) {
    this.repo = repo;
  }

  public List<PacienteResponse> list() {
    return repo.findAll().stream().map(this::toResponse).toList();
  }

  public PacienteResponse get(Long id) {
    return toResponse(repo.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado")));
  }

  @Transactional
  public PacienteResponse create(PacienteRequest req) {
    Paciente p = Paciente.builder()
        .nome(req.nome())
        .idade(req.idade())
        .cep(req.cep())
        .rua(req.rua())
        .bairro(req.bairro())
        .cidade(req.cidade())
        .estado(req.estado())
        .complemento(req.complemento())
        .observacoes(req.observacoes())
        .build();
    return toResponse(repo.save(p));
  }

  @Transactional
  public PacienteResponse update(Long id, PacienteRequest req) {
    Paciente p = repo.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
    p.setNome(req.nome());
    p.setIdade(req.idade());
    p.setCep(req.cep());
    p.setRua(req.rua());
    p.setBairro(req.bairro());
    p.setCidade(req.cidade());
    p.setEstado(req.estado());
    p.setComplemento(req.complemento());
    p.setObservacoes(req.observacoes());
    return toResponse(p);
  }

  @Transactional
  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Paciente não encontrado");
    repo.deleteById(id);
  }

  private PacienteResponse toResponse(Paciente p) {
    return new PacienteResponse(
        p.getId(), p.getNome(), p.getIdade(), p.getCep(),
        p.getRua(), p.getBairro(), p.getCidade(), p.getEstado(),
        p.getComplemento(), p.getObservacoes()
    );
  }
}
