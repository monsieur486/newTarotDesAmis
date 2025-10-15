package fr.tartempion.tda.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Partie {
  private Integer id = 0;
  private Integer idContrat = 0;
  private Integer idMort = 0;
  private Integer idPreneur = 0;
  private Integer idAppel = 0;
  private Boolean isFait = false;
  private Integer points = 0;
  private Integer idPetitAuBout = 0;
  private Boolean isChelem = false;
  private Boolean isCapotRenverse = false;
}
