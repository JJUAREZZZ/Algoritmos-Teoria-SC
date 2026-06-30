package quicklibrary.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import quicklibrary.exception.BookNotFoundException;
import quicklibrary.exception.DuplicateKeyException;
import quicklibrary.exception.ValidationException;
import quicklibrary.model.Book;
import quicklibrary.model.BookStatus;
import quicklibrary.model.LoanRecord;
import quicklibrary.model.LoanRequest;
import quicklibrary.persistence.FileManager;
import quicklibrary.structures.list.CustomLinkedList;
import quicklibrary.structures.queue.CustomQueue;
import quicklibrary.structures.stack.CustomStack;
import quicklibrary.structures.tree.CustomAVLTree;

public class LibraryController {
    private CustomAVLTree<Book> arbolLibros;
    private CustomQueue<LoanRequest> colaSolicitudes;
    private CustomStack<LoanRecord> historialPrestamos;

    private File carpetaDatos;
    private File archivoLibros;
    private File archivoSolicitudes;
    private File archivoHistorial;
    private FileManager gestorArchivos;

    public LibraryController() {
        this.arbolLibros = new CustomAVLTree<Book>();
        this.colaSolicitudes = new CustomQueue<LoanRequest>();
        this.historialPrestamos = new CustomStack<LoanRecord>();
        this.carpetaDatos = new File("data");
        this.archivoLibros = new File(carpetaDatos, "libros.csv");
        this.archivoSolicitudes = new File(carpetaDatos, "solicitudes.csv");
        this.archivoHistorial = new File(carpetaDatos, "historial.csv");
        this.gestorArchivos = new FileManager();
        prepararArchivos();
        cargarLibros();
        cargarSolicitudes();
        cargarHistorial();
    }

    private void prepararArchivos() {
        if (!carpetaDatos.exists()) {
            carpetaDatos.mkdirs();
        }
    }

    public void registrarLibro(String codigo, String titulo, String autor, String categoria, String anioTexto, BookStatus estado)
            throws ValidationException, DuplicateKeyException {
        String codigoLimpio = validarTexto(codigo, "codigo");
        String tituloLimpio = validarTexto(titulo, "titulo");
        String autorLimpio = validarTexto(autor, "autor");
        String categoriaLimpia = validarTexto(categoria, "categoria");
        int anio = validarAnio(anioTexto);
        if (estado == null) {
            estado = BookStatus.DISPONIBLE;
        }
        Book nuevoLibro = new Book(codigoLimpio, tituloLimpio, autorLimpio, categoriaLimpia, anio, estado);
        arbolLibros.insert(nuevoLibro);
        guardarLibros();
    }

    public void modificarLibro(String codigo, String titulo, String autor, String categoria, String anioTexto, BookStatus estado)
            throws ValidationException, BookNotFoundException {
        String codigoLimpio = validarTexto(codigo, "codigo");
        String tituloLimpio = validarTexto(titulo, "titulo");
        String autorLimpio = validarTexto(autor, "autor");
        String categoriaLimpia = validarTexto(categoria, "categoria");
        int anio = validarAnio(anioTexto);
        if (estado == null) {
            estado = BookStatus.DISPONIBLE;
        }
        Book libroEncontrado = buscarLibroPorCodigo(codigoLimpio);
        libroEncontrado.setTitulo(tituloLimpio);
        libroEncontrado.setAutor(autorLimpio);
        libroEncontrado.setCategoria(categoriaLimpia);
        libroEncontrado.setAnio(anio);
        libroEncontrado.setEstado(estado);
        guardarLibros();
    }

    public void eliminarLibro(String codigo) throws ValidationException, BookNotFoundException {
        String codigoLimpio = validarTexto(codigo, "codigo");
        Book llave = new Book(codigoLimpio, "", "", "", 0, BookStatus.DISPONIBLE);
        arbolLibros.delete(llave);
        guardarLibros();
    }

    public Book buscarLibroPorCodigo(String codigo) throws ValidationException, BookNotFoundException {
        String codigoLimpio = validarTexto(codigo, "codigo");
        Book llave = new Book(codigoLimpio, "", "", "", 0, BookStatus.DISPONIBLE);
        return arbolLibros.search(llave);
    }

    private Book buscarLibroPorCodigoSinExcepcion(String codigo) {
        if (codigo == null) {
            return null;
        }
        Book llave = new Book(codigo.trim(), "", "", "", 0, BookStatus.DISPONIBLE);
        return arbolLibros.searchOrNull(llave);
    }

    public CustomLinkedList<Book> obtenerTodosLibros() {
        return arbolLibros.inOrder();
    }

