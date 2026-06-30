package quicklibrary.view.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import quicklibrary.controller.LibraryController;
import quicklibrary.model.Book;
import quicklibrary.model.BookStatus;
import quicklibrary.structures.list.CustomLinkedList;

public class BookPanel extends JPanel {
    private LibraryController controlador;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtAnio;
    private JComboBox<BookStatus> cboEstado;

    private JTextField txtBusqueda;
    private JComboBox<String> cboCriterio;

    public BookPanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        crearTabla();
        crearFormulario();
    }

    private void crearTabla() {
    }

    private void crearFormulario() {
    }

    private JButton crearBotonEstilizado(String texto, Color fondo) {
        return null;
    }

    public void actualizarTabla(CustomLinkedList<Book> libros) {
    }

    private void registrarLibro() {
    }

    private void modificarLibro() {
    }

    private void eliminarLibro() {
    }

    private void buscarPorCodigo() {
    }

    private void buscarPorTexto() {
    }

    private void mostrarArbol() {
    }

    private void cargarSeleccion() {
    }

    private void limpiarCampos() {
    }

    private void mostrarError(String mensaje) {
    }
}
