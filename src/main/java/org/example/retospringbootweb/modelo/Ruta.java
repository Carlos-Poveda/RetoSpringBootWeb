package org.example.retospringbootweb.modelo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "rutas")
public class Ruta {

    @Id
    private String id;
    private String type;
    private Geometry geometry;
    @Field("geometry_name")
    private String geometryName;
    private Properties properties;

    // Clase interna para la Geometría
    @Data
    public static class Geometry {
        private String type;
        private List<Double> coordinates; // [x, y]
    }

    // Clase interna para las Propiedades
    @Data
    public static class Properties {
        @Field("ogc_fid")
        private Integer ogcFid;

        private String punto;
        private String x;
        private String y;
        private String nombre;
        private String ubicacion;
        private String direccion;
        private String horario;
        private String telefono;
        private String web;

        @Field("mas_informacion")
        private String masInformacion;
    }
}