package quicklibrary.view.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import quicklibrary.controller.LibraryController;
import quicklibrary.model.Book;
import quicklibrary.model.BookStatus;
import quicklibrary.structures.list.CustomLinkedList;

public class BookPanel extends JPanel {
    private LibraryController controlador;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtAnio;
    private JComboBox<BookStatus> cboEstado;

    private JTextField txtBusqueda;
    private JComboBox<String> cboCriterio;

    public BookPanel(LibraryController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        crearTabla();
        crearFormulario();
        actualizarTabla(controlador.obtenerTodosLibros());
    }

    private void crearTabla() {
        String[] columnas = {"Codigo", "Titulo", "Autor", "Categoria", "Anio", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setRowHeight(26);
        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaLibros.getTableHeader().setBackground(new Color(240, 244, 248));
        tablaLibros.setSelectionBackground(new Color(212, 230, 241));
        tablaLibros.setShowGrid(true);
        tablaLibros.setGridColor(new Color(230, 235, 240));

        tablaLibros.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Catalogo de libros", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void crearFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setPreferredSize(new Dimension(340, 0));
        panelPrincipal.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Gestion de libros", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(52, 73, 94)));

        JPanel panelCampos = new JPanel(new GridLayout(6, 2, 6, 6));
        panelCampos.setBackground(Color.WHITE);

        txtCodigo = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtCategoria = new JTextField();
        txtAnio = new JTextField();
        cboEstado = new JComboBox<>(BookStatus.values());

        Font fontLabels = new Font("Segoe UI", Font.BOLD, 12);
        String[] labels = {"Codigo:", "Titulo:", "Autor:", "Categoria:", "Anio:", "Estado:"};
        JComponent[] components = {txtCodigo, txtTitulo, txtAutor, txtCategoria, txtAnio, cboEstado};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(fontLabels);
            panelCampos.add(lbl);
            panelCampos.add(components[i]);
        }

        JPanel panelBotones = new JPanel(new GridLayout(5, 2, 6, 6));
        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar = crearBotonEstilizado("Registrar", new Color(46, 204, 113));
        JButton btnModificar = crearBotonEstilizado("Modificar", new Color(52, 152, 219));
        JButton btnEliminar = crearBotonEstilizado("Eliminar", new Color(231, 76, 60));
        JButton btnBuscarCodigo = crearBotonEstilizado("Buscar codigo", new Color(155, 89, 182));
        JButton btnTodos = crearBotonEstilizado("Mostrar todos", new Color(149, 165, 166));
        JButton btnDisponibles = crearBotonEstilizado("Disponibles", new Color(241, 196, 15));
        JButton btnPrestados = crearBotonEstilizado("Prestados", new Color(230, 126, 34));
        JButton btnOrdenTitulo = crearBotonEstilizado("Ordenar titulo", new Color(52, 73, 94));
        JButton btnArbol = crearBotonEstilizado("Ver arbol AVL", new Color(34, 153, 84));
        JButton btnLimpiar = crearBotonEstilizado("Limpiar", new Color(127, 140, 141));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscarCodigo);
        panelBotones.add(btnTodos);
        panelBotones.add(btnDisponibles);
        panelBotones.add(btnPrestados);
        panelBotones.add(btnOrdenTitulo);
        panelBotones.add(btnArbol);
        panelBotones.add(btnLimpiar);

        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1, true),
                "Busqueda rapida", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)));

        JPanel filaBusqueda = new JPanel(new GridLayout(1, 3, 5, 5));
        filaBusqueda.setBackground(Color.WHITE);
        txtBusqueda = new JTextField();
        cboCriterio = new JComboBox<>(new String[] {"Titulo", "Autor", "Categoria"});
        JButton btnBuscarTexto = crearBotonEstilizado("Buscar", new Color(41, 128, 185));

        filaBusqueda.add(cboCriterio);
        filaBusqueda.add(txtBusqueda);
        filaBusqueda.add(btnBuscarTexto);
        panelBusqueda.add(filaBusqueda, BorderLayout.CENTER);

        panelPrincipal.add(panelCampos, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(panelBusqueda, BorderLayout.SOUTH);
        add(panelPrincipal, BorderLayout.EAST);

        btnRegistrar.addActionListener(e -> registrarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnBuscarCodigo.addActionListener(e -> buscarPorCodigo());
        btnTodos.addActionListener(e -> actualizarTabla(controlador.obtenerTodosLibros()));
        btnDisponibles.addActionListener(e -> actualizarTabla(controlador.obtenerLibrosDisponibles()));
        btnPrestados.addActionListener(e -> actualizarTabla(controlador.obtenerLibrosPrestados()));
        btnOrdenTitulo.addActionListener(e -> actualizarTabla(controlador.obtenerLibrosOrdenadosPorTitulo()));
        btnArbol.addActionListener(e -> mostrarArbol());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnBuscarTexto.addActionListener(e -> buscarPorTexto());
    }

    private JButton crearBotonEstilizado(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setForeground(fondo.darker());
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fondo, 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return boton;
    }

    public void actualizarTabla(CustomLinkedList<Book> libros) {
        modeloTabla.setRowCount(0);
        if (libros == null) return;
        for (int i = 0; i < libros.size(); i++) {
            Book libro = libros.get(i);
            Object[] fila = {libro.getCodigo(), libro.getTitulo(), libro.getAutor(), libro.getCategoria(),
                    libro.getAnio(), libro.getEstado()};
            modeloTabla.addRow(fila);
        }
    }

    private void registrarLibro() {
        try {
            controlador.registrarLibro(txtCodigo.getText(), txtTitulo.getText(), txtAutor.getText(),
                    txtCategoria.getText(), txtAnio.getText(), (BookStatus) cboEstado.getSelectedItem());
            JOptionPane.showMessageDialog(this, "Listo, se guardo el libro correctamente");
            limpiarCampos();
            actualizarTabla(controlador.obtenerTodosLibros());
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void modificarLibro() {
        try {
            controlador.modificarLibro(txtCodigo.getText(), txtTitulo.getText(), txtAutor.getText(),
                    txtCategoria.getText(), txtAnio.getText(), (BookStatus) cboEstado.getSelectedItem());
            JOptionPane.showMessageDialog(this, "Libro modificado con exito");
            actualizarTabla(controlador.obtenerTodosLibros());
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void eliminarLibro() {
        try {
            int opcion = JOptionPane.showConfirmDialog(this, "Seguro que quieres borrar este libro?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                controlador.eliminarLibro(txtCodigo.getText());
                JOptionPane.showMessageDialog(this, "Libro eliminado");
                limpiarCampos();
                actualizarTabla(controlador.obtenerTodosLibros());
            }
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void buscarPorCodigo() {
        try {
            Book libro = controlador.buscarLibroPorCodigo(txtCodigo.getText());
            CustomLinkedList<Book> resultado = new CustomLinkedList<>();
            resultado.addLast(libro);
            actualizarTabla(resultado);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void buscarPorTexto() {
        try {
            String criterio = (String) cboCriterio.getSelectedItem();
            CustomLinkedList<Book> resultado;
            if ("Autor".equals(criterio)) {
                resultado = controlador.buscarLibrosPorAutor(txtBusqueda.getText());
            } else if ("Categoria".equals(criterio)) {
                resultado = controlador.buscarLibrosPorCategoria(txtBusqueda.getText());
            } else {
                resultado = controlador.buscarLibrosPorTitulo(txtBusqueda.getText());
            }
            actualizarTabla(resultado);
            if (resultado.size() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron coincidencias");
            }
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarArbol() {
        JTextArea area = new JTextArea(controlador.obtenerDibujoArbol(), 22, 60);
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 12));
        area.setBackground(new Color(245, 247, 250));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Vista del arbol AVL", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarSeleccion() {
        int fila = tablaLibros.getSelectedRow();
        if (fila < 0) return;
        txtCodigo.setText(String.valueOf(modeloTabla.getValueAt(fila, 0)));
        txtTitulo.setText(String.valueOf(modeloTabla.getValueAt(fila, 1)));
        txtAutor.setText(String.valueOf(modeloTabla.getValueAt(fila, 2)));
        txtCategoria.setText(String.valueOf(modeloTabla.getValueAt(fila, 3)));
        txtAnio.setText(String.valueOf(modeloTabla.getValueAt(fila, 4)));
        cboEstado.setSelectedItem(BookStatus.fromText(String.valueOf(modeloTabla.getValueAt(fila, 5))));
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtAnio.setText("");
        txtBusqueda.setText("");
        cboEstado.setSelectedItem(BookStatus.DISPONIBLE);
        tablaLibros.clearSelection();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.WARNING_MESSAGE);
    }
}