package boilerplate.serverapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @NotBlank(message = "Name is mandatory")
  private String name;

  @Column(nullable = true, unique = true)
  @NotBlank(message = "Email is mandatory")
  @Email
  private String email;

  @Column(nullable = true, length = 13)
  private String phone;

  @Column(nullable = true, length = 5)
  private Double salary;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Employee manager;

  @PrimaryKeyJoinColumn
  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
  private User user;
}
