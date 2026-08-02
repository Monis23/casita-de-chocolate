package com.casitachocolate.cafeteria.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String descripcion;    // Ej: "500 ml" o "Paquete 6 unidades"
    private String fotoUrl;        // Enlace de la imagen (ej: de Imgur)
    private Integer cantidad;      // Stock actual
    private Integer vendidos = 0;  // Cantidad vendida acumulada

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Transient  // No se guarda en la BD, se calcula en vivo
    private boolean disponible;

    // Método que actualiza la disponibilidad según la cantidad
    public void actualizarDisponibilidad() {
        this.disponible = (this.cantidad != null && this.cantidad > 0);
    }

    // Al setear la cantidad, se actualiza automáticamente la disponibilidad
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        actualizarDisponibilidad();
    }
}