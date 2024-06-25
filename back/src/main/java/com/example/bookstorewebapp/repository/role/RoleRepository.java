package com.example.bookstorewebapp.repository.role;

import com.example.bookstorewebapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Long>,
        JpaSpecificationExecutor<Role> {
    Role findByRole(Role.RoleName roleName);
}
