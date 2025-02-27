package JeuDeLaLune;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import java.awt.event.MouseEvent;

import JeuDeLaLune.ihm.FramePlateau;
import JeuDeLaLune.metier.Bot;
import JeuDeLaLune.metier.CarteUnique;
import JeuDeLaLune.metier.Emplacement;
import JeuDeLaLune.metier.Joueur;
import JeuDeLaLune.metier.MetierPlateau;
import JeuDeLaLune.metier.TypeJoueur;

//TODO : fixer le score du bot

public class Controleur 
{
    public static final int WIDTH  = 1045;
    public static final int HEIGHT = 630;
    public static final int BORDER = 100;

    private MetierPlateau metier;
    private FramePlateau ihm;
    private Joueur joueur;
    private Bot machine;

    private CarteUnique carteSelectionnee;

    private int numTour;

    public Controleur()
    {
        this.metier  = new MetierPlateau(this);
        this.ihm     = new FramePlateau (this);
        this.machine = new Bot          (this);
        this.joueur  = new Joueur       ();

        this.numTour = 0;

        this.metier.genererPlateau(10, Controleur.BORDER, Controleur.BORDER, Controleur.WIDTH - Controleur.BORDER, Controleur.HEIGHT - 200, 100, 100);
        this.ihm.majIhm();
    }   

    public void dessinerTout(Graphics2D g2)
    {
        this.dessinerChemin         (g2);
        this.dessinerEmplacement    (g2);
        this.dessinerMainJoueur     (g2);
        this.dessinerScoreJoueur    (g2);
        this.dessinerScoreMachine   (g2);
        this.dessinerNbTour         (g2);
    }

