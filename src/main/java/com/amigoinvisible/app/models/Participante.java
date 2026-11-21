package com.amigoinvisible.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "participantes")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String wishlist;

    // Autorreferencia: a quién le regala esta persona
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_asignado_id")
    private Participante receptorAsignado;

    // Constructores vacíos exigidos por JPA
    public Participante() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWishlist() { return wishlist; }
    public void setWishlist(String wishlist) { this.wishlist = wishlist; }

    public Participante getReceptorAsignado() { return receptorAsignado; }
    public void setReceptorAsignado(Participante receptorAsignado) { this.receptorAsignado = receptorAsignado; }
}