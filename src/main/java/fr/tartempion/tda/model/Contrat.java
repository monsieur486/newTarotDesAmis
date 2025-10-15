package fr.tartempion.tda.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Contrat {

  private Integer id;
  private String denomination;
  private String initiale;
  private Integer points;
}
