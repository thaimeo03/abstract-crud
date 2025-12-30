package com.tht.abstract_crud.model.base;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
  private int code;
  private String message;
  private String traceId;
  private ZonedDateTime responseTime;
  private T data;

  public static <T> BaseResponse<T> success(T data, HttpStatus code) {
    int codeResponse = code != null ? code.value() : HttpStatus.OK.value();
    String responseMessage = code != null ? code.getReasonPhrase() : HttpStatus.OK.getReasonPhrase();

    return BaseResponse.<T>builder()
        .code(codeResponse)
        .message(responseMessage)
        .traceId(generateTraceId())
        .responseTime(ZonedDateTime.now())
        .data(data)
        .build();
  }

  public static <T> BaseResponse<T> error(String message, HttpStatus code) {
    int codeResponse = code != null ? code.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();

    String responseMessage = "";
    if (message != null) {
      responseMessage = message;
    } else {
      responseMessage = code != null ? code.getReasonPhrase() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    return BaseResponse.<T>builder()
        .code(codeResponse)
        .message(responseMessage)
        .traceId(generateTraceId())
        .responseTime(ZonedDateTime.now())
        .build();
  }

  // Helpers
  private static String generateTraceId() {
    return java.util.UUID.randomUUID().toString();
  }
}
