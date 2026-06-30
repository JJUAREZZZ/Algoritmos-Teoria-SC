package quicklibrary.model;

public class Book implements Comparable<Book> {
    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int anio;
    private BookStatus estado;

    public Book(String codigo, String titulo, String autor, String categoria, int anio, BookStatus estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.estado = estado;
    }

