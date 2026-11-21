package com.amigoinvisible.app.repositories; // Ajusta el paquete

import com.amigoinvisible.app.models.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    // Aquí podríamos añadir búsquedas personalizadas como:
    // Optional<Participante> findByEmail(String email);
}