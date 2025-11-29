package consultorio.p2.servidores2.dto.cep;

public record CepResponse(
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String localidade,
    String uf,
    Boolean erro
) {}
