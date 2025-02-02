package JeuDeLaLune;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.event.MouseEvent;

import JeuDeLaLune.ihm.FramePlateau;
import JeuDeLaLune.metier.CarteUnique;
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

    private CarteUnique carteSelectionnee;

    public Controleur()
    {
        this.metier = new MetierPlateau(this);
        this.ihm    = new FramePlateau (this);
        this.joueur = new Joueur       ();

        this.metier.genererPlateau(10, Controleur.BORDER, Controleur.BORDER, Controleur.WIDTH - Controleur.BORDER, Controleur.HEIGHT - 200, 100, 100);
        this.ihm.majIhm();
    }   

    public void dessinerTout(Graphics2D g2)
    {
        this.dessinerChemin(g2);
        this.dessinerEmplacement(g2);
        this.dessinerMainJoueur(g2);
    }

    public void dessinerChemin(Graphics2D g2)
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
    }

    public void dessinerEmplacement(Graphics2D g2)
    {
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
            else
            {
                try {
                    Image img = ImageIO.read(new File("./src/JeuDeLaLune/ressources/images/cartes/" + e.getCarteAssocie().getCarte().getId() + ".png"));
    
                    g2.drawImage(img, e.getX(), e.getY(), null);
    
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
    }

    public void dessinerMainJoueur(Graphics2D g2)
    {

        for (int i = 0; i < this.joueur.getMainCarteUnique().length; i++)
        {
            try 
            {
                CarteUnique carte = this.joueur.getMainCarteUnique()[i];
                Image img = ImageIO.read(new File("./src/JeuDeLaLune/ressources/images/cartes/" + carte.getCarte().getId() + ".png"));

                g2.drawImage(img, carte.getX() , carte.getY(), ihm);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void getCarteClique(MouseEvent e)
    {
        for (CarteUnique c : this.joueur.getMainCarteUnique()) 
        {
            if(e.getX() >= c.getX() && e.getY() >= c.getY() && e.getX() <= c.getX() + 64 && e.getY() <= c.getY() + 64)
            {
                System.out.println(c.getCarte().name());
                this.carteSelectionnee = c;
            }
        }
    }

    public void actionsSourisRelache(MouseEvent e)
    {
        if(this.carteSelectionnee != null)
        {
            Emplacement emplacement = this.getEmplacementAPosSouris(e);
            if(emplacement == null)
            {
                this.carteSelectionnee.setX(this.carteSelectionnee.getOrigin_x());
                this.carteSelectionnee.setY(this.carteSelectionnee.getOrigin_y());
            }
            else
            {
                emplacement.associerCarte(this.carteSelectionnee);
                this.joueur.retirerCarte(this.carteSelectionnee);
                this.joueur.ajouterCarteAleatoire();
            }
            this.carteSelectionnee = null;
        }
        this.majIhm();
    }

    public Emplacement getEmplacementAPosSouris(MouseEvent e)
    {
        for (Emplacement emplacement : this.metier.getPlateauDeJeu()) 
        {
            if(e.getX() >= emplacement.getX() && e.getY() >= emplacement.getY() && e.getX() <= emplacement.getX() + 64 && e.getY() <= emplacement.getY() + 64)
            {
                return emplacement;
            }
        }
        return null;
    }

    public void majIhm()
    {
        this.ihm.majIhm();
    }


    public static void main(String[] args)
    {
        new Controleur();
    }

    public void carteEnMouvement(MouseEvent e)
    {
        if(this.carteSelectionnee != null)
        {
            this.carteSelectionnee.setX(e.getX() - 32);
            this.carteSelectionnee.setY(e.getY() - 32);
        }
        this.majIhm();
    }
}
