package com.amigoinvisible.app.services;

import com.amigoinvisible.app.models.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoAsignacion(Participante donante, Participante receptor) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(donante.getEmail());
            mensaje.setSubject("🎄 Tu Amigo Invisible Familiar es... 🎅");
            
            String cuerpo = "¡Hola " + donante.getNombre() + "!\n\n"
                    + "El sorteo del Amigo Invisible de la familia se ha realizado con éxito.\n\n"
                    + "Te ha tocado regalar a: " + receptor.getNombre().toUpperCase() + "\n\n"
                    + "Para echarte una mano, aquí tienes su lista de deseos/ideas:\n\n"
                    + receptor.getWishlist() + "\n\n"
                    + "Recuerda mantenerlo en secreto. ¡Feliz Navidad!";
                    
            mensaje.setText(cuerpo);
            
            mailSender.send(mensaje);
            logger.info("OK - Correo enviado a: {}", donante.getEmail());
            
        } catch (Exception e) {
            logger.error("FALLO - No se pudo enviar el correo a: {}. Error: {}", donante.getEmail(), e.getMessage());
        }
    }
}