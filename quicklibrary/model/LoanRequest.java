package quicklibrary.model;

import java.time.LocalDate;
// clase que va a representar una solicitud pendiente de prestamo
public class LoanRequest implements Comparable<LoanRequest> {
    //atributos necesarios, como el codigo del estudiante, libro, nombre del estudiante 
    // y la fecha en la que se registra la solicitud
    private String codigoEstudiante;
    private String nombreEstudiante;
    private String codigoLibro;
    private LocalDate fechaSolicitud;

    public LoanRequest(String codigoEstudiante, String nombreEstudiante, String codigoLibro, LocalDate fechaSolicitud) {
        this.codigoEstudiante = codigoEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.codigoLibro = codigoLibro;
        this.fechaSolicitud = fechaSolicitud;
    }
    // se utiliza para comparar solicitudes usando la fecha y luego el codigo del estudiante
    @Override
    public int compareTo(LoanRequest otraSolicitud) {
        if (otraSolicitud == null) return 1;
        int comparacion = this.fechaSolicitud.compareTo(otraSolicitud.fechaSolicitud);
        
        if (comparacion != 0) return comparacion;
        return this.codigoEstudiante.compareToIgnoreCase(otraSolicitud.codigoEstudiante);
    }
    // retorna el codigo del estudiante
    public String getCodigoEstudiante()  {return codigoEstudiante;}
    // devuelve el nombre del estudiante
    public String getNombreEstudiante()  { return nombreEstudiante;}
    // retorna el codigo del libro solicitado
    public String getCodigoLibro()  {return codigoLibro;}
    
    // Devuelve la fecha en la que se registro la solicitud
    public LocalDate getFechaSolicitud()  {return fechaSolicitud;}

    @Override
    public String toString() {
        return codigoEstudiante + " - " + nombreEstudiante + " solicita " + codigoLibro + " (" + fechaSolicitud + ")";
    }
    // convierte la solicitud a formato csv para guardarlo en el archivo
    public String toCsv() {
        return limpiarCsv(codigoEstudiante) + "," + limpiarCsv(nombreEstudiante) + "," + limpiarCsv(codigoLibro) + "," + fechaSolicitud;
    }
    // limpia los textos antes de guardarlos en el csv
    private String limpiarCsv(String texto) {
        if (texto == null)  return "";
        return texto.replace(",", " ").trim();
    }   
    // crea una solicitud a partir de una linea de csv guardada previamente
    public static LoanRequest fromCsv(String linea) {
        String[] partes = linea.split(",", -1);
        // si la linea no tiene los campos necearios se descarta
        if (partes.length < 4)  return null;

        LocalDate fecha;
        // se intenta leer la fecha guardada
        // si falla se usa la fecha actual
        try {
            fecha = LocalDate.parse(partes[3].trim());
        } catch (Exception e) {
            fecha = LocalDate.now();
        }
        return new LoanRequest(partes[0].trim(), partes[1].trim(), partes[2].trim(), fecha);
    }
    
}
