package JeuDeLaLune.ihm;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import JeuDeLaLune.Controleur;

public class PanelPlateau extends JPanel
{
    private Controleur ctrl;

    private Graphics2D g2;
    private Image fond;

    public PanelPlateau(Controleur ctrl)
    {
        this.ctrl = ctrl;

        try {
            this.fond = ImageIO.read(new File("./src/JeuDeLaLune/ressources/images/fond.png"));   
        } catch (Exception e) {
            e.printStackTrace();
        }

        GestionnaireSouris handler = new GestionnaireSouris();
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);

    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        this.g2 = (Graphics2D)g;

        this.g2.drawImage(fond, 0, 0, null);

        this.ctrl.dessinerTout(this.g2);
    }

    public void majIhm()
    {
        this.repaint();
    }

    private class GestionnaireSouris extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e) 
        {
            PanelPlateau.this.ctrl.getCarteClique(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) 
        {
            PanelPlateau.this.ctrl.carteEnMouvement(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            PanelPlateau.this.ctrl.actionsSourisRelache(e);
        }
        
    }
}
