package fr.tartempion.tda.service;

import fr.tartempion.tda.dto.PartieDetailDto;
import fr.tartempion.tda.model.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartieService {

  private final AmiService amiService;
  private final ContratService contratService;

  public PartieDetailDto getResume(Partie partie, int numJoueur) {
    PartieDetailDto dto = new PartieDetailDto();
    String contrat = contratService.getContratById(partie.getIdContrat()).getInitiale();
    StringBuilder sb = new StringBuilder();
    if (partie.getIdContrat() == 1) {
      dto.setIdPartie(partie.getIdContrat());
      sb.append(partie.getIdContrat().toString())
              .append(": ")
              .append(contrat);
      dto.setResume(sb.toString());
      return dto;
    }
    String preneur = amiService.findById(partie.getIdPreneur()).getNom();
    sb.append(preneur);
    if (numJoueur > 4) {
      String appelle = amiService.findById(partie.getIdAppel()).getNom();
      if (appelle.equals(preneur)) {
        sb.append("⚽");
      } else {
        sb.append("\uD83D\uDD17");
        sb.append(preneur);
      }
      sb.append(" ");
    }
    if (partie.getIsFait()) {
      sb.append("\uD83D\uDFE2");
    } else {
      sb.append("\uD83D\uDD34");
    }
    sb.append(partie.getPoints());

    if (partie.getIdPetitAuBout() > 0) {
      sb.append(" PAB:");
      sb.append(partie.getIdPetitAuBout());
    }

    if (partie.getIsChelem()) {
      sb.append(" \uD83D\uDC4DChélem");
    }

    if (partie.getIsCapotRenverse()) {
      sb.append(" ☠Capot renversé");
    }

    if (numJoueur == 6) {
      sb.append(" \uD83E\uDEA6:");
      String mort = amiService.findById(partie.getIdMort()).getNom();
      sb.append(mort);
    }

    return dto;

  }

}
