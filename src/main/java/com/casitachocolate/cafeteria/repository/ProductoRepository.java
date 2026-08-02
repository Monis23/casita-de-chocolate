package com.casitachocolate.cafeteria.repository;

import com.casitachocolate.cafeteria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí no necesitamos métodos extra, usaremos los que ya trae JpaRepository.
    // El filtro de disponibles lo haremos en el Servicio.
}