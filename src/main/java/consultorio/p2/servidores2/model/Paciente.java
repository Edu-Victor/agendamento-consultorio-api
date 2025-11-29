package consultorio.p2.servidores2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Paciente {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer age;

  @Column(nullable = false, length = 8)
  private String cep;

  // Endereço - recebe viaCep
  private String rua;//logradouro
  private String bairro;//bairro
  private String cidade;//localidade
  private String estado;//uf
  private String complemento;//complemento

  @Column(columnDefinition = "text")
  private String observacoes;

  @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Relatorios> reports = new ArrayList<>();

}
