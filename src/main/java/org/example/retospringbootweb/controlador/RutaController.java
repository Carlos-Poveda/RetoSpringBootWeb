package org.example.retospringbootweb.controlador;

import org.example.retospringbootweb.excepcion.RutaNotFoundException;
import org.example.retospringbootweb.modelo.Ruta;
import org.example.retospringbootweb.persistencia.RutaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class RutaController {

    private final RutaRepository rutaRepository;

    public RutaController(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    // Listado principal
    @GetMapping("/rutas")
    public String all_rutas(Model model) {
        List<Ruta> rutas = rutaRepository.findAll();
        model.addAttribute("rutas", rutas);
        return "rutas";
    }
    // RUTA POR ID (web)
    @GetMapping("/ruta_id/{id}")
    public String findById(@PathVariable String id, Model model) {
        Ruta ruta = rutaRepository.findById(id).orElse(null);
        if (ruta != null) {
            model.addAttribute("ruta", ruta);
            return "detalle_ruta"; // Nombre del nuevo archivo HTML
        } else {
            throw new RutaNotFoundException("No hay ninguna ruta con el id: " + id);
        }
    }

    // RUTA POR NOMBRE
    @GetMapping("/ruta_nombre")
    public Ruta findByNombre(@RequestParam String nombre) {
        Ruta ruta = rutaRepository.findFirstByPropertiesNombre(nombre);
        if (ruta != null) {
            return ruta;
        } else {
            throw new RutaNotFoundException("No hay ninguna ruta con el nombre: "+nombre);
        }
    }

    // AÑADIR RUTA
    @PostMapping("/add_ruta")
    public Ruta save(@RequestBody Ruta ruta) {
        return rutaRepository.save(ruta);
    }
    // BORRAR RUTA POR NOMBRE
    @DeleteMapping("/borrar_ruta_nombre")
    public void deleteByName(@RequestParam String nombre) {
        Ruta ruta = rutaRepository.findFirstByPropertiesNombre(nombre);
        if (ruta == null) {
            throw new RutaNotFoundException("No hay ninguna ruta con el nombre: "+nombre);
        }
        rutaRepository.delete(ruta);
    }
    // BORRAR RUTA POR ID
    @DeleteMapping("/borrar_ruta_id/{id}")
    public void delete(@PathVariable String id) {
        Ruta ruta = rutaRepository.findById(id).orElse(null);
        if (ruta == null) {
            throw new RutaNotFoundException("No hay ninguna ruta con el id: "+id);
        }
        rutaRepository.delete(ruta);
    }
    // Existe por id
    @GetMapping("/comprobar_id/{id}")
    public ResponseEntity<Void> checkExists(@PathVariable String id) {
        if (rutaRepository.existsById(id)) {
            return ResponseEntity.ok().build(); // Devuelve Status 200
        } else {
            throw new RutaNotFoundException("No hay ninguna ruta con el id: "+id);
        }
    }
    // Existe por nombre
    @GetMapping("/comprobar_nombre")
    public ResponseEntity<Void> checkExistsByNombre(@RequestParam String nombre) {
        Ruta ruta = rutaRepository.findFirstByPropertiesNombre(nombre);
        if (ruta != null) {
            return ResponseEntity.ok().build(); // Devuelve Status 200
            } else {
            throw new RutaNotFoundException("No hay ninguna ruta con el nombre: "+nombre);
        }
    }
    // Mostrar formulario nueva ruta
    @GetMapping("/rutas/nueva")
    public String formularioNuevaRuta(Model model) {
        return "nueva_ruta";
    }
    // Guardar nueva ruta (botón)
    @PostMapping("/rutas/guardar")
    public String guardarRuta(@ModelAttribute Ruta nuevaRuta) {
        rutaRepository.save(nuevaRuta);
        return "redirect:/rutas";
    }
    // Eliminar ruta (botón)
    @PostMapping("/rutas/eliminar/{id}")
    public String eliminarRuta(@PathVariable String id) {
        rutaRepository.deleteById(id);
        return "redirect:/rutas";
    }
}
