package com.amigoinvisible.app.services;

import com.amigoinvisible.app.models.Participante;
import com.amigoinvisible.app.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SorteoService {

    @Autowired
    private ParticipanteRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public String realizarSorteo() {
        List<Participante> participantes = (List<Participante>) repository.findAll();

        if (participantes.size() < 3) {
            return "Error: Hacen falta al menos 3 participantes para hacer un sorteo válido.";
        }

        // Mezclar la lista aleatoriamente
        Collections.shuffle(participantes);

        // Asignar en forma de anillo para garantizar que nadie se regale a sí mismo
        for (int i = 0; i < participantes.size(); i++) {
            Participante donante = participantes.get(i);
            // El receptor es el siguiente en la lista (y el último le regala al primero)
            Participante receptor = participantes.get((i + 1) % participantes.size());
            
            donante.setReceptorAsignado(receptor);
            
            // Enviar el correo definitivo
            enviarCorreo(donante, receptor);
        }

        // Guardar las asignaciones en la base de datos de Aiven para tener un respaldo
        repository.saveAll(participantes);

        return "¡Sorteo completado con éxito! Se han emparejado " + participantes.size() + " familiares y enviado los correos.";
    }

    private void enviarCorreo(Participante donante, Participante receptor) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(donante.getEmail());
        mensaje.setSubject("🎄 Tu Amigo Invisible de la Familia es...");

        String cuerpoMensaje = "¡Hola " + donante.getNombre() + "!\n\n"
                + "El sorteo se ha realizado. Este año te ha tocado regalar a:\n\n"
                + "🎁 Nombre: " + receptor.getNombre() + "\n\n"
                + "Aquí tienes su lista de deseos para que aciertes de pleno:\n"
                + receptor.getWishlist() + "\n\n"
                + "¡Guarda este correo en secreto y feliz búsqueda del regalo!";

        mensaje.setText(cuerpoMensaje);
        mailSender.send(mensaje);
    }
}