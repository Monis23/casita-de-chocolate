package com.casitachocolate.cafeteria.model;

public enum Categoria {
    ASEO("Aseo"),
    CONFITURAS("Confituras"),
    COMIDA("Comida");

    private final String nombre;

    Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}