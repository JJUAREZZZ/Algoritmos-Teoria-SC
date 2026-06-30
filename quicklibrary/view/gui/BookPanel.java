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

    public LoanPanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));
        crearTablas();
        crearFormulario();
        actualizarTodo();
    }

  private void crearTablas() {
        String[] columnasSolicitud = {"Codigo estudiante", "Nombre", "Codigo libro", "Fecha solicitud"};
        modeloSolicitudes = new DefaultTableModel(columnasSolicitud, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }

        };
        tablaSolicitudes = new JTable(modeloSolicitudes);
        tablaSolicitudes.setRowHeight(24);
      String[] columnasHistorial = {"Fecha atencion", "Estudiante", "Libro", "Titulo", "Resultado"};
        modeloHistorial = new DefaultTableModel(columnasHistorial, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
  };
        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setRowHeight(24);

        JScrollPane scrollSolicitudes = new JScrollPane(tablaSolicitudes);
        scrollSolicitudes.setBorder(BorderFactory.createTitledBorder("Cola de solicitudes pendientes"));
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder("Historial de prestamos atendidos (pila)"));

       JSplitPane separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollSolicitudes, scrollHistorial);
        separador.setResizeWeight(0.55);
        add(separador, BorderLayout.CENTER);
    }
 private void crearFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Solicitudes y prestamos"));
