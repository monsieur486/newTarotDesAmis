package fr.tartempion.tda.model;

import fr.tartempion.tda.dto.JoueurListeDto;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Reunion {
  private List<Ami> joueurs = new ArrayList<>();
  private List<Partie> parties = new ArrayList<>();
  private Boolean isOver = false;

  public void addJoueur(Ami a) {
    joueurs.add(a);
  }

  public void addPartie(Partie p) {
    p.setId(parties.size() + 1);
    parties.add(p);
  }

  public void clearReunion() {
    joueurs.clear();
    parties.clear();
    isOver = false;
  }

  public void setOver() {
    isOver = true;
  }

  public List<JoueurListeDto> getListeJoueurs() {
    return joueurs.stream()
            .sorted(Comparator.comparing(
                    (Ami a) -> a.getNom() == null ? "" : a.getNom(),
                    String.CASE_INSENSITIVE_ORDER
            ))
            .map(a -> JoueurListeDto.builder()
                    .id(a.getId())
                    .nom(a.getNom())
                    .build())
            .toList();
  }

  public List<Partie> getListeParties() {
    return parties.stream()
            .sorted(Comparator.comparing(Partie::getId, Comparator.nullsLast(Integer::compareTo)))
            .toList();
  }

  public Ami getJoueurById(Integer id) {
    return joueurs.stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElse(null);
  }

  public Partie getPartieById(Integer id) {
    return parties.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
  }

  public Integer getNbJoueurs() {
    return joueurs.size();
  }

  public Integer getNbParties() {
    return parties.size();
  }
}
