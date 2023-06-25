package boilerplate.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boilerplate.serverapp.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  public boolean existsByEmail(String email);

  public boolean existsByIdNotAndEmail(Integer id, String email);
}
