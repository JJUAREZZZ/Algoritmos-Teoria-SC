package quicklibrary.view.gui;

import java.awt.*;
import javax.swing.*;
import quicklibrary.controller.LibraryController;
import quicklibrary.model.Book;
import quicklibrary.structures.tree.AVLNode;
import quicklibrary.structures.tree.CustomAVLTree;

public class AVLTreeVisualizerPanel extends JPanel {
    private LibraryController controlador;
    private final int RADIO = 24;
    private final int ESPACIO_V = 65;

    private final Color COLOR_FONDO       = new Color(248, 249, 250);
    private final Color COLOR_LINEAS      = new Color(180, 190, 200);
    private final Color COLOR_RAIZ        = new Color(231, 76, 60);
    private final Color COLOR_INTERM      = new Color(52, 152, 219);
    private final Color COLOR_HOJA        = new Color(46, 204, 113);
    private final Color COLOR_TEXTO       = Color.WHITE;

    public AVLTreeVisualizerPanel(LibraryController controlador) {
        this.controlador = controlador;
        setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(2400, 800));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        CustomAVLTree<Book> arbol = null;
        try {
            arbol = controlador.obtenerArbolLibros();
        } catch (Exception e) {}

        if (arbol != null && !arbol.isEmpty()) {
            AVLNode<Book> raiz = arbol.getRaiz();
            int xInicial = getWidth() / 2;
            int yInicial = 45;
            int espacioHorizontalInicial = getWidth() / 4;

            dibujarNodo(g2, raiz, xInicial, yInicial, espacioHorizontalInicial, raiz, arbol);
        } else {
            g2.setColor(new Color(120, 130, 140));
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 15));
            FontMetrics fm = g2.getFontMetrics();
            String msg = "El arbol esta vacio. Agrega libros para ver la estructura";
            g2.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, 100);
        }
    }

    private void dibujarNodo(Graphics2D g2, AVLNode<Book> nodo, int x, int y, int dx, AVLNode<Book> raizGlobal, CustomAVLTree<Book> arbol) {
        if (nodo == null || nodo.dato == null) return;

        int nuevoDx = Math.max(35, dx / 2);
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(COLOR_LINEAS);

        if (nodo.izquierda != null) {
            g2.drawLine(x, y, x - dx, y + ESPACIO_V);
            dibujarNodo(g2, nodo.izquierda, x - dx, y + ESPACIO_V, nuevoDx, raizGlobal, arbol);
        }
        if (nodo.derecha != null) {
            g2.drawLine(x, y, x + dx, y + ESPACIO_V);
            dibujarNodo(g2, nodo.derecha, x + dx, y + ESPACIO_V, nuevoDx, raizGlobal, arbol);
        }

        if (nodo == raizGlobal) {
            g2.setColor(COLOR_RAIZ);
        } else if (nodo.izquierda == null && nodo.derecha == null) {
            g2.setColor(COLOR_HOJA);
        } else {
            g2.setColor(COLOR_INTERM);
        }

        g2.fillOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);

        g2.setColor(COLOR_TEXTO);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));

        String textoCodigo = nodo.dato.getCodigo();
        if (textoCodigo == null) textoCodigo = "?";

        FontMetrics fm = g2.getFontMetrics();
        int textX = x - (fm.stringWidth(textoCodigo) / 2);
        int textY = y + (fm.getAscent() / 2) - 2;
        g2.drawString(textoCodigo, textX, textY);

        g2.setColor(new Color(80, 80, 80));
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        int altIzq = (nodo.izquierda != null) ? nodo.izquierda.altura : 0;
        int altDer = (nodo.derecha != null) ? nodo.derecha.altura : 0;
        int balance = altIzq - altDer;

        g2.drawString("bf=" + balance, x - 12, y + RADIO + 14);
    }
}