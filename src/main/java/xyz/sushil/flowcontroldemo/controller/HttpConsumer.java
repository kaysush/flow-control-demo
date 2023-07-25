package xyz.sushil.flowcontroldemo.controller;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@ConditionalOnProperty(prefix = "gcp.subscriber.push", name = "enabled", havingValue = "true")
public class HttpConsumer {

  @PostMapping("/pubsub/push")
  public ResponseEntity<Void> processPush(@RequestBody PushEvent event) {
    String message  = new String(Base64.getDecoder().decode(event.getMessage().getData()));
    log.info("Got a message : " + message);
    int number = Integer.parseInt(message);
    if(number % 3 == 0)
      return ResponseEntity.internalServerError().build();
    else
      return ResponseEntity.ok().build();
  }
}
