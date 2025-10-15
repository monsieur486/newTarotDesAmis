package fr.tartempion.tda.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Ami {

  private Integer id;
  private String nom;
  private String imageUrl;
  private Boolean isGuest;
}
