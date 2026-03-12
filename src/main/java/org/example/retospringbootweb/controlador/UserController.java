package org.example.retospringbootweb.controlador;

import org.example.retospringbootweb.modelo.User;
import org.example.retospringbootweb.persistencia.UserRepository; // Asegúrate de que tienes este import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Mostrar la página de Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Devolverá la plantilla login.html
    }

    // 2. Mostrar la página de Registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new User());
        return "registro"; // Devolverá la plantilla registro.html
    }

    // 3. Procesar el formulario de Registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute User usuario, Model model) {
        System.out.println("Intentando registrar a: " + usuario.getEmail()); // Log de prueba
        // Comprobar si el email ya existe en la base de datos
        if (userRepository.findByEmail(usuario.getEmail()).isPresent()) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        // Configuración de seguridad crítica antes de guardar
        usuario.setAdmin(false); // NUNCA fiarse de lo que viene del formulario, forzamos a false
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptar la contraseña

        // Guardar en MongoDB
//        userRepository.save(usuario);
        User guardado = userRepository.save(usuario);
        System.out.println("Usuario guardado con ID: " + guardado.getId()); // Si esto sale, está en la nube
        // Redirigir al login con un mensaje de éxito
        return "redirect:/login?registrado=true";
    }
}