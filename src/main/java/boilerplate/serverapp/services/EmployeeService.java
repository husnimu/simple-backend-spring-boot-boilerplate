package boilerplate.serverapp.services;

import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import boilerplate.serverapp.models.Employee;
import boilerplate.serverapp.models.User;
import boilerplate.serverapp.models.dto.request.EmployeeRequest;
import boilerplate.serverapp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
  private EmployeeRepository employeeRepository;
  private PasswordEncoder passwordEncoder;
  private ModelMapper modelMapper;

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

  public Employee create(EmployeeRequest employeeRequest) {
    Employee employee = modelMapper.map(employeeRequest, Employee.class);
    User user = modelMapper.map(employeeRequest, User.class);

    if (emailChecker(null, employee.getEmail()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!!!");

    if (employeeRequest.getManagerId() != null) {
      employee.setManager(getById(employeeRequest.getManagerId()));
    }
    if (user.getUsername() != null) {
      employee.setUser(user);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
      user.setEmployee(employee);
    }
    employeeRepository.save(employee);
    return employee;
  }

  public Employee update(Integer id, EmployeeRequest employeeRequest) {
    Employee employee = modelMapper.map(employeeRequest, Employee.class);
    User user = modelMapper.map(employeeRequest, User.class);

    Employee oldEmployee = getById(id);

    employee.setId(id);
    user.setId(id);

    if (employeeRequest.getManagerId() == id) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee cannot be it's own manager'");
    }

    if (emailChecker(id, employee.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!!!");
    }

    user.setPassword(oldEmployee.getUser().getPassword());
    user.setCreatedAt(oldEmployee.getUser().getCreatedAt());
    user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    if (user.getRoles() == null) {
      user.setRoles(oldEmployee.getUser().getRoles());
    }

    if (employeeRequest.getManagerId() != null) {
      employee.setManager(getById(employeeRequest.getManagerId()));
    } else {
      employee.setManager(oldEmployee.getManager());
    }
    employee.setUser(user);
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
