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

import boilerplate.serverapp.models.User;
import boilerplate.serverapp.models.dto.request.UserRequest;
import boilerplate.serverapp.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
  private UserService userService;

  @GetMapping
  public List<User> getAll() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public User getById(@PathVariable Integer id) {
    return userService.getById(id);
  }

  @PostMapping
  public User createUser(@RequestBody UserRequest userRequest) {
    return userService.create(userRequest);
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
    return userService.update(id, userRequest);
  }

  @DeleteMapping("/{id}")
  public Object delete(@PathVariable Integer id) {
    return userService.delete(id);
  }
}
