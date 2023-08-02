package com.pragma.usuario.micro.infrastructure.persistence.usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends CrudRepository<UserData, Integer> {
    Optional<UserData> findByEmail(String email);

}
