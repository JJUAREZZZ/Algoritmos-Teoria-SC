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
                "Exportar datos", TitledBorder.LEFT, TitledBorder.TOP, 
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
                "Importar datos", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JButton btnImportarLibros = crearBotonEstilizado("Importar libros desde CSV", new Color(26, 188, 156)); 
        panelImportar.add(btnImportarLibros);

        JPanel panelAgrupadoControles = new JPanel(new BorderLayout(10, 10));
        panelAgrupadoControles.setBackground(Color.WHITE);
        panelAgrupadoControles.add(panelExportar, BorderLayout.NORTH);
        panelAgrupadoControles.add(panelImportar, BorderLayout.CENTER);

        JLabel etiquetaInfo = new JLabel("Formatos: CSV para datos y TXT para el reporte");
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
                "Historial de operaciones", TitledBorder.LEFT, TitledBorder.TOP, 
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
    
    private void exportarReporte() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte");
        selector.setSelectedFile(new File("reporte_quicklibrary.txt"));
        selector.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int opcion = selector.showSaveDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            String ruta = controlador.exportarReporteEn(selector.getSelectedFile());
            registrarLog("Reporte creado en: " + ruta);
            JOptionPane.showMessageDialog(this, "Se guardo el reporte en:\n" + ruta);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void exportarLibros() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar libros");
        selector.setSelectedFile(new File("libros_exportados.csv"));
        selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int opcion = selector.showSaveDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            String ruta = controlador.exportarLibrosEn(selector.getSelectedFile());
            registrarLog("Libros guardados en: " + ruta);
            JOptionPane.showMessageDialog(this, "Libros guardados en:\n" + ruta);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }
    
     private void exportarHistorial() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar historial");
        selector.setSelectedFile(new File("historial_exportado.csv"));
        selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int opcion = selector.showSaveDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            String ruta = controlador.exportarHistorialEn(selector.getSelectedFile());
            registrarLog("Historial creado en: " + ruta);
            JOptionPane.showMessageDialog(this, "Historial guardado en:\n" + ruta);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void exportarSolicitudes() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar solicitudes");
        selector.setSelectedFile(new File("solicitudes_exportadas.csv"));
        selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int opcion = selector.showSaveDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            String ruta = controlador.exportarSolicitudesEn(selector.getSelectedFile());
            registrarLog("Solicitudes listas en: " + ruta);
            JOptionPane.showMessageDialog(this, "Se guardaron las solicitudes en:\n" + ruta);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void importarLibros() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Cargar archivo de libros");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int opcion = selector.showOpenDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            int cantidad = controlador.importarLibrosDesdeCsv(selector.getSelectedFile());
            registrarLog("Importado desde: " + selector.getSelectedFile().getAbsolutePath()
                    + " - libros nuevos: " + cantidad);
            JOptionPane.showMessageDialog(this, "Listo, se se agregaron " + cantidad + " libros nuevos");
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void registrarLog(String mensaje) {
        areaLog.append(mensaje + "\n");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.WARNING_MESSAGE);
    }
}
