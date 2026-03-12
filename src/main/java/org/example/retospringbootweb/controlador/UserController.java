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
    // Página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
    // Página de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new User());
        return "registro";
    }
    // Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute User usuario, Model model) {
        // Comprobar si el email ya existe en la base de datos
        if (userRepository.findByEmail(usuario.getEmail()).isPresent()) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }
        // Configuración de seguridad antes de guardar
        usuario.setAdmin(false);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptar la contraseña

        userRepository.save(usuario);
        return "redirect:/login?registrado=true";
    }
}