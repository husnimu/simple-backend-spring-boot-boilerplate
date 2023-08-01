package boilerplate.serverapp.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import boilerplate.serverapp.models.Role;
import boilerplate.serverapp.models.User;
import boilerplate.serverapp.models.dto.request.UserRequest;
import boilerplate.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;
  private RoleService roleService;
  private ModelMapper modelMapper;

  private PasswordEncoder passwordEncoder;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getById(Integer id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  public User create(UserRequest userRequest) {
    User user = modelMapper.map(userRequest, User.class);

    List<Role> roles = new ArrayList<Role>();

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    // user.setRoles(user.getRoles());
    user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    if (user.getRoles() != null) {
      user.getRoles().forEach(role -> {
        role = roleService.getById(role.getId());
        roles.add(role);
      });
    }
    user.setRoles(roles);

    userRepository.save(user);
    return user;
  }

  public User update(Integer id, UserRequest userRequest) {
    User user = modelMapper.map(userRequest, User.class);

    User oldUser = getById(id);

    List<Role> roles = new ArrayList<Role>();

    if (userRepository.existsByIdNotAndUsername(id, user.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!!!");
    }

    user.setId(id);
    user.setPassword(oldUser.getPassword());
    user.setCreatedAt(oldUser.getCreatedAt());
    user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    if (user.getRoles() == null) {
      user.setRoles(oldUser.getRoles());
    } else {
      user.getRoles().forEach(role -> {
        role = roleService.getById(role.getId());
        roles.add(role);
      });
      user.setRoles(roles);
    }
    return userRepository.save(user);
  }

  public User delete(Integer id) {
    User user = getById(id);
    try {
      userRepository.delete(user);
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete user!!!");
    }
  }
}
