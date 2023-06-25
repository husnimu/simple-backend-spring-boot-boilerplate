package boilerplate.serverapp.models.dto.request;

import java.util.List;

import boilerplate.serverapp.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  private String username;
  private String password;
  private String name;
  private String email;
  private String phone;
  private Double salary;
  private Integer managerId;
  private List<Role> roles;
}
