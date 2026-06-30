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
