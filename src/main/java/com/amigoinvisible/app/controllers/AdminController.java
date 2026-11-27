/*package com.amigoinvisible.app.controllers;

import com.amigoinvisible.app.services.SorteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SorteoService sorteoService;

    // Token de seguridad hardcodeado para que solo tú puedas dispararlo
    private final String SECRET_TOKEN = "sorteo-familia-2026";

    @PostMapping("/sortear")
    public ResponseEntity<String> lanzarSorteo(@RequestParam String token) {
        if (!SECRET_TOKEN.equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Acceso denegado: Token inválido.");
        }

        try {
            sorteoService.ejecutarSorteo();
            return ResponseEntity.ok("Sorteo ejecutado matemáticamente y correos enviados con éxito.");
        } catch (IllegalStateException e) {
            // Captura el error si hay menos de 3 participantes
            return ResponseEntity.badRequest().body("Error en la lógica: " + e.getMessage());
        } catch (Exception e) {
            // Captura cualquier fallo (ej. error de conexión SMTP)
            return ResponseEntity.internalServerError()
                                 .body("Error crítico durante la ejecución: " + e.getMessage());
        }
    }
}

*/

package com.amigoinvisible.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/test-email")
    public String testEmail() {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        // Escribe aquí el correo donde quieres recibir el simulacro
        mensaje.setTo("alejandroxd2004@gmail.com"); 
        mensaje.setSubject("🎄 Simulacro: Tu Amigo Invisible es...");

        // Plantilla de prueba simulando un emparejamiento real
        mensaje.setText("Hola Alejandro,\n\n" +
                "Esto es una prueba del sistema de distribución.\n\n" +
                "Te ha tocado regalar a: Usuario de Prueba\n" +
                "Su correo es: prueba@gmail.com\n\n" +
                "Su lista de deseos:\n" +
                "- Un libro de fantasía\n" +
                "- Una taza de café térmica\n\n" +
                "¡Feliz Navidad y que empiece la búsqueda del regalo perfecto!");

        mailSender.send(mensaje);
        return "Éxito: Simulacro enviado. Revisa tu bandeja de entrada para verificar el formato.";
    }
}