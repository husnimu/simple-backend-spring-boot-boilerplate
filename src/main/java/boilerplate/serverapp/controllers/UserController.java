package boilerplate.serverapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boilerplate.serverapp.models.Role;
import boilerplate.serverapp.models.User;
import boilerplate.serverapp.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping
public class UserController {
  private UserService userService;

  @GetMapping("/user")
  public List<User> getAll() {
    return userService.getAll();
  }

  @GetMapping("/user/{id}")
  public User getById(@PathVariable Integer id) {
    return userService.getById(id);
  }

  @GetMapping("/employee/{id}/user")
  public User getByEmployeeId(@PathVariable Integer id) {
    return userService.getById(id);
  }

  @GetMapping("/employee/{id}/user/role")
  public List<Role> getUserRole(@PathVariable Integer id) {
    return userService.getUserRoles(id);
  }

  // // with dto
  // @PostMapping("/user")
  // public User createUser(@RequestBody UserRequest userRequest) {
  // return userService.create(userRequest);
  // }

  @PostMapping("/user")
  public User createUser(@RequestBody User user) {
    return userService.create(user);
  }

  @PutMapping("/user/{id}")
  public User updateUser(@PathVariable Integer id, @RequestBody User user) {
    return userService.update(id, user);
  }

  @PutMapping("/employee/{id}/user")
  public User update(@PathVariable Integer id, @RequestBody User user) {
    return userService.updateByEmployee(id, user);
  }

  @DeleteMapping("/user/{id}")
  public Object delete(@PathVariable Integer id) {
    return userService.delete(id);
  }

  // @GetMapping("/user/search")
  // public List<User> search(@RequestParam String search) {
  // return userService.search(search);
  // }
}
