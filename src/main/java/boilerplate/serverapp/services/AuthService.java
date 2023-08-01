package boilerplate.serverapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import boilerplate.serverapp.models.User;
import boilerplate.serverapp.models.dto.request.LoginRequest;
import boilerplate.serverapp.models.dto.response.LoginResponse;
import boilerplate.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
  private UserRepository userRepository;
  private AuthenticationManager authenticationManager;
  private AppUserDetailService appUserDetailService;

  public LoginResponse login(LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
        loginRequest.getPassword());

    Authentication auth = authenticationManager.authenticate(authReq);
    SecurityContextHolder.getContext().setAuthentication(auth);

    User user = userRepository
        .findByUsernameOrEmail(loginRequest.getUsername(), loginRequest.getUsername())
        .get();

    UserDetails userDetails = appUserDetailService.loadUserByUsername(
        loginRequest.getUsername());

    List<String> authorities = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
        .collect(Collectors.toList());

    return new LoginResponse(user.getUsername(), user.getEmail(), authorities);
  }
}
