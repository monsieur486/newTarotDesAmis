package fr.tartempion.tda.dto;

import fr.tartempion.tda.model.Reunion;
import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class MessageDto {
  private int level;
  private Reunion reunion;
}
