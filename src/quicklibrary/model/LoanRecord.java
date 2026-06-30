package quicklibrary.model;

import java.time.LocalDate;
// clase que representa un prestamo ya atendido
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
    // retorna el codigo del estudiante
    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }
    // retorna el nombre del estudiante
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    // retorna el codigo del libro
    public String getCodigoLibro() {
        return codigoLibro;
    }
    // retorna el titulo del libro
    public String getTituloLibro() {
        return tituloLibro;
    }
    // retorna la fecha de la solicitud
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    // retorna la fecha de atencion
    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }
    // devuelve el resultado de la atencion de la solicitud
    public String getResultado() {
        return resultado;
    }
    @Override // se sobreescribe el metodo para poder comparar los objetos
    public int compareTo(LoanRecord otro) { // compara registros segun la fecha que se atendio
        if (otro == null)  return 1;
        return this.fechaAtencion.compareTo(otro.fechaAtencion);
    }

    @Override
    public String toString() {
        return fechaAtencion + " - " + codigoLibro + " - " + resultado;
    }
    
    public String toCsv() {
        return limpiarCsv(codigoEstudiante) + "," + limpiarCsv(nombreEstudiante) + "," + limpiarCsv(codigoLibro) + "," + limpiarCsv(tituloLibro) + "," +
                fechaSolicitud + "," + fechaAtencion + "," + limpiarCsv(resultado);
    }
    private String limpiarCsv(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace(",", " ").trim();
    }

    public static LoanRecord fromCsv(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length < 7) {
            return null;
        }
        LocalDate solicitud;
        LocalDate atencion;
        try {
            solicitud = LocalDate.parse(partes[4].trim());
        } catch (Exception e) {
            solicitud = LocalDate.now();
        }
        try {
            atencion = LocalDate.parse(partes[5].trim());
        } catch (Exception e) {
            atencion = LocalDate.now();
        }
        return new LoanRecord(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                solicitud, atencion, partes[6].trim());
    }
    
}
