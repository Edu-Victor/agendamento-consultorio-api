package consultorio.p2.servidores2.dto.paciente;

import jakarta.validation.constraints.*;

public record PacienteRequest(
    @NotBlank String nome,
    @NotNull @Min(0) @Max(150) Integer idade,
    @NotBlank @Pattern(regexp = "\\d{8}") String cep,


    String rua,
    String bairro,
    String cidade,
    String estado,
    String complemento,
    String observacoes
) {}
