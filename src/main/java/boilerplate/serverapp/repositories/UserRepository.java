package boilerplate.serverapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boilerplate.serverapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  public List<User> findByRolesId(Integer id);

  public boolean existsById(Integer id);

  public boolean existsByUsername(String username);

  public boolean existsByIdNotAndUsername(Integer id, String username);

  public Optional<User> findByUsernameOrEmployeeEmail(String username, String email);
}