package org.example.retospringbootweb.excepcion;

public class RutaNotFoundException extends RuntimeException {
    public RutaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
