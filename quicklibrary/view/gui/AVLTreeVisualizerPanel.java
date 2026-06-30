package quicklibrary.view.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
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
    }

    private void dibujarNodo(Graphics2D g2, AVLNode<Book> nodo, int x, int y, int dx, AVLNode<Book> raizGlobal, CustomAVLTree<Book> arbol) {
    }
}
