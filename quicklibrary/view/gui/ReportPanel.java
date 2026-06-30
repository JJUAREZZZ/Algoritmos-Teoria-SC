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
        return null;
    }

    public void actualizarReporte() {
    }

    private void exportarReporte() {
    }
}
