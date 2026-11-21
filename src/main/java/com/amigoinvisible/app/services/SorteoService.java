package com.amigoinvisible.app.services;

import com.amigoinvisible.app.models.Participante;
import com.amigoinvisible.app.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SorteoService {

    private static final Logger logger = LoggerFactory.getLogger(SorteoService.class);
    
    @Autowired
    private ParticipanteRepository repository;
    
    @Autowired
    private EmailService emailService;

    @Transactional
    public void ejecutarSorteo() {
        List<Participante> participantes = repository.findAll();

        if (participantes.size() < 3) {
            throw new IllegalStateException("Se requieren al menos 3 participantes para el sorteo.");
        }

        Collections.shuffle(participantes);
        
        for (int i = 0; i < participantes.size(); i++) {
            Participante donante = participantes.get(i);
            int indiceReceptor = (i + 1) % participantes.size();
            Participante receptor = participantes.get(indiceReceptor);
            donante.setReceptorAsignado(receptor);
        }

        repository.saveAll(participantes);
        logger.info("Emparejamientos guardados en base de datos. Iniciando envío de correos...");

        // Bucle de envío ciego
        for (Participante p : participantes) {
            emailService.enviarCorreoAsignacion(p, p.getReceptorAsignado());
        }
        
        logger.info("Sorteo finalizado por completo.");
    }
}