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
        actualizarTodo();
    }

    private void crearTablas() {
        String[] columnasSolicitud = {"Codigo estudiante", "Nombre alumno", "Codigo libro", "Fecha solicitud"};
        modeloSolicitudes = new DefaultTableModel(columnasSolicitud, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaSolicitudes = new JTable(modeloSolicitudes);
        tablaSolicitudes.setRowHeight(26);
        tablaSolicitudes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaSolicitudes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaSolicitudes.getTableHeader().setBackground(new Color(240, 244, 248));
        tablaSolicitudes.setSelectionBackground(new Color(212, 230, 241));
        tablaSolicitudes.setShowGrid(true);
        tablaSolicitudes.setGridColor(new Color(230, 235, 240));

        String[] columnasHistorial = {"Fecha atencion", "Estudiante", "Cod. Libro", "Titulo del libro", "Resultado"};
        modeloHistorial = new DefaultTableModel(columnasHistorial, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setRowHeight(26);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaHistorial.getTableHeader().setBackground(new Color(240, 244, 248));
        tablaHistorial.setSelectionBackground(new Color(212, 230, 241));
        tablaHistorial.setShowGrid(true);
        tablaHistorial.setGridColor(new Color(230, 235, 240));

        JScrollPane scrollSolicitudes = new JScrollPane(tablaSolicitudes);
        scrollSolicitudes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Cola de solicitudes pendientes", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Historial de prestamos atendidos", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JSplitPane separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollSolicitudes, scrollHistorial);
        separador.setResizeWeight(0.5);
        separador.setBorder(BorderFactory.createEmptyBorder());
        add(separador, BorderLayout.CENTER);
    }

    private void crearFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(12, 12));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setPreferredSize(new Dimension(320, 0));
        panelPrincipal.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Solicitudes y prestamos", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 8, 8));
        panelCampos.setBackground(Color.WHITE);
        txtCodigoEstudiante = new JTextField();
        txtNombreEstudiante = new JTextField();
        txtCodigoLibro = new JTextField();

        Font fontLabels = new Font("Segoe UI", Font.BOLD, 12);
        JLabel lblCod = new JLabel("Codigo estudiante:");
        lblCod.setFont(fontLabels);
        JLabel lblNom = new JLabel("Nombre estudiante:");
        lblNom.setFont(fontLabels);
        JLabel lblLib = new JLabel("Codigo libro:");
        lblLib.setFont(fontLabels);

        panelCampos.add(lblCod);
        panelCampos.add(txtCodigoEstudiante);
        panelCampos.add(lblNom);
        panelCampos.add(txtNombreEstudiante);
        panelCampos.add(lblLib);
        panelCampos.add(txtCodigoLibro);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 8, 8));
        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar = crearBotonEstilizado("Registrar solicitud", new Color(46, 204, 113));
        JButton btnSiguiente = crearBotonEstilizado("Ver siguiente", new Color(155, 89, 182));
        JButton btnAtender = crearBotonEstilizado("Atender siguiente", new Color(52, 152, 219));
        JButton btnActualizar = crearBotonEstilizado("Actualizar tablas", new Color(149, 165, 166));
        JButton btnLimpiar = crearBotonEstilizado("Limpiar campos", new Color(231, 76, 60));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnAtender);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);

        panelPrincipal.add(panelCampos, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.EAST);

        btnRegistrar.addActionListener(e -> registrarSolicitud());
        btnSiguiente.addActionListener(e -> mostrarSiguiente());
        btnAtender.addActionListener(e -> atenderSolicitud());
        btnActualizar.addActionListener(e -> actualizarTodo());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private JButton crearBotonEstilizado(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fondo.darker(), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return boton;
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
