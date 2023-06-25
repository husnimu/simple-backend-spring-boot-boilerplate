package boilerplate.serverapp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boilerplate.serverapp.models.dto.request.LoginRequest;
import boilerplate.serverapp.models.dto.response.LoginResponse;
import boilerplate.serverapp.services.AuthService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping
public class AuthController {
  private AuthService authService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest LoginRequest) {
    return authService.login(LoginRequest);
  }
}
