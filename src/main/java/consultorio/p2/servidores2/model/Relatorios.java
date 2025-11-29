package consultorio.p2.servidores2.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "relatorios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Relatorios {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "paciente_id", nullable = false)
  private Paciente paciente;

  @Column(nullable = false)
  private LocalDate data;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoSessao tipoSessao;

  @Column(nullable = false)
  private Integer humor;

  @Column(columnDefinition = "text")
  private String observacoes;
}
