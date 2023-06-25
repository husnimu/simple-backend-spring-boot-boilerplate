package boilerplate.serverapp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import boilerplate.serverapp.models.Employee;
import boilerplate.serverapp.models.Role;
import boilerplate.serverapp.models.User;
import boilerplate.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;
  private EmployeeService employeeService;
  // private ModelMapper modelMapper;

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

  public List<Role> getUserRoles(Integer id) {
    User user = getById(id);
    return user.getRoles();
  }

  public User create(User user) {
    if (user.getEmployee() != null) {
      Employee employee = user.getEmployee();
      employee.setUser(user);
      user.setEmployee(employee);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(user.getRoles());
    userRepository.save(user);
    return user;
  }

  public User update(Integer id, User user) {
    Employee employee = employeeService.getById(id);
    User oldUser = getById(id);
    if (userRepository.existsByIdNotAndUsername(id, user.getUsername()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!!!");
    user.setId(id);

    if (user.getEmployee() != null) {
      employee.setName(user.getEmployee().getName());
      employee.setEmail(user.getEmployee().getEmail());
      employee.setPhone(user.getEmployee().getPhone());
    }
    user.setPassword(oldUser.getPassword());
    if (user.getRoles() == null) {
      user.setRoles(oldUser.getRoles());
    }
    user.setEmployee(employee);
    return userRepository.save(user);
  }

  public User updateByEmployee(Integer id, User user) {
    Employee employee = employeeService.getById(id);
    if (userRepository.existsById(id)) {
      user.setId(id);
    }
    if (userRepository.existsByIdNotAndUsername(id, user.getUsername()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!!!");
    user.setEmployee(employee);
    return userRepository.save(user);
  }

  public User delete(Integer id) {
    User user = getById(id);
    try {
      if (user.getRoles() != null) {
        user.setRoles(null);
      }
      userRepository.delete(user);
      employeeService.delete(id);
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete user!!!");
    }
  }
}
