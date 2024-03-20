package com.study.Usuarios.repository;

import com.study.Usuarios.model.EnumRoles;
import com.study.Usuarios.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByNombreRol(EnumRoles nombreRol);
}
