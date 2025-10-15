package fr.tartempion.tda.web;

import fr.tartempion.tda.core.MessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PartieController {
  private final MessageStore store;
  private final SimpMessagingTemplate broker;

  @PreAuthorize("hasRole('REDACTEUR')")
  @GetMapping("/partie")
  public String ajoutePartie(Model model, Authentication authentication, CsrfToken csrfToken) {
    model.addAttribute("title", "Partie");
    model.addAttribute("currentMessage", store.get());
    model.addAttribute("_csrf", csrfToken);
    return "partie";
  }
}
