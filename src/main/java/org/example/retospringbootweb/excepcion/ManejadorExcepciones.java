package org.example.retospringbootweb.excepcion;

import org.example.retospringbootweb.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

//@RestControllerAdvice
//public class ManejadorExcepciones {
//    // Manejar errores de "No encontrado" (404)
//    @ExceptionHandler(RutaNotFoundException.class)
//    public ResponseEntity<ErrorResponseDTO> handleNotFound(RutaNotFoundException ex) {
//        ErrorResponseDTO error = new ErrorResponseDTO(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(),
//                "No encontrado",
//                ex.getMessage()
//        );
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//}
