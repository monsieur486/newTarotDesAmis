package fr.tartempion.tda.core;

import fr.tartempion.tda.dto.MessageDto;
import fr.tartempion.tda.model.Reunion;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class MessageStore {
  private final AtomicReference<MessageDto> current = new AtomicReference<>(new MessageDto(1, new Reunion()));

  public MessageDto get() {
    return current.get();
  }

  public void set(MessageDto v) {
    current.set(v);
  }
}
