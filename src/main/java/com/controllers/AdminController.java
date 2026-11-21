package com.amigoinvisible.app.controllers;

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