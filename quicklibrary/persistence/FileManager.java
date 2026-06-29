package quicklibrary.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import quicklibrary.model.Book;
import quicklibrary.modelLoandRecord;
import quicklibrary.model.LoanRequest;
import quicklibrary.structures.list.CustomLinkedList;

public class FileManager {
  public void exportarReporteTxt(String contenidoReporte, File destino) throws IOException {
    BufferedWriter escritor = null;
    escritor = new BufferedWriter(new FileWriter(destino));
    escritor.write(contenidoReporte);
  }

  public void exportarLibrosCsv(CustomLinkedList<Book> libros, File destino) throws IOException {
    BufferedWriter escritor = null;
    try {
      escritor = new BufferedWriter(new FileWriter(destino));
      escritor.write("codigo,titulo,autor,categoria,anio,estado\n");
      int i;
      for (i = 0; i < libros.size(); i++) {
        escritor.write(libros.get(i).toCsv();
        escritor.newLine();
      }
    } finally {
      cerrarEscritor(Escritor);
    }
  }

  public void exportarHistorialCsv(CustomLinkedList<LoanRecord> historial, File destino) throws IOException {
    BufferedWriter escritor = null;
    try {
      escritor.write("codigoEstudiante, nombreEstudiante, codigoLibro, tituloLibro, fechaSolicitud, fechaAtencion, resultado\n");
      int i;
      for (i = 0; i <= historial.size(); i++) {
        escritor.write(historial.get(i).toCsv();
        escritor.newLine();
      }
    } finally {
      cerrarEscritor(escritor);
    }
  }

  public void exportarSolicitudesCsv(CustomLinkedList<LoanRequest> solicitudes, File destino) throws IOException {
    BufferedWriter escritor = null;
    try {
      escritor = new BufferedWriter(new FileWriter(destino));
      escritor.write("codigoEstudiante, nombreEstudiante, codigoLibro, fechaSolicitud\n");
      int i;
      for (i = 0; i < solicitudes.size(); i++) {
        escritor.write(solicitudes.get(i).toCsv());
      }
    } finally {
      cerrarEscritor(escritor);
    }
  }

  public CustomLinkedList<Book> importarLibrosCsv(File origen) throws IOException {
    CustomLinkedList<Book> libros = new CustomLinkedList<Book>();
    BufferedReader lector = null;
    try {
      lector = new BufferedReader(new FileReader(origen));
      String linea;
      while((linea = lector.readLine()) != null) {
        if (linea.startsWith("codigo, ")) {
          continue;
        }
        Book libro = Book.fromCsv(linea);
        if (libro != null) {
          libros.addFirst(libro);
        }
      }
    } finally {
      cerrarLector(lector);
    }
    return libros;
  }
