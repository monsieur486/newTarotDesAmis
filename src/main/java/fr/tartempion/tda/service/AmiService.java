package fr.tartempion.tda.service;

import fr.tartempion.tda.model.Ami;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AmiService {

  private final List<Ami> amis = new ArrayList<>();

  @PostConstruct
  void init() {
    // Initialiser 6 amis : ami1..ami6 (non invités)
    amis.clear();
    amis.add(new Ami(1, "Bernard", "/images/trombi_bernard", true));
    amis.add(new Ami(2, "Dan", "/images/trombi_dan", false));
    amis.add(new Ami(3, "Etienne", "/images/trombi_etienne", false));
    amis.add(new Ami(4, "Fabrice", "/images/inconnu.png", false));
    amis.add(new Ami(5, "Fanny", "/images/inconnu.png", true));
    amis.add(new Ami(6, "JP", "/images/trombi_jp", false));
    amis.add(new Ami(7, "Laurent", "/images/trombi_laurent", false));
    amis.add(new Ami(8, "Guest", "/images/inconnu.png", true));
  }

  public List<Ami> getAmis() {
    return amis.stream()
            .sorted(Comparator
                    .comparing((Ami a) -> Boolean.TRUE.equals(a.getIsGuest()))
                    .thenComparing(a -> a.getNom() == null ? "" : a.getNom(), String.CASE_INSENSITIVE_ORDER)
            )
            .toList();
  }

  public Ami findById(Integer id) {
    if (id == null) return null;
    return amis.stream().filter(a -> id.equals(a.getId())).findFirst().orElse(null);
  }
}
