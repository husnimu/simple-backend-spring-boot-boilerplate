package boilerplate.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boilerplate.serverapp.models.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
}
