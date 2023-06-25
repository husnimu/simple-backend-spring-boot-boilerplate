package boilerplate.serverapp.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import boilerplate.serverapp.models.AppUserDetail;
import boilerplate.serverapp.models.User;
import boilerplate.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsernameOrEmployeeEmail(userName, userName);
    user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));
    return user.map(AppUserDetail::new).get();
  }

}
