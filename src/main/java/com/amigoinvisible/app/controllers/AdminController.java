package com.amigoinvisible.app.controllers;

import com.amigoinvisible.app.services.SorteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SorteoService sorteoService;

    // Puedes mantener el test-email si quieres hacer más pruebas previas
    
    @GetMapping("/ejecutar-sorteo-definitivo")
    public String lanzarSorteo() {
        return sorteoService.realizarSorteo();
    }
}