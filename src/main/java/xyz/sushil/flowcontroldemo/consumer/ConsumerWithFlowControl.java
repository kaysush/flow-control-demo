package xyz.sushil.flowcontroldemo.consumer;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.cloud.spring.pubsub.support.DefaultSubscriberFactory;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "gcp.subscriber.pull", name = "enabled", havingValue = "true")
public class ConsumerWithFlowControl implements CommandLineRunner {

  private final PubSubSubscriberTemplate pubSubTemplate;
  private final String subscription;

  @Autowired
  public ConsumerWithFlowControl(PubSubSubscriberTemplate pubSubTemplate, @Value("${gcp.subscription}") String subscription) {
    this.pubSubTemplate = pubSubTemplate;
    this.subscription = subscription;
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Starting the consumer...");
    pubSubTemplate.subscribe(subscription, (pubsubMessage) -> {
      String message = pubsubMessage.getPubsubMessage().getData().toString(StandardCharsets.UTF_8);
      log.info("Got a message : " + message);
      try {
        /*
            Sleep to simulate a delay in message processing. This will cause outstanding messages to pile up and
            consumer will stop pulling in more messages.
         */
        Thread.sleep(11000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      pubsubMessage.ack();
    });
  }
}
