package boilerplate.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
  private String name;
  private String email;
  private String phone;
  private Double salary;
  private Integer managerId;
  private String username;
  private String password;
}
