package fr.tartempion.tda.web;

import fr.tartempion.tda.core.MessageStore;
import fr.tartempion.tda.dto.JoueurListeDto;
import fr.tartempion.tda.dto.MessageDto;
import fr.tartempion.tda.dto.PartieForm;
import fr.tartempion.tda.model.Reunion;
import fr.tartempion.tda.service.ContratService;
import fr.tartempion.tda.service.ReunionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartieController {
  private final MessageStore store;
  private final ReunionService reunionService;
  private final ContratService contratService;
  private final SimpMessagingTemplate broker; // STOMP

  @PreAuthorize("hasRole('REDACTEUR')")
  @GetMapping("/partie")
  public String ajoutePartie(Model model, CsrfToken csrfToken) {
    Reunion r = reunionService.getCurrentReunion();
    List<JoueurListeDto> joueurs = r.getListeJoueurs();

    PartieForm form = new PartieForm();
    form.setIdMort(0);
    form.setIdContrat(1);
    form.setIdPreneur(0);
    form.setIdAppel(0);
    form.setIsFait(true);
    form.setPoint(0);
    form.setIdPetitAuBout(0);
    form.setIsChelem(false);
    form.setIsCapotRenverse(false);

    model.addAttribute("title", "Partie");
    model.addAttribute("currentMessage", store.get());
    model.addAttribute("_csrf", csrfToken);
    model.addAttribute("joueurs", joueurs);
    model.addAttribute("nbJoueurs", joueurs.size());
    model.addAttribute("contrats", contratService.getContrats());
    model.addAttribute("form", form);
    return "partie";
  }

  @PostMapping("/partie")
  public String ajouterPartie(@ModelAttribute("form") PartieForm f) {
    reunionService.addPartie(f);
    MessageDto dto = store.get();
    System.out.println("Nombre de parties:" + dto.getReunion().getNbParties());
    broker.convertAndSend("/topic/messages", dto);
    return "redirect:/";
  }
}
