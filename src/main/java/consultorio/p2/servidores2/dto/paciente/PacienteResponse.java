package consultorio.p2.servidores2.dto.paciente;

public record PacienteResponse(
    Long id,
    String nome,
    Integer idade,
    String cep,
    String rua,
    String bairro,
    String cidade,
    String estado,
    String complemento,
    String observacoes
) {}
