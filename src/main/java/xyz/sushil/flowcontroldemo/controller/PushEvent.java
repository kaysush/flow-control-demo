package xyz.sushil.flowcontroldemo.controller;

import com.google.auto.value.extension.toprettystring.ToPrettyString;
import java.util.Map;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PushEvent {

  private Message message;

  @Data
  static class Message {

    private String data;
    private Map<String, String> attributes;

    private String messageId;

    private String publishTime;
  }
}
