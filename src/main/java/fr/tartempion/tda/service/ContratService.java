package fr.tartempion.tda.service;

import fr.tartempion.tda.model.Contrat;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ContratService {

  private final List<Contrat> contrats = new ArrayList<>();

  @PostConstruct
  void init() {
    contrats.clear();
    Contrat contrat = new Contrat(1, "Belge", "\uD83C\uDDE7\uD83C\uDDEA", 0);
    contrats.add(contrat);
    contrat = new Contrat(2, "Petite", "P", 20);
    contrats.add(contrat);
    contrat = new Contrat(3, "Garde", "G", 50);
    contrats.add(contrat);
    contrat = new Contrat(4, "Garde sans", "GS", 100);
    contrats.add(contrat);
    contrat = new Contrat(5, "Garde contre", "GC", 200);
    contrats.add(contrat);
  }

  public List<Contrat> getContrats() {
    return contrats.stream()
            .sorted(Comparator.comparing(Contrat::getId, Comparator.nullsLast(Integer::compareTo)))
            .toList();
  }

  public Contrat getContratById(Integer id) {
    return contrats.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
  }
}
