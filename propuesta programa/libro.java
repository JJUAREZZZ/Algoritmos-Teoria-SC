package com.quicklibrary.models;

/**
 * Clase que representa un Libro en el sistema QuickLibrary.
 * Implementa Comparable para permitir su ordenamiento en el ABB
 */
public class Libro implements Comparable<Libro> {
    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int anio;
    private String estado; // Ejemplo: "Disponible", "Prestado"

    public Libro(String codigo, String titulo, String autor, String categoria, int anio) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.estado = "Disponible";
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Este método es vital para el Árbol Binario de Búsqueda.
     * Compara los códigos de los libros.
     */
    @Override
    public int compareTo(Libro otro) {
        return this.codigo.compareTo(otro.getCodigo());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%d) | Estado: %s", 
                codigo, titulo, autor, anio, estado);
    }
}
