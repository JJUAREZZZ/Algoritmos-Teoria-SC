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
