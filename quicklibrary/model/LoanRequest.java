package quicklibrary.model;

import java.time.LocalDate;
public class LoanRequest implements Comparable<LoanRequest> {
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
    
}