    public CustomLinkedList<Book> obtenerLibrosDisponibles() {
        CustomLinkedList<Book> todos = obtenerTodosLibros();
        CustomLinkedList<Book> resultado = new CustomLinkedList<Book>();
        int i;
        for (i = 0; i < todos.size(); i++) {
            Book libro = todos.get(i);
            if (libro.getEstado() == BookStatus.DISPONIBLE) {
                resultado.addLast(libro);
            }
        }
        return resultado;
    }

    public CustomLinkedList<Book> obtenerLibrosPrestados() {
        CustomLinkedList<Book> todos = obtenerTodosLibros();
        CustomLinkedList<Book> resultado = new CustomLinkedList<Book>();
        int i;
        for (i = 0; i < todos.size(); i++) {
            Book libro = todos.get(i);
            if (libro.getEstado() == BookStatus.PRESTADO) {
                resultado.addLast(libro);
            }
        }
        return resultado;
    }

    public CustomLinkedList<Book> buscarLibrosPorTitulo(String titulo) throws ValidationException {
        String texto = validarTexto(titulo, "titulo a buscar");
        CustomLinkedList<Book> todos = obtenerTodosLibros();
        CustomLinkedList<Book> resultado = new CustomLinkedList<Book>();
        int i;
        for (i = 0; i < todos.size(); i++) {
            Book libro = todos.get(i);
            if (contieneTexto(libro.getTitulo(), texto)) {
                resultado.addLast(libro);
            }
        }
        return resultado;
    }

    public CustomLinkedList<Book> buscarLibrosPorAutor(String autor) throws ValidationException {
        String texto = validarTexto(autor, "autor a buscar");
        CustomLinkedList<Book> todos = obtenerTodosLibros();
        CustomLinkedList<Book> resultado = new CustomLinkedList<Book>();
        int i;
        for (i = 0; i < todos.size(); i++) {
            Book libro = todos.get(i);
            if (contieneTexto(libro.getAutor(), texto)) {
                resultado.addLast(libro);
            }
        }
        return resultado;
    }

    public CustomLinkedList<Book> buscarLibrosPorCategoria(String categoria) throws ValidationException {
        String texto = validarTexto(categoria, "categoria a buscar");
        CustomLinkedList<Book> todos = obtenerTodosLibros();
        CustomLinkedList<Book> resultado = new CustomLinkedList<Book>();
        int i;
        for (i = 0; i < todos.size(); i++) {
            Book libro = todos.get(i);
            if (contieneTexto(libro.getCategoria(), texto)) {
                resultado.addLast(libro);
            }
        }
        return resultado;
    }

    public CustomLinkedList<Book> obtenerLibrosOrdenadosPorTitulo() {
        CustomLinkedList<Book> todos = obtenerTodosLibros();
        CustomLinkedList<Book> ordenados = new CustomLinkedList<Book>();
        int i;
        for (i = 0; i < todos.size(); i++) {
            ordenados.insertOrdered(todos.get(i));
        }
        return ordenados;
    }

    public void registrarSolicitud(String codigoEstudiante, String nombreEstudiante, String codigoLibro)
            throws ValidationException {
        String codigoEstudianteLimpio = validarTexto(codigoEstudiante, "codigo del estudiante");
        String nombreEstudianteLimpio = validarTexto(nombreEstudiante, "nombre del estudiante");
        String codigoLibroLimpio = validarTexto(codigoLibro, "codigo del libro");
        LoanRequest solicitud = new LoanRequest(codigoEstudianteLimpio, nombreEstudianteLimpio,
                codigoLibroLimpio, LocalDate.now());
        colaSolicitudes.enqueue(solicitud);
        guardarSolicitudes();
    }

    public LoanRequest consultarSiguienteSolicitud() {
        return colaSolicitudes.peek();
    }

    public String atenderSiguienteSolicitud() {
        LoanRequest solicitud = colaSolicitudes.peek();
        if (solicitud == null) {
            return "No existen solicitudes pendientes.";
        }

        Book libro = buscarLibroPorCodigoSinExcepcion(solicitud.getCodigoLibro());
        String resultado;
        String tituloLibro = "No encontrado";

        if (libro == null) {
            resultado = "Solicitud atendida: el libro con codigo " + solicitud.getCodigoLibro() + " no existe.";
        } else if (!libro.estaDisponible()) {
            tituloLibro = libro.getTitulo();
            resultado = "Solicitud atendida: el libro '" + libro.getTitulo() + "' no esta disponible.";
        } else {
            tituloLibro = libro.getTitulo();
            libro.setEstado(BookStatus.PRESTADO);
            resultado = "Prestamo realizado correctamente para " + solicitud.getNombreEstudiante() +
                    ". Libro: " + libro.getTitulo() + ".";
        }

        colaSolicitudes.dequeue();
        LoanRecord registro = new LoanRecord(solicitud.getCodigoEstudiante(), solicitud.getNombreEstudiante(),
                solicitud.getCodigoLibro(), tituloLibro, solicitud.getFechaSolicitud(), LocalDate.now(), resultado);
        historialPrestamos.push(registro);
        guardarLibros();
        guardarSolicitudes();
        guardarHistorial();
        return resultado;
    }

