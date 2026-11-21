package com.amigoinvisible.app.controllers;

import com.amigoinvisible.app.models.Participante;
import com.amigoinvisible.app.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteRepository repository;

    @PostMapping
    public String registrarParticipante(Participante participante) {
        repository.save(participante);
        // IMPORTANTE: Redirigimos a la página física que acabamos de crear
        return "redirect:/gracias.html"; 
    }
}