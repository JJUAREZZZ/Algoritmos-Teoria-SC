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
    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public BookStatus getEstado() {
        return estado;
    }

    public void setEstado(BookStatus estado) {
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return this.estado == BookStatus.DISPONIBLE;
    }

