package fr.tartempion.tda.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartieDetailDto {
  private Integer idPartie;
  private String resume;
}
