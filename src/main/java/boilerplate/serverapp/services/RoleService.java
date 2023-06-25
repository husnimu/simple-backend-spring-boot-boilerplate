package boilerplate.serverapp.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import boilerplate.serverapp.models.Privilege;
import boilerplate.serverapp.models.Role;
import boilerplate.serverapp.repositories.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
  private RoleRepository roleRepository;
  private PrivilegeService privilegeService;

  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  public Role getById(Integer id) {
    return roleRepository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not exists!!!"));
  }

  public Role create(Role role) {
    Set<Privilege> privileges = new HashSet<Privilege>();
    if (role.getPrivileges() != null) {
      role.getPrivileges().forEach(privilege -> {
        privilege = privilegeService.getById(privilege.getId());
        privileges.add(privilege);
      });
    }
    role.setPrivileges(privileges);

    roleRepository.save(role);
    return role;
  }

  public Role update(Integer id, Role role) {
    Role oldRole = getById(id);
    role.setId(id);

    List<Role> roles = new ArrayList<Role>();
    roles.add(role);

    Set<Privilege> privileges = new HashSet<Privilege>();

    if (role.getUsers() == null) {
      role.setUsers(oldRole.getUsers());
    }
    if (role.getPrivileges() == null) {
      role.setPrivileges(oldRole.getPrivileges());
    }
    role.getPrivileges().forEach(privilege -> {
      privilege = privilegeService.getById(privilege.getId());
      privileges.add(privilege);
    });
    role.setPrivileges(privileges);

    roleRepository.save(role);
    return role;
  }

  public Role delete(Integer id) {
    Role role = getById(id);
    try {
      role.setPrivileges(null);
      roleRepository.delete(role);
      return role;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete employee!!!");
    }
  }
}