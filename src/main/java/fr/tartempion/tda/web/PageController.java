package fr.tartempion.tda.web;

import fr.tartempion.tda.core.MessageStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
  private final MessageStore store;
  @Value("${APP_LOG_FOOTER:false}")
  private boolean logFooter;

  public PageController(MessageStore s) {
    this.store = s;
  }

  @GetMapping({"/"})
  public String index(Model model) {
    model.addAttribute("title", "TDA");
    model.addAttribute("currentMessage", store.get());
    model.addAttribute("logFooter", logFooter);
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }
}
