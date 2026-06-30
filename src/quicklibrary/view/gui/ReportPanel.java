package quicklibrary.view.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import quicklibrary.controller.LibraryController;

public class ReportPanel extends JPanel {
    private LibraryController controlador;
    private JTextArea areaReporte;

    public ReportPanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        crearComponentes();
    }

    private void crearComponentes() {
        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaReporte.setBackground(new Color(245, 247, 250));
        areaReporte.setForeground(new Color(44, 62, 80));
        areaReporte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollReporte = new JScrollPane(areaReporte);
        scrollReporte.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Estadisticas y Estado General", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton btnActualizar = crearBotonEstilizado("Actualizar reporte", new Color(52, 152, 219));
        
        panelBotones.add(btnActualizar);

        add(scrollReporte, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> actualizarReporte());
    }

    private JButton crearBotonEstilizado(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setForeground(fondo.darker());
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fondo, 2, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        return boton;
    }

    public void actualizarReporte() {
        areaReporte.setText(controlador.generarReporteTexto());
    }

    private void exportarReporte() {
        try {
            String ruta = controlador.exportarReporte();
            JOptionPane.showMessageDialog(this, "Listo, el reporte se guardo en:\n" + ruta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ups, no se pudo exportar: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
