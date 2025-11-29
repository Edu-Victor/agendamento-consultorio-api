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
  private String nome;

  @Column(nullable = false)
  private Integer idade;

  @Column(nullable = false, length = 8)
  private String cep;

  private String rua;
  private String bairro;
  private String cidade;
  private String estado;
  private String complemento;

  @Column(columnDefinition = "text")
  private String observacoes;

  @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Relatorios> reports = new ArrayList<>();

}
