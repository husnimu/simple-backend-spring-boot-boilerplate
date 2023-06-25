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
  private String name;

  @Column(nullable = true, unique = true)
  private String email;

  @Column(nullable = true, length = 13)
  private String phone;

  @Column(nullable = true, length = 5)
  private Long salary;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;

  @PrimaryKeyJoinColumn
  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
  private User user;
}
