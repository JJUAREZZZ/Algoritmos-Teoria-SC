package quicklibrary.view.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import quicklibrary.controller.LibraryController;
import quicklibrary.model.LoanRecord;
import quicklibrary.model.LoanRequest;
import quicklibrary.structures.list.CustomLinkedList;

public class LoanPanel extends JPanel {
    private LibraryController controlador;
    private JTable tablaSolicitudes;
    private JTable tablaHistorial;
    private DefaultTableModel modeloSolicitudes;
    private DefaultTableModel modeloHistorial;

    private JTextField txtCodigoEstudiante;
    private JTextField txtNombreEstudiante;
    private JTextField txtCodigoLibro;

    public LoanPanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        crearTablas();
        crearFormulario();
    }

    private void crearTablas() {
    }

    private void crearFormulario() {
    }

    private JButton crearBotonEstilizado(String texto, Color fondo) {
        return null;
    }

    public void actualizarTodo() {
    }

    private void actualizarSolicitudes() {
    }

    private void actualizarHistorial() {
    }

    private void registrarSolicitud() {
    }

    private void mostrarSiguiente() {
    }

    private void atenderSolicitud() {
    }

    private void limpiarCampos() {
    }

    private void mostrarError(String mensaje) {
    }
}
