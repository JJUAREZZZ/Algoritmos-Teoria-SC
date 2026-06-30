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
    private FilePanel filePanel;

    public MainFrame() {
        this.controlador = new LibraryController();
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("QuickLibrary - Sistema de Prestamos");
        setSize(1120, 740);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    private void crearComponentes() {
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(44, 62, 80));
        panelTop.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel titulo = new JLabel("QUICKLIBRARY");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panelTop.add(titulo, BorderLayout.WEST);
        
        add(panelTop, BorderLayout.NORTH);

        bookPanel = new BookPanel(controlador);
        loanPanel = new LoanPanel(controlador);
        reportPanel = new ReportPanel(controlador);
        filePanel = new FilePanel(controlador);

        JTabbedPane pestanias = new JTabbedPane();
        pestanias.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        pestanias.addTab("Libros", bookPanel);
        pestanias.addTab("Prestamos", loanPanel);
        pestanias.addTab("Reportes", reportPanel);
        pestanias.addTab("Archivos", filePanel);

        pestanias.addChangeListener(e -> {
            bookPanel.actualizarTabla(controlador.obtenerTodosLibros());
            loanPanel.actualizarTodo();
            reportPanel.actualizarReporte();
        });

        add(pestanias, BorderLayout.CENTER);
    }
}
