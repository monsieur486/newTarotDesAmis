package fr.tartempion.tda.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Partie {
  private Integer id;
  private Integer idMort;
  private Integer idContrat;
  private Integer idPreneur;
  private Integer idAppel;
  private Boolean isFait;
  private Integer points;
  private Integer idPetitAuBout;
  private Boolean isChelem;
  private Boolean isCapotRenverse;

  public Partie() {
    this.id = 0;
    this.idMort = 0;
    this.idContrat = 0;
    this.idPreneur = 0;
    this.idAppel = 0;
    this.isFait = false;
    this.points = 0;
    this.idPetitAuBout = 0;
    this.isChelem = false;
    this.isCapotRenverse = false;
  }
}
