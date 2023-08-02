package com.pragma.usuario.micro.infrastructure.persistence.rol;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDataRepository extends CrudRepository<RoleData, Integer> {
}
