package fr.tartempion.tda.web;

import fr.tartempion.tda.core.MessageStore;
import fr.tartempion.tda.dto.MessageDto;
import fr.tartempion.tda.model.Ami;
import fr.tartempion.tda.model.Reunion;
import fr.tartempion.tda.service.AmiService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EditController {
  private final MessageStore store;
  private final SimpMessagingTemplate broker;
  private final AmiService amiService;

  public EditController(MessageStore s, SimpMessagingTemplate b, AmiService amiService) {
    this.store = s;
    this.broker = b;
    this.amiService = amiService;
  }

  @PreAuthorize("hasRole('REDACTEUR')")
  @GetMapping("/edit")
  public String editForm(Model model, Authentication authentication, CsrfToken csrfToken) {
    model.addAttribute("title", "Éditer - TDA");
    model.addAttribute("currentMessage", store.get());
    boolean isRedacteur = authentication != null && authentication.getAuthorities().stream()
            .anyMatch(a -> "ROLE_REDACTEUR".equals(a.getAuthority()));
    model.addAttribute("isRedacteur", isRedacteur);
    model.addAttribute("_csrf", csrfToken);
    // Liste des amis (utile au niveau 1)
    model.addAttribute("amis", amiService.getAmis());
    return "edit";
  }

  @PreAuthorize("hasRole('REDACTEUR')")
  @PostMapping("/edit")
  public String save(@RequestParam("level") int level) {
    int clamped = Math.max(0, Math.min(2, level));
    MessageDto dto = new MessageDto(clamped, store.get().getReunion() == null ? new Reunion() : store.get().getReunion());
    store.set(dto);
    broker.convertAndSend("/topic/messages", dto);
    return "redirect:/edit";
  }

  @PreAuthorize("hasRole('REDACTEUR')")
  @PostMapping("/edit/create")
  public String createReunion(@RequestParam(name = "amis", required = false) List<Integer> amiIds,
                              Model model,
                              CsrfToken csrfToken) {
    List<Integer> ids = amiIds != null ? amiIds : new ArrayList<>();
    if (ids.size() < 4 || ids.size() > 6) {
      model.addAttribute("title", "Éditer - TDA");
      model.addAttribute("currentMessage", store.get());
      model.addAttribute("_csrf", csrfToken);
      model.addAttribute("amis", amiService.getAmis());
      model.addAttribute("selectedIds", ids);
      model.addAttribute("formError", "Veuillez sélectionner entre 4 et 6 amis.");
      return "edit";
    }
    Reunion r = store.get().getReunion();
    if (r == null) r = new Reunion();
    r.clearReunion();
    for (Integer id : ids) {
      Ami a = amiService.findById(id);
      if (a != null) r.addJoueur(a);
    }
    MessageDto dto = new MessageDto(2, r);
    store.set(dto);
    broker.convertAndSend("/topic/messages", dto);
    return "redirect:/edit";
  }

  @PreAuthorize("hasRole('REDACTEUR')")
  @PostMapping("/edit/finish")
  public String finishPartie() {
    MessageDto curr = store.get();
    MessageDto dto = new MessageDto(3, curr.getReunion());
    store.set(dto);
    broker.convertAndSend("/topic/messages", dto);
    return "redirect:/edit";
  }

  @PreAuthorize("hasRole('REDACTEUR')")
  @PostMapping("/edit/reset")
  public String resetPartie() {
    MessageDto curr = store.get();
    Reunion r = curr.getReunion();
    if (r == null) r = new Reunion();
    r.clearReunion();
    MessageDto dto = new MessageDto(1, r);
    store.set(dto);
    broker.convertAndSend("/topic/messages", dto);
    return "redirect:/edit";
  }
}
