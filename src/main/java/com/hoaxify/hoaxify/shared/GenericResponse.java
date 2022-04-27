package com.hoaxify.hoaxify.shared;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse {

  private String Message;

  public GenericResponse(String message) {
    this.Message = message;
  }
}
