package consultorio.p2.servidores2.dto.report;


import java.time.LocalDate;

import consultorio.p2.servidores2.model.TipoSessao;

public record RelatorioResponse(
    Long id,
    Long pacienteId,
    LocalDate data,
    TipoSessao tipoSessao,
    Integer humor,
    String observacoes
) {}
