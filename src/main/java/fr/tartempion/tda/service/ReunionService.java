package fr.tartempion.tda.service;

import fr.tartempion.tda.core.MessageStore;
import fr.tartempion.tda.dto.PartieDetailDto;
import fr.tartempion.tda.dto.PartieForm;
import fr.tartempion.tda.model.Reunion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReunionService {

  private final MessageStore store;
  private final PartieService partieService;

  public Reunion getCurrentReunion(){
    return store.get().getReunion();
  }

  public List<PartieDetailDto> resumesParties(){
    Reunion reunion = getCurrentReunion();
    int numJoueurs = reunion.getParties().size();
    return reunion.getParties().stream()
            .map(partie -> partieService.getResume(partie, numJoueurs))
            .toList();
  }

  public void addPartie(PartieForm partieForm) {
    Reunion reunion = getCurrentReunion();
    reunion.addPartie(partieForm.toEntity());
  }
}
