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

