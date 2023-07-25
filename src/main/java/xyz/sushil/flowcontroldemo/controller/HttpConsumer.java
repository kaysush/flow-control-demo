package xyz.sushil.flowcontroldemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HttpConsumer {

  @PostMapping("/pubsub/push")
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public void processPush(@RequestBody String body) {
    log.info(body);
  }
}
