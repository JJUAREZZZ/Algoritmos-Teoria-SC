package quicklibrary.view.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import quicklibrary.controller.LibraryController;

public class FilePanel extends JPanel {
    private LibraryController controlador;
    private JTextArea areaLog;

    public FilePanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        crearComponentes();
    }

    private void crearComponentes() {
        JPanel panelIzquierda = new JPanel(new BorderLayout(12, 12));
        panelIzquierda.setBackground(Color.WHITE);
        panelIzquierda.setPreferredSize(new Dimension(320, 0));

        JPanel panelExportar = new JPanel(new GridLayout(4, 1, 8, 8));
        panelExportar.setBackground(Color.WHITE);
        panelExportar.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true), 
                "Exportar Datos", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JButton btnExportarReporte = crearBotonEstilizado("Exportar reporte TXT", new Color(155, 89, 182));   
        JButton btnExportarLibros = crearBotonEstilizado("Exportar libros CSV", new Color(46, 204, 113));     
        JButton btnExportarHistorial = crearBotonEstilizado("Exportar historial CSV", new Color(52, 152, 219)); 
        JButton btnExportarSolicitudes = crearBotonEstilizado("Exportar solicitudes CSV", new Color(230, 126, 34)); 

        panelExportar.add(btnExportarReporte);
        panelExportar.add(btnExportarLibros);
        panelExportar.add(btnExportarHistorial);
        panelExportar.add(btnExportarSolicitudes);

        JPanel panelImportar = new JPanel(new GridLayout(1, 1, 8, 8));
        panelImportar.setBackground(Color.WHITE);
        panelImportar.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true), 
                "Importar Datos", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JButton btnImportarLibros = crearBotonEstilizado("Importar libros desde CSV", new Color(26, 188, 156)); 
        panelImportar.add(btnImportarLibros);

        JPanel panelAgrupadoControles = new JPanel(new BorderLayout(10, 10));
        panelAgrupadoControles.setBackground(Color.WHITE);
        panelAgrupadoControles.add(panelExportar, BorderLayout.NORTH);
        panelAgrupadoControles.add(panelImportar, BorderLayout.CENTER);

        JLabel etiquetaInfo = new JLabel("Formatos admitidos: CSV para datos, TXT para reporte.");
        etiquetaInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        etiquetaInfo.setForeground(new Color(127, 140, 141));
        etiquetaInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panelIzquierda.add(panelAgrupadoControles, BorderLayout.CENTER);
        panelIzquierda.add(etiquetaInfo, BorderLayout.SOUTH);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaLog.setBackground(new Color(245, 247, 250));
        areaLog.setForeground(new Color(44, 62, 80));
        areaLog.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setBackground(Color.WHITE);
        panelLog.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true), 
                "Registro de Operaciones en Tiempo Real", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));
        panelLog.add(scrollLog, BorderLayout.CENTER);

        add(panelIzquierda, BorderLayout.WEST);
        add(panelLog, BorderLayout.CENTER);

        btnExportarReporte.addActionListener(e -> exportarReporte());
        btnExportarLibros.addActionListener(e -> exportarLibros());
        btnExportarHistorial.addActionListener(e -> exportarHistorial());
        btnExportarSolicitudes.addActionListener(e -> exportarSolicitudes());
        btnImportarLibros.addActionListener(e -> importarLibros());
    }
