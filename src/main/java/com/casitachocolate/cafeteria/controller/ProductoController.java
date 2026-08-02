package com.casitachocolate.cafeteria.controller;

import com.casitachocolate.cafeteria.model.Producto;
import com.casitachocolate.cafeteria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String catalogo(Model model) {
        try {
            List<Producto> productos = productoService.listarDisponibles();
            model.addAttribute("productos", productos != null ? productos : List.of());
            return "catalogo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar el catálogo: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

@GetMapping("/admin")
public String adminPanel(Model model) {
    try {
        List<Producto> productos = productoService.listarTodos();
        // Si la lista es null, usamos lista vacía
        model.addAttribute("productos", productos != null ? productos : new ArrayList<>());
        return "admin";
    } catch (Exception e) {
        // Log del error en la consola de Render
        e.printStackTrace();
        // Pasamos una lista vacía y un mensaje de error a la vista
        model.addAttribute("productos", new ArrayList<>());
        model.addAttribute("error", "Error al cargar productos: " + e.getMessage());
        return "admin"; // Sigue mostrando admin.html pero con lista vacía
    }
}

    @GetMapping("/admin/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping("/admin/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        try {
            productoService.guardar(producto);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin?error=Error al guardar";
        }
    }

    @GetMapping("/admin/editar/{id}")
    public String editarProductoForm(@PathVariable Long id, Model model) {
        try {
            Producto producto = productoService.obtenerPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            model.addAttribute("producto", producto);
            return "formulario";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin?error=Producto no encontrado";
        }
    }

    @GetMapping("/admin/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin?error=Error al eliminar";
        }
    }

    @PostMapping("/admin/vender/{id}")
    public String venderProducto(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            productoService.venderProducto(id, cantidad);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin?error=Error al vender: " + e.getMessage();
        }
    }
}