    public String registrarDevolucion(String codigoLibro) throws ValidationException, BookNotFoundException {
        String codigoLimpio = validarTexto(codigoLibro, "codigo del libro");
        Book libro = buscarLibroPorCodigo(codigoLimpio);
        if (libro.getEstado() == BookStatus.DISPONIBLE) {
            return "El libro ya se encontraba disponible.";
        }
        libro.setEstado(BookStatus.DISPONIBLE);
        guardarLibros();
        return "Devolucion registrada correctamente para el libro: " + libro.getTitulo() + ".";
    }

    public CustomLinkedList<LoanRequest> obtenerSolicitudesPendientes() {
        return colaSolicitudes.toList();
    }

    public CustomLinkedList<LoanRecord> obtenerHistorialPrestamos() {
        return historialPrestamos.toList();
    }

    public int contarLibros() {
        return arbolLibros.size();
    }

    public int contarLibrosDisponibles() {
        return obtenerLibrosDisponibles().size();
    }

    public int contarLibrosPrestados() {
        return obtenerLibrosPrestados().size();
    }

    public int contarSolicitudesPendientes() {
        return colaSolicitudes.size();
    }

    public int contarHistorial() {
        return historialPrestamos.size();
    }

    public String generarReporteTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("QUICKLIBRARY - REPORTE GENERAL\n");
        sb.append("================================\n");
        sb.append("Total de libros: ").append(contarLibros()).append("\n");
        sb.append("Libros disponibles: ").append(contarLibrosDisponibles()).append("\n");
        sb.append("Libros prestados: ").append(contarLibrosPrestados()).append("\n");
        sb.append("Solicitudes pendientes: ").append(contarSolicitudesPendientes()).append("\n");
        sb.append("Solicitudes atendidas en historial: ").append(contarHistorial()).append("\n\n");
        sb.append("Complejidad esperada:\n");
        sb.append("- Encolar solicitud: O(1)\n");
        sb.append("- Atender solicitud: O(1) para retirar de cola + O(log n) promedio por busqueda AVL\n");
        sb.append("- Buscar libro por codigo en AVL: O(log n) promedio\n");
        sb.append("- Recorrido inorden: O(n)\n");
        return sb.toString();
    }

    public String exportarReporte() throws IOException {
        File archivoReporte = new File(carpetaDatos, "reporte_quicklibrary.txt");
        gestorArchivos.exportarReporteTxt(generarReporteTexto(), archivoReporte);
        return archivoReporte.getAbsolutePath();
    }

    public String exportarReporteEn(File destino) throws IOException {
        gestorArchivos.exportarReporteTxt(generarReporteTexto(), destino);
        return destino.getAbsolutePath();
    }

    public String exportarLibrosEn(File destino) throws IOException {
        gestorArchivos.exportarLibrosCsv(obtenerTodosLibros(), destino);
        return destino.getAbsolutePath();
    }

    public String exportarHistorialEn(File destino) throws IOException {
        gestorArchivos.exportarHistorialCsv(obtenerHistorialPrestamos(), destino);
        return destino.getAbsolutePath();
    }

    public String exportarSolicitudesEn(File destino) throws IOException {
        gestorArchivos.exportarSolicitudesCsv(obtenerSolicitudesPendientes(), destino);
        return destino.getAbsolutePath();
    }

    public int importarLibrosDesdeCsv(File origen) throws IOException {
        CustomLinkedList<Book> importados = gestorArchivos.importarLibrosCsv(origen);
        int importados_ok = 0;
        int i;
        for (i = 0; i < importados.size(); i++) {
            try {
                arbolLibros.insert(importados.get(i));
                importados_ok++;
            } catch (DuplicateKeyException e) {
                // Se omiten los libros cuyo codigo ya existe en la biblioteca.
            }
        }
        if (importados_ok > 0) {
            guardarLibros();
        }
        return importados_ok;
    }


    }
}
