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




    
    public void registrarSolicitud(String codigoEstudiante, String nombreEstudiante, String codigoLibro) throws ValidationException {
        // se necesitan validar y limpiar los datos necesarios para registrar la solicitud
        String codigoEstudianteLimpio = validarTexto(codigoEstudiante, "codigo del estudiante");
        String nombreEstudianteLimpio = validarTexto(nombreEstudiante, "nombre del estudiante");
        String codigoLibroLimpio = validarTexto(codigoLibro, "codigo del libro");
        // se crea la solicitud con la fecha aactual del sistema
        LoanRequest solicitud = new LoanRequest(codigoEstudianteLimpio, nombreEstudianteLimpio,codigoLibroLimpio, LocalDate.now());
        
        colaSolicitudes.enqueue(solicitud); // se agrega en la cola
        guardarSolicitudes();
    }
    public LoanRequest consultarSiguienteSolicitud() {
        return colaSolicitudes.peek();
    }

    public String atenderSiguienteSolicitud() {
        // se obtiene la primera solicitud de la cola sin retirarla todavia
        LoanRequest solicitud = colaSolicitudes.peek();
        // se valida si existen pendientes por atender
        if (solicitud == null) return "No existen solicitudes pendientes.";
        // se busca libro solicitado usando el docido registrado de la solicitud
        Book libro = buscarLibroPorCodigoSinExcepcion(solicitud.getCodigoLibro());
        String resultado;
        String tituloLibro = "No encontrado";
        // verifica si el libro no existe
        if (libro == null) {
            resultado = "Solicitud atendida: el libro con codigo " + solicitud.getCodigoLibro() + " no existe.";
        //el libro existe pero no esta disponible para prestamos
        } else if (!libro.estaDisponible()) {
            tituloLibro = libro.getTitulo();
            resultado = "Solicitud atendida: el libro '" + libro.getTitulo() + "' no esta disponible.";
        // existe y esta disponible para resgistrar el prestamo
        } else {
            tituloLibro = libro.getTitulo();
            libro.setEstado(BookStatus.PRESTADO);
            resultado = "Prestamo realizado correctamente para " + solicitud.getNombreEstudiante() +
                    ". Libro: " + libro.getTitulo() + ".";
        }
        colaSolicitudes.dequeue();
        // se crea el registro
        LoanRecord registro = new LoanRecord(solicitud.getCodigoEstudiante(), solicitud.getNombreEstudiante(),
                solicitud.getCodigoLibro(), tituloLibro, solicitud.getFechaSolicitud(), LocalDate.now(), resultado);
        historialPrestamos.push(registro);
        // se guarda cambios
        guardarLibros();
        guardarSolicitudes();
        guardarHistorial();
        return resultado;
    }

    public CustomLinkedList<LoanRequest> obtenerSolicitudesPendientes() {
        return colaSolicitudes.toList(); // retorna una lista con las solicitudes que aun se encuentran en la cola
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
    public CustomLinkedList<LoanRecord> obtenerHistorialPrestamos() {
        return historialPrestamos.toList();
    }
    public int contarSolicitudesPendientes() {
        return colaSolicitudes.size();
    }

    public int contarHistorial() {
        return historialPrestamos.size();
    }
    
    private Book buscarLibroPorCodigoSinExcepcion(String codigo) {
        if (codigo == null) return null;
        Book llave = new Book(codigo.trim(), "", "", "", 0, BookStatus.DISPONIBLE);
        return arbolLibros.searchOrNull(llave);
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
                // Controlado
            }
        }
        return importados_ok; 
    }

    
}
