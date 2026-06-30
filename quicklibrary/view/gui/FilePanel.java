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
