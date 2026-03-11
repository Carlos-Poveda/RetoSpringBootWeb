package org.example.retospringbootweb.persistencia;

import org.example.retospringbootweb.modelo.Ruta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends MongoRepository<Ruta,String> {
//    void deleteRutaByPropertiesNombre(String nombre);
    Ruta findFirstByPropertiesNombre(String nombre);

}
