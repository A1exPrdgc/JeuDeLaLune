package JeuDeLaLune.metier;

import JeuDeLaLune.Controleur;

public class Joueur 
{
    public static final int TAILLE_MAIN = 3;


    private CarteUnique[] mainCarteUnique;

    private int score;

    public Joueur()
    {
        this.mainCarteUnique = new CarteUnique[Joueur.TAILLE_MAIN];
        this.score = 0;

        this.remplirMain();
    }
   

    public void ajouterCarteAleatoire()
    {
        if(this.mainCarteUnique.length <= Joueur.TAILLE_MAIN)
        {
            CarteUnique carteUnique = new CarteUnique(CarteLunaires.values()[(int)(Math.random() * (CarteLunaires.values().length - 1))]);
            this.mainCarteUnique[this.getFreeSlot()] = carteUnique;
            carteUnique.setX(Controleur.WIDTH - (((this.getIndexOf(carteUnique) + 1) - (Joueur.TAILLE_MAIN + 1)) * -1) * (64 + 10) - 10); 
            carteUnique.setY(Controleur.HEIGHT - 64 - 45);
            carteUnique.setOrigin_x(Controleur.WIDTH - (((this.getIndexOf(carteUnique) + 1) - (Joueur.TAILLE_MAIN + 1)) * -1) * (64 + 10) - 10); 
            carteUnique.setOrigin_y(Controleur.HEIGHT - 64 - 45);
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public int getIndexOf(CarteUnique carte)
    {
        for (int i = 0; i < mainCarteUnique.length; i++)
        {
            if(this.mainCarteUnique[i] == carte)
            {
                return i;
            }    
        }
        return -1;
    }

    public int getFreeSlot()
    {
        for (int i = 0; i < this.mainCarteUnique.length; i++) 
        {
            if(this.mainCarteUnique[i] == null)
            {
                return i;
            }    
        }
        return -1;
    }

    public void retirerCarte(CarteUnique CarteUnique)
    {
        if(this.mainCarteUnique.length > 0)
        {
            CarteUnique.reduireNum();
            this.mainCarteUnique[this.getIndexOf(CarteUnique)] = null;
        }
    }

    public void remplirMain()
    {
        for (int i = 0; i < TAILLE_MAIN; i++) 
        {
            this.ajouterCarteAleatoire();    
        }
    }

    public CarteUnique[] getMainCarteUnique() {
        return this.mainCarteUnique;
    }
}
