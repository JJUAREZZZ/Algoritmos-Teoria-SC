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
