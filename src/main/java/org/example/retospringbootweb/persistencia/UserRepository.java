package org.example.retospringbootweb.persistencia;

import org.example.retospringbootweb.modelo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    //    void deleteRutaByPropertiesNombre(String nombre);
    Optional<User> findByEmail(String email);

}
