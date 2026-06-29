package quicklibrary.model;


public class LoanRecord {
    private String codigoEstudiante;
    private String nombreEstudiante;
    private String codigoLibro;
    private String tituloLibro;
    private LocalDate fechaSolicitud;
    private LocalDate fechaAtencion;
    private String resultado;

    public LoanRecord(String codigoEstudiante, String nombreEstudiante, String codigoLibro, String tituloLibro,
                      LocalDate fechaSolicitud, LocalDate fechaAtencion, String resultado) {
        this.codigoEstudiante = codigoEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.codigoLibro = codigoLibro;
        this.tituloLibro = tituloLibro;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAtencion = fechaAtencion;
        this.resultado = resultado;
    }
}
