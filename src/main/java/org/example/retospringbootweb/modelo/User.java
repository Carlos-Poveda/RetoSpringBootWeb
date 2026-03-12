package org.example.retospringbootweb.modelo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private boolean admin;
}