package quicklibrary.model;

public enum BookStatus {
    DISPONIBLE,
    PRESTADO;

    public static BookStatus fromText(String texto) {
        if (texto == null) {
            return DISPONIBLE;
        }
        String valor = texto.trim().toUpperCase();
        if (valor.equals("PRESTADO")) {
            return PRESTADO;
        }
        return DISPONIBLE;
    }
}
