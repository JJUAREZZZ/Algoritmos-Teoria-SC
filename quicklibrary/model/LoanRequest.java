package quicklibrary.model;

import java.time.LocalDate;
// clase que va a arepresntar una solicitud pendiente de prestamo
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
        if (otraSolicitud == null) {
            return 1;
        }
        int comparacion = this.fechaSolicitud.compareTo(otraSolicitud.fechaSolicitud);
        if (comparacion != 0) {
            return comparacion;
        }
        return this.codigoEstudiante.compareToIgnoreCase(otraSolicitud.codigoEstudiante);
    }
    // retorna el codigo del estudiante
    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }
    // devuelve el nombre del estudiante
    public String getNombreEstudiante() {
        return nombreEstudiante;
        
    }
    // retorna el codigo del libro solicitado
    public String getCodigoLibro() {
        return codigoLibro;
    }
    
    // Devuelve la fecha en la que se registro la solicitud
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    @Override
    public String toString() {
        return codigoEstudiante + " - " + nombreEstudiante + " solicita " + codigoLibro + " (" + fechaSolicitud + ")";
    }

    public String toCsv() {
        return limpiarCsv(codigoEstudiante) + "," + limpiarCsv(nombreEstudiante) + "," +
                limpiarCsv(codigoLibro) + "," + fechaSolicitud;
    }

    private String limpiarCsv(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace(",", " ").trim();
    }    
    
}
