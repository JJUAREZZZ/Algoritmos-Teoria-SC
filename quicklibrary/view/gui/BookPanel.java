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

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 6, 6));
        txtCodigoEstudiante = new JTextField();
        txtNombreEstudiante = new JTextField();
        txtCodigoLibro = new JTextField();

        panelCampos.add(new JLabel("Codigo estudiante:"));
        panelCampos.add(txtCodigoEstudiante);
        panelCampos.add(new JLabel("Nombre estudiante:"));
        panelCampos.add(txtNombreEstudiante);
        panelCampos.add(new JLabel("Codigo libro:"));
        panelCampos.add(txtCodigoLibro);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 6, 6));
        JButton btnRegistrar = new JButton("Registrar solicitud");
        JButton btnSiguiente = new JButton("Ver siguiente");
        JButton btnAtender = new JButton("Atender siguiente");
        JButton btnActualizar = new JButton("Actualizar tablas");
        JButton btnLimpiar = new JButton("Limpiar campos");
