public class LibraryController {
    private CustomAVLTree<Book> arbolLibros;
    private CustomQueue<LoanRequest> colaSolicitudes;
    private CustomStack<LoanRecord> historialPrestamos;

    private File carpetaDatos;
    private File archivoLibros;
    private File archivoSolicitudes;
    private File archivoHistorial;
    private FileManager gestorArchivos;
