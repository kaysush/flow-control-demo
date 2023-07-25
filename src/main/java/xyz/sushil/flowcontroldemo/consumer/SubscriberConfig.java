package xyz.sushil.flowcontroldemo.consumer;

import com.google.api.gax.batching.FlowControlSettings;
import org.springframework.context.annotation.Configuration;
import com.google.cloud.spring.pubsub.core.PubSubConfiguration;
import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.cloud.spring.pubsub.support.DefaultSubscriberFactory;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Configuration
public class SubscriberConfig {

  @Value("${spring.cloud.gcp.project-id}")
  public String projectId;

  @Value("${gcp.subscription}")
  public String subscription;

  @Value("${spring.cloud.gcp.pubsub.subscriber.flow-control.max-outstanding-element-count}")
  public long maxOutstandingElement;

  public PubSubConfiguration configuration() {
    PubSubConfiguration conf = new PubSubConfiguration();
    conf.initialize(projectId);
    return conf;
  }


  @Bean
  public PubSubSubscriberTemplate pubSubSubscriberTemplate(PubSubConfiguration configuration){
    DefaultSubscriberFactory factory = new DefaultSubscriberFactory(() -> projectId, configuration);
    factory.setFlowControlSettings(FlowControlSettings.newBuilder().setMaxOutstandingElementCount(maxOutstandingElement).build());
   return new PubSubSubscriberTemplate(factory);
  }

}
