package quicklibrary.controller;

import java.io.File;
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
        Book libro = buscarLibroPorCodigo(codigoLimpio);
        if (libro.getEstado() == BookStatus.PRESTADO) {
            throw new ValidationException("No se puede eliminar el libro porque esta prestado actualmente.");
        }
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
        if (!codigoEstudianteLimpio.matches("\\d+")) {
            throw new ValidationException("El codigo del estudiante debe ser completamente numerico.");
        }
        String nombreEstudianteLimpio = validarTexto(nombreEstudiante, "nombre del estudiante");
        String codigoLibroLimpio = validarTexto(codigoLibro, "codigo del libro");
        if (buscarLibroPorCodigoSinExcepcion(codigoLibroLimpio) == null) {
            throw new ValidationException("El libro con el codigo ingresado no existe en el catalogo.");
        }
        CustomLinkedList<LoanRequest> pendientes = obtenerSolicitudesPendientes();
        for (int i = 0; i < pendientes.size(); i++) {
            LoanRequest r = pendientes.get(i);
            if (r.getCodigoEstudiante().equals(codigoEstudianteLimpio) && r.getCodigoLibro().equals(codigoLibroLimpio)) {
                throw new ValidationException("Este estudiante ya tiene una solicitud pendiente para este mismo libro.");
            }
        }
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

    public String obtenerDibujoArbol() {
        return arbolLibros.drawTree();
    }

    public CustomAVLTree<Book> obtenerArbolLibros() {
        return this.arbolLibros;
    }

    private String validarTexto(String texto, String campo) throws ValidationException {
        if (texto == null || texto.trim().length() == 0) {
            throw new ValidationException("El campo " + campo + " no puede estar vacio.");
        }
        return texto.trim();
    }

    private int validarAnio(String anioTexto) throws ValidationException {
        String limpio = validarTexto(anioTexto, "anio");
        int anio;
        try {
            anio = Integer.parseInt(limpio);
        } catch (NumberFormatException e) {
            throw new ValidationException("El anio debe ser numerico.");
        }
        int anioActual = LocalDate.now().getYear();
        if (anio < 1000 || anio > anioActual + 1) {
            throw new ValidationException("El anio ingresado no es valido.");
        }
        return anio;
    }

    private boolean contieneTexto(String textoCompleto, String textoBuscado) {
        if (textoCompleto == null || textoBuscado == null) {
            return false;
        }
        return textoCompleto.toLowerCase().contains(textoBuscado.toLowerCase());
    }

    private void cargarLibros() {
        if (!archivoLibros.exists()) {
            cargarDatosIniciales();
            guardarLibros();
            return;
        }
        try {
            CustomLinkedList<Book> libros = gestorArchivos.cargarLibrosCsv(archivoLibros);
            int i;
            for (i = 0; i < libros.size(); i++) {
                try {
                    arbolLibros.insert(libros.get(i));
                } catch (DuplicateKeyException e) {
                    // Si el archivo trae duplicados, se ignoran para conservar la clave unica.
                }
            }
        } catch (IOException e) {
            cargarDatosIniciales();
        }
        if (arbolLibros.size() == 0) {
            cargarDatosIniciales();
            guardarLibros();
        }
    }

    private void cargarSolicitudes() {
        if (!archivoSolicitudes.exists()) {
            return;
        }
        try {
            CustomLinkedList<LoanRequest> solicitudes = gestorArchivos.cargarSolicitudesCsv(archivoSolicitudes);
            int i;
            for (i = 0; i < solicitudes.size(); i++) {
                colaSolicitudes.enqueue(solicitudes.get(i));
            }
        } catch (IOException e) {
            // Si no se puede leer, simplemente inicia sin solicitudes.
        }
    }

    private void cargarHistorial() {
        if (!archivoHistorial.exists()) {
            return;
        }
        try {
            CustomLinkedList<LoanRecord> temporal = gestorArchivos.cargarHistorialCsv(archivoHistorial);
            int i;
            for (i = temporal.size() - 1; i >= 0; i--) {
                historialPrestamos.push(temporal.get(i));
            }
        } catch (IOException e) {
            // Sin historial si el archivo no se puede leer.
        }
    }

    private void guardarLibros() {
        gestorArchivos.guardarLibrosCsv(obtenerTodosLibros(), archivoLibros);
    }

    private void guardarSolicitudes() {
        gestorArchivos.guardarSolicitudesCsv(obtenerSolicitudesPendientes(), archivoSolicitudes);
    }

    private void guardarHistorial() {
        gestorArchivos.guardarHistorialCsv(obtenerHistorialPrestamos(), archivoHistorial);
    }

    private void cargarDatosIniciales() {
        Object[][] datos = {
                {"101", "Programacion en Java", "Herbert Schildt", "Programacion", 2022, BookStatus.DISPONIBLE},
                {"102", "Estructuras de Datos", "Mark Allen Weiss", "Computacion", 2021, BookStatus.DISPONIBLE},
                {"103", "Introduccion a los Algoritmos", "Thomas Cormen", "Algoritmos", 2022, BookStatus.PRESTADO},
                {"104", "Clean Code", "Robert C. Martin", "Programacion", 2008, BookStatus.DISPONIBLE},
                {"105", "Design Patterns", "Erich Gamma", "Software", 1994, BookStatus.DISPONIBLE},
                {"106", "Base de Datos", "Abraham Silberschatz", "Base de datos", 2019, BookStatus.DISPONIBLE},
                {"107", "Sistemas Operativos", "Abraham Silberschatz", "Sistemas", 2020, BookStatus.PRESTADO},
                {"108", "Redes de Computadoras", "Andrew Tanenbaum", "Redes", 2011, BookStatus.DISPONIBLE},
                {"109", "Ingenieria de Software", "Ian Sommerville", "Software", 2015, BookStatus.DISPONIBLE},
                {"110", "Inteligencia Artificial", "Stuart Russell", "Inteligencia Artificial", 2021, BookStatus.DISPONIBLE},
                {"111", "Calculo de Una Variable", "James Stewart", "Matematica", 2016, BookStatus.DISPONIBLE},
                {"112", "Algebra Lineal", "Gilbert Strang", "Matematica", 2019, BookStatus.PRESTADO},
                {"113", "Fisica Universitaria", "Young Freedman", "Fisica", 2018, BookStatus.DISPONIBLE},
                {"114", "Probabilidad y Estadistica", "Murray Spiegel", "Estadistica", 2017, BookStatus.DISPONIBLE},
                {"115", "Compiladores", "Alfred Aho", "Computacion", 2007, BookStatus.DISPONIBLE},
                {"116", "Arquitectura de Computadoras", "John Hennessy", "Hardware", 2019, BookStatus.DISPONIBLE},
                {"117", "Patrones de Arquitectura", "Martin Fowler", "Software", 2002, BookStatus.DISPONIBLE},
                {"118", "Python para Todos", "Charles Severance", "Programacion", 2016, BookStatus.PRESTADO},
                {"119", "Matematica Discreta", "Kenneth Rosen", "Matematica", 2018, BookStatus.DISPONIBLE},
                {"120", "Seguridad Informatica", "William Stallings", "Seguridad", 2020, BookStatus.DISPONIBLE},
                {"121", "Mineria de Datos", "Jiawei Han", "Datos", 2011, BookStatus.DISPONIBLE},
                {"122", "Analisis y Diseno de Algoritmos", "Anany Levitin", "Algoritmos", 2012, BookStatus.DISPONIBLE},
                {"123", "Desarrollo Web Moderno", "Jennifer Robbins", "Web", 2020, BookStatus.DISPONIBLE},
                {"124", "Fundamentos de Programacion", "Luis Joyanes", "Programacion", 2017, BookStatus.DISPONIBLE},
                {"125", "Gestion de Proyectos", "Kathy Schwalbe", "Gestion", 2019, BookStatus.DISPONIBLE},
                {"126", "Computacion en la Nube", "Rajkumar Buyya", "Cloud", 2013, BookStatus.PRESTADO},
                {"127", "Machine Learning", "Tom Mitchell", "Inteligencia Artificial", 1997, BookStatus.DISPONIBLE},
                {"128", "Programacion Concurrente", "Brian Goetz", "Programacion", 2006, BookStatus.DISPONIBLE},
                {"129", "UX Design", "Don Norman", "Diseno", 2013, BookStatus.DISPONIBLE},
                {"130", "Administracion de Redes", "Kurose Ross", "Redes", 2021, BookStatus.DISPONIBLE}
        };

        int i;
        for (i = 0; i < datos.length; i++) {
            Book libro = new Book((String) datos[i][0], (String) datos[i][1], (String) datos[i][2],
                    (String) datos[i][3], (Integer) datos[i][4], (BookStatus) datos[i][5]);
            try {
                arbolLibros.insert(libro);
            } catch (DuplicateKeyException e) {
                // No deberia ocurrir con datos iniciales controlados.
            }
        }
    }
}
