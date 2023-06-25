package boilerplate.serverapp.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import boilerplate.serverapp.models.Employee;
import boilerplate.serverapp.models.User;
import boilerplate.serverapp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
  private EmployeeRepository employeeRepository;
  private PasswordEncoder passwordEncoder;

  public Boolean emailValidation(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
      return false;
    return pat.matcher(email).matches();
  }

  public boolean emailChecker(Integer id, String email) {
    if (id != null) {
      return employeeRepository.existsByIdNotAndEmail(id, email);
    } else {
      return employeeRepository.existsByEmail(email);
    }
  }

  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  public Employee getById(Integer id) {
    return employeeRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!!"));
  }

  public Employee create(Employee employee) {
    if (!emailValidation(employee.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not valid!!!");
    }
    if (emailChecker(null, employee.getEmail()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!!!");

    if (employee.getUser() != null) {
      User user = employee.getUser();
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      employee.setUser(user);
      user.setEmployee(employee);
    }
    employeeRepository.save(employee);
    return employee;
  }

  public Employee update(Integer id, Employee employee) {
    if (!emailValidation(employee.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not valid!!!");
    }
    Employee oldEmployee = getById(id);
    employee.setId(id);
    if (emailChecker(id, employee.getEmail()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!!!");

    if (employee.getUser() != null) {
      User user = employee.getUser();
      employee.setUser(user);
      user.setId(id);
      user.setPassword(oldEmployee.getUser().getPassword());
      user.setRoles(oldEmployee.getUser().getRoles());
      user.setEmployee(employee);
    }
    employeeRepository.save(employee);
    return employee;
  }

  public Employee delete(Integer id) {
    Employee employee = getById(id);
    try {
      if (employee.getUser() != null) {
        if (employee.getUser().getRoles() != null) {
          employee.getUser().setRoles(null);
        }
      }
      employeeRepository.delete(employee);
      return employee;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete employee!!!");
    }
  }
}
