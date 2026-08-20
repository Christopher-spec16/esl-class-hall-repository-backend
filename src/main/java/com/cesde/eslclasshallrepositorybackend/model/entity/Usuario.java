package com.cesde.eslclasshallrepositorybackend.model.entity;

import java.util.HashSet;
import java.util.Set;

import com.cesde.eslclasshallrepositorybackend.model.base.BaseEntity;
import com.cesde.eslclasshallrepositorybackend.model.embeddable.Direccion;
import com.cesde.eslclasshallrepositorybackend.model.enums.EstadoUsuario;
import com.cesde.eslclasshallrepositorybackend.model.enums.RolUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "usuarios_migracion")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends BaseEntity {

    protected Usuario(String nombre, String email, String passwordHash, RolUsuario rol) {
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.estado = EstadoUsuario.ACTIVE;
        this.rol = rol;
    }

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, length = 20, updatable = false)
    private RolUsuario rol;

    @Embedded
    private Direccion direccion;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerfilUsuario perfil;

    @OneToMany(mappedBy = "solicitadoPor", orphanRemoval = false)
    private Set<SolicitudRecurso> solicitudes = new HashSet<>();

    @OneToMany(mappedBy = "creadoPor", orphanRemoval = false)
    private Set<Recurso> recursosCreados = new HashSet<>();

    @PrePersist
    @PreUpdate
    protected void ensureRoleMatchesSubtype() {
        if (this instanceof Admin) {
            this.rol = RolUsuario.ADMIN;
            return;
        }

        if (this instanceof Docente) {
            this.rol = RolUsuario.DOCENTE;
        }
    }
}
