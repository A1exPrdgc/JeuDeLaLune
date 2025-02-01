package JeuDeLaLune;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import JeuDeLaLune.ihm.FramePlateau;
import JeuDeLaLune.metier.CarteLunaires;
import JeuDeLaLune.metier.Emplacement;
import JeuDeLaLune.metier.Joueur;
import JeuDeLaLune.metier.MetierPlateau;

public class Controleur 
{
    public static final int WIDTH  = 1045;
    public static final int HEIGHT = 630;
    public static final int BORDER = 100;

    private MetierPlateau metier;
    private FramePlateau ihm;
    private Joueur joueur;

    public Controleur()
    {
        this.metier = new MetierPlateau(this);
        this.ihm    = new FramePlateau (this);
        this.joueur = new Joueur();

        this.metier.genererPlateau(10, Controleur.BORDER, Controleur.BORDER, Controleur.WIDTH - 200, Controleur.HEIGHT - Controleur.BORDER, 100, 100);
        this.ihm.majIhm();
    }   

    public void dessinerTout(Graphics2D g2)
    {
        for (Emplacement e : this.metier.getPlateauDeJeu()) 
        {
            for (Emplacement voisin : e.getLstEmplacementsVoisins()) 
            {
                if(e.getCarteAssocie() == null && voisin.getCarteAssocie() == null)
                {
                    g2.setColor(new Color(65, 27, 184));
                }
                else
                {
                    g2.setColor(new Color(0, 240, 255));
                }
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(e.getX() + 32, e.getY() + 32, voisin.getX() + 32, voisin.getY() + 32);
            }    
        }

        for (Emplacement e : this.metier.getPlateauDeJeu()) 
        {
            if(e.getCarteAssocie() == null)
            {
                try {
                    Image img = ImageIO.read(new File("./src/JeuDeLaLune/ressources/images/emplacements/" + e.getIdImage() + ".png"));
    
                    g2.drawImage(img, e.getX(), e.getY(), null);
    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }  
            } 
        }

        for (int i = 0; i < Joueur.TAILLE_MAIN; i++)
        {
            try {
                Image img = ImageIO.read(new File("./src/JeuDeLaLune/ressources/images/cartes/" + this.joueur.getMainCarteLunaires().get(i).getId() + ".png"));   
                g2.drawImage(img, WIDTH - (i + 1) * (64 + 10) - 10, HEIGHT - 64 - 45, ihm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void majIhm()
    {
        this.ihm.majIhm();
    }


    public static void main(String[] args)
    {
        new Controleur();
    }
}
