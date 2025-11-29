package consultorio.p2.servidores2.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import consultorio.p2.servidores2.dto.cep.CepResponse;

@Service
public class CepService {

  private final RestClient restClient = RestClient.create();

  public CepResponse fetchCep(String cep) {
    // enviar 8 digitos para a api, qualquer coisa alem disso retornar exception
    return restClient.get()
        .uri("https://viacep.com.br/ws/{cep}/json/", cep)
        .retrieve()
        .body(CepResponse.class);
  }
}
