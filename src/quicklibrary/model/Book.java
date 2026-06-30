package quicklibrary.model;
//clase para represerta el libro de nuestra biblioteca 
public class Book implements Comparable<Book> {
    //atributos del libro
    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int anio;
    private BookStatus estado;
//Constructor para inicializar nuestrar variables
    public Book(String codigo, String titulo, String autor, String categoria, int anio, BookStatus estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.estado = estado;
    }
    //metodos que nos permiten modificar y que nos retornen el codigo, titulo, autor, categoria, anio(año), estado del libro
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

    @Override
    public int compareTo(Book otroLibro) {
        if (otroLibro == null) {
            return 1;
        }
        return this.codigo.compareToIgnoreCase(otroLibro.codigo);
    }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + estado + ")";
    }

    public String toCsv() {
        return limpiarCsv(codigo) + "," + limpiarCsv(titulo) + "," + limpiarCsv(autor) + "," +
                limpiarCsv(categoria) + "," + anio + "," + estado;
    }

    public static Book fromCsv(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length < 6) {
            return null;
        }
        int anioLibro;
        try {
            anioLibro = Integer.parseInt(partes[4].trim());
        } catch (NumberFormatException e) {
            anioLibro = 0;
        }
        return new Book(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                anioLibro, BookStatus.fromText(partes[5]));
    }

    private String limpiarCsv(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace(",", " ").trim();
    }
}

