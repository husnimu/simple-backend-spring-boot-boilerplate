package boilerplate.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boilerplate.serverapp.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}