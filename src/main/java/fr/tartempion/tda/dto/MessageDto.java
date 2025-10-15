package fr.tartempion.tda.dto;

import fr.tartempion.tda.model.Reunion;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
  private int level;
  private Reunion reunion;
}