    public void dessinerChemin(Graphics2D g2)
    {
        for (Emplacement e : this.metier.getPlateauDeJeu()) 
        {
            for (Emplacement voisin : e.getLstEmplacementsVoisins()) 
            {
                if(e.getCarteAssocie() == null || voisin.getCarteAssocie() == null)
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

    public void dessinerScoreJoueur(Graphics2D g2)
    {
        g2.setFont(new Font(null, 0, 64));
        g2.setColor(new Color(255, 237, 165));
        g2.drawString(String.format("%03d", this.joueur.getScore()), 10, HEIGHT - 50);
    }

    public void dessinerNbTour(Graphics2D g2)
    {
        g2.setFont(new Font(null, 0, 64));
        g2.setColor(new Color(255, 237, 165));
        g2.drawString(String.format("%02d", this.numTour), WIDTH - 100, 60);
    }

    public void dessinerScoreMachine(Graphics2D g2)
    {
        g2.setFont(new Font(null, 0, 64));
        g2.setColor(new Color(255, 237, 165));
        g2.drawString(String.format("%03d", this.machine.getScore()), 10, 60);
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
                if(emplacement.getCarteAssocie() == null)
                {
                    if(this.numTour % 2 == 0)
                    {
                        //le joueur peut jouer
                        emplacement.associerCarte(this.carteSelectionnee);
                        emplacement.attribuerPossesseur(TypeJoueur.JOUEUR);
                        this.joueur.jouer(this.carteSelectionnee);
                    }
                    this.incrementerTour();
                    this.ContabiliserPoints();
                    this.majIhm();

                    //la machine joue après que le joueur il ait jouer
                    this.machine.jouer();
                    this.incrementerTour();
                }
                else
                {
                    this.carteSelectionnee.setX(this.carteSelectionnee.getOrigin_x());
                    this.carteSelectionnee.setY(this.carteSelectionnee.getOrigin_y());
                }
            }
            this.carteSelectionnee = null;
        }
        if(this.estPartieTermine())
        {
            finDePartie();
        }
        this.ContabiliserPoints();
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

    public void finDePartie()
    {
        this.ihm.setVisible(false);
        if(this.machine.getScore() > this.joueur.getScore())
        {
            JOptionPane.showMessageDialog(null, "L'ordinateur à gagné la partie", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Félicitation, vous avez gagné la partie", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        }
        System.exit(0);
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

    public boolean estPartieTermine()
    {
        for (Emplacement e : this.metier.getPlateauDeJeu()) 
        {
            if(e.getCarteAssocie() == null)
            {
                return false;
            }    
        }
        return true;
    }

    public void ContabiliserPoints()
    {
        List<Emplacement> lstEmplacement = this.metier.getPlateauDeJeu();

        this.joueur.setScore(0);
        for (Emplacement emplacement : lstEmplacement) 
        {
            if(emplacement.getPossesseur() != null && emplacement.getCarteAssocie() != null)
            {
                //gain par emplacement possédé
                if(emplacement.getPossesseur() == TypeJoueur.JOUEUR)
                {
                    this.incrementerScore();
                }

                for (Emplacement voisin : emplacement.getLstEmplacementsVoisins()) 
                {
                    //gain par 2 carte similaire connecté
                    if(estCartesSimilaireParEmplacement(emplacement, voisin))
                    {
                        this.incrementerScore();
                    }

                    //gain par complétion de lune
                    if(estLuneCompleteParEmplacement(emplacement, voisin))
                    {
                        this.incrementerScore();
                    }
        
                    //gain par suite de lune
                    if(estSuiteCompleteParEmplacement(emplacement, voisin))
                    {
                        this.incrementerScore();
                    } 
                }
            }
            emplacement.parcourir();
        }
        this.deparcourirTout();
    }




    public boolean estCartesSimilaireParEmplacement(Emplacement emplacement, Emplacement voisin)
    {
        if(voisin.getPossesseur() != null && voisin.getCarteAssocie() != null)
        {
            return voisin.getCarteAssocie().getCarte() == emplacement.getCarteAssocie().getCarte() && emplacement.getPossesseur() == voisin.getPossesseur() && !voisin.isParcouru();
        }
        return false;
    }

    public void incrementerScore()
    {
        if(this.numTour % 2 == 0)
        {
            this.joueur.increaseScore(1);
        }
        else
        {
            this.machine.increaseScore(1);
        }
    }


    public boolean estLuneCompleteParEmplacement(Emplacement emplacement, Emplacement voisin)
    {
        if(voisin.getPossesseur() != null && voisin.getCarteAssocie() != null)
        {
            return voisin.getCarteAssocie().getCarte().getId() + emplacement.getCarteAssocie().getCarte().getId() == 7 && emplacement.getPossesseur() == voisin.getPossesseur() && !voisin.isParcouru();
        }
        return false;
    }


    public boolean estSuiteCompleteParEmplacement(Emplacement emplacement, Emplacement voisin)
    {
        final int NB_TYPE_CARTES = 7;

        if(voisin.getPossesseur() != null && voisin.getCarteAssocie() != null)
        {
            for (Emplacement voisin2 : emplacement.getLstEmplacementsVoisins()) 
            {    
                if (voisin != voisin2 && emplacement.getPossesseur() == voisin.getPossesseur() && emplacement.getPossesseur() == voisin2.getPossesseur()) 
                {
                    int voisinAvant = (voisin .getCarteAssocie().getCarte().getOrdre() - 1);

                    if(voisinAvant < 0){voisinAvant += 8;}

                    int voisinApres = (voisin2.getCarteAssocie().getCarte().getOrdre() + 1) % (NB_TYPE_CARTES + 1);

                    if( emplacement.getCarteAssocie().getCarte().getOrdre() == voisinAvant && 
                        emplacement.getCarteAssocie().getCarte().getOrdre() == voisinApres)
                    {
                        return true;
                    }
                }   
            }
        }
        return false;
    }


    public void deparcourirTout()
    {
        for (Emplacement e : this.metier.getPlateauDeJeu()) 
        {
            e.deparcourir();    
        }
    }

    public void incrementerTour()
    {
        this.numTour++;
    }

    public List<Emplacement> getPlateauDeJeu()
    {
        return this.metier.getPlateauDeJeu();
    }
}
