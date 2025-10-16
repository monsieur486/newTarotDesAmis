package fr.tartempion.tda.dto;

import fr.tartempion.tda.model.Partie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartieForm {
  private Integer idMort;          // optionnel: caché si 5 joueurs; caché aussi si 4 joueurs (voir règle)
  private Integer idPreneur;       // requis (0 = “choisir un joueur” -> validation côté contrôleur)
  private Integer idAppel;         // optionnel: caché si 4 joueurs
  private Integer idPetitAuBout;   // optionnel (0 = “choisir un joueur”)

  private Integer idContrat;    // liste (premier choix = “balge” id=1)
  private Integer point;        // 0, 10, 20, 30, 40, 50

  private Boolean isFait;
  private Boolean isCapotRenverse;
  private Boolean isChelem;

  public Partie toEntity(){
    return Partie.builder()
            .idMort(this.idMort)
            .idContrat(this.idContrat)
            .idPreneur(this.idPreneur)
            .idAppel(this.idAppel)
            .isFait(this.isFait)
            .points(this.point)
            .idPetitAuBout(this.idPetitAuBout)
            .isChelem(this.isChelem)
            .isCapotRenverse(this.isCapotRenverse)
            .build();
  }
}
