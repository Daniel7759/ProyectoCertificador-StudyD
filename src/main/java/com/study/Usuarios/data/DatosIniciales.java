package com.study.Usuarios.data;

import com.study.Usuarios.model.EnumRoles;
import com.study.Usuarios.model.Role;
import com.study.Usuarios.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DatosIniciales(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Comprueba si los roles ya existen en la base de datos
        if (roleRepository.findByNombreRol(EnumRoles.ADMIN) == null) {
            // Si no existe, crea el rol ADMIN y guárdalo en la base de datos
            Role admin = Role.builder().nombreRol(EnumRoles.ADMIN).build();
            roleRepository.save(admin);
        }

        if (roleRepository.findByNombreRol(EnumRoles.USER) == null) {
            // Si no existe, crea el rol USER y guárdalo en la base de datos
            Role user = Role.builder().nombreRol(EnumRoles.USER).build();
            roleRepository.save(user);
        }
    }
}
