package fr.tartempion.tda.config;

import org.springframework.lang.Nullable;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GlobalModelAttributes {

  @ModelAttribute
  public void addCsrfToken(Model model, @Nullable CsrfToken csrfToken) {
    model.addAttribute("_csrf", csrfToken);
  }
}

