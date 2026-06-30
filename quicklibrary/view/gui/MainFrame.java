package quicklibrary.view.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import quicklibrary.controller.LibraryController;

public class MainFrame extends JFrame {
    private LibraryController controlador;
    private BookPanel bookPanel;
    private LoanPanel loanPanel;
    private ReportPanel reportPanel;

    public MainFrame() {
        this.controlador = new LibraryController();
        configurarVentana();
        crearComponentes();
    }

   public MainFrame() {
        this.controlador = new LibraryController();
        configurarVentana();
        crearComponentes();
    }
 private void configurarVentana() {
        setTitle("QuickLibrary - Sistema de Prestamos de Libros");
        setSize(1060, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si no carga el estilo del sistema, se usa el estilo por defecto de Swing.
        }
    }
