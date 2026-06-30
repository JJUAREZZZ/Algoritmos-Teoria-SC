package quicklibrary.view.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class LoanPanel extends JPanel {
    private LibraryController controlador;
    private JTable tablaSolicitudes;
    private JTable tablaHistorial;
    private DefaultTableModel modeloSolicitudes;
    private DefaultTableModel modeloHistorial;

    private JTextField txtCodigoEstudiante;
    private JTextField txtNombreEstudiante;
    private JTextField txtCodigoLibro;

 public LoanPanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));
        crearTablas();
        crearFormulario();
        actualizarTodo();
    }
