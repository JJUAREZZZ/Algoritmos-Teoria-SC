package quicklibrary.model;

import java.time.LocalDate;
public class LoanRecord implements Comparable<LoanRecord>{
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
    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    public String getCodigoLibro() {
        return codigoLibro;
    }
    public String getTituloLibro() {
        return tituloLibro;
    }
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }
    public String getResultado() {
        return resultado;
    }
    @Override
    public int compareTo(LoanRecord otro) {
        if (otro == null)  return 1;
        return this.fechaAtencion.compareTo(otro.fechaAtencion);
    }
}
