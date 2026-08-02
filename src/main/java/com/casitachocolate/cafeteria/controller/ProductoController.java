package com.casitachocolate.cafeteria.controller;

import com.casitachocolate.cafeteria.model.Producto;
import com.casitachocolate.cafeteria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String catalogo(Model model) {
        model.addAttribute("productos", productoService.listarDisponibles());
        return "catalogo";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "admin";
    }

    @GetMapping("/admin/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    // ⚠️ UN SOLO MÉTODO PARA GUARDAR (sin imágenes)
    @PostMapping("/admin/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin";
    }

    @GetMapping("/admin/editar/{id}")
    public String editarProductoForm(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "formulario";
    }

    @GetMapping("/admin/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/vender/{id}")
    public String venderProducto(@PathVariable Long id, @RequestParam int cantidad) {
        productoService.venderProducto(id, cantidad);
        return "redirect:/admin";
    }
}