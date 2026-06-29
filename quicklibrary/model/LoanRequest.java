package quicklibrary.model;

import java.time.LocalDate;
public class LoanRequest {
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
}
