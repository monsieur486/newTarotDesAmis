package fr.tartempion.tda.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoueurListeDto {
  private Integer id;
  private String nom;
}
