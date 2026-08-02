package com.casitachocolate.cafeteria.service;

import com.casitachocolate.cafeteria.model.Categoria;
import com.casitachocolate.cafeteria.model.Producto;
import com.casitachocolate.cafeteria.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        List<Producto> productos = productoRepository.findAll();
        productos.forEach(Producto::actualizarDisponibilidad);
        return productos;
    }

    public List<Producto> listarDisponibles() {
        return listarTodos().stream()
                .filter(Producto::isDisponible)
                .toList();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        producto.actualizarDisponibilidad();
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public void venderProducto(Long id, int cantidadVendida) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getCantidad() < cantidadVendida) {
            throw new RuntimeException("No hay suficiente stock. Solo quedan " + producto.getCantidad());
        }

        producto.setCantidad(producto.getCantidad() - cantidadVendida);
        producto.setVendidos(producto.getVendidos() + cantidadVendida);
        producto.actualizarDisponibilidad();
        productoRepository.save(producto);
    }

    // Método corregido para listar por categoría
    public List<Producto> listarPorCategoria(String categoriaNombre) {
        return listarTodos().stream()
                .filter(p -> p.isDisponible() && p.getCategoria() != null 
                        && p.getCategoria().name().equalsIgnoreCase(categoriaNombre))
                .collect(Collectors.toList());
    }
}