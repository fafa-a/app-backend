package com.hoaxify.hoaxify.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.hoaxify.hoaxify.error.ApiError;
import com.hoaxify.hoaxify.shared.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/api/1.0/users")
  public GenericResponse createUser(@Valid @RequestBody User user) {
    userService.save(user);
    return new GenericResponse("User saved");

  }

  @ExceptionHandler({ MethodArgumentNotValidException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiError handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(400, "Validation error", request.getServletPath());
    BindingResult result = ex.getBindingResult();
    Map<String, String> validationErrors = new HashMap<>();
    for (FieldError fieldError : result.getFieldErrors()) {
      validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    apiError.setValidationErrors(validationErrors);
    return apiError;
  }
}
