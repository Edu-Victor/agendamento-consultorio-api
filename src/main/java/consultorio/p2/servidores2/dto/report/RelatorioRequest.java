package consultorio.p2.servidores2.dto.report;


import jakarta.validation.constraints.*;

import java.time.LocalDate;

import consultorio.p2.servidores2.model.TipoSessao;

public record RelatorioRequest(
    @NotNull LocalDate data,
    @NotNull TipoSessao tipoSessao,
    @NotNull @Min(1) @Max(10) Integer humor,
    String observacoes
) {}
