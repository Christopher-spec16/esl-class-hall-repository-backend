package com.cesde.eslclasshallrepositorybackend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Direccion {

    @Column(length = 120)
    private String calle;

    @Column(length = 80)
    private String ciudad;

    @Column(length = 20)
    private String codigoPostal;

    @Column(length = 80)
    private String pais;
}
