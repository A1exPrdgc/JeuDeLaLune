package JeuDeLaLune.metier;

import java.io.File;
import java.util.HashSet;

public class Emplacement 
{
    private static final int NB_IMAGES = (new File("./src/JeuDeLaLune/ressources/images/emplacements")).listFiles().length;

    private int x;
    private int y;

    private static int nbEmplacements = 0;
    private int numEmplacement;
    private int idImage;
    private CarteUnique carteAssocie;
    private TypeJoueur possesseur;
    private boolean parcouru;

    private HashSet<Emplacement> lstEmplacementsVoisins;

    public Emplacement(int x, int y)
    {
        this.numEmplacement         = nbEmplacements++;
        this.lstEmplacementsVoisins = new HashSet<Emplacement>();

        this.idImage = (int)(Math.random() * (Emplacement.NB_IMAGES - 1));

        this.x = x;
        this.y = y;

        this.parcouru = false;
    }

    public void parcourir()
    {
        this.parcouru = true;
    }

    public void deparcourir()
    {
        this.parcouru = false;
    }

    public boolean isParcouru() {
        return parcouru;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIdImage() {
        return this.idImage;
    }

    public boolean ajouterVoisin(Emplacement voisin)
    {
        if (!this.estVoisinDejaExistant(voisin)) 
        {
            this.lstEmplacementsVoisins.add(voisin);   
            return true;
        }
        return false;
    }

    public void attribuerPossesseur(TypeJoueur typeJoueur)
    {
        this.possesseur = typeJoueur;
    }

    public void retirerVoisin(Emplacement voisin)
    {
        this.lstEmplacementsVoisins.remove(voisin);
    }

    public int getNumEmplacement() {
        return this.numEmplacement;
    }

    public HashSet<Emplacement> getLstEmplacementsVoisins() {
        return lstEmplacementsVoisins;
    }

    public boolean associerCarte(CarteUnique carte)
    {
        if(this.carteAssocie == null)
        {
            this.carteAssocie = carte;
            return true;
        }
        return false;
    }

    public boolean estVoisinDejaExistant(Emplacement emplacement)
    {
        for (Emplacement e : this.lstEmplacementsVoisins) 
        {
            if(e.equals(emplacement))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) 
    {
        return ((Emplacement)obj).getX() == this.x && ((Emplacement)obj).getY() == this.y;
    }

    public String afficherEmplacementsAvecVoisins()
    {
        String s = this.toString() + " :\n";

        for (Emplacement e : this.lstEmplacementsVoisins) 
        {
            s += "\t- " + e + "\n";    
        }
        return s;
    }

    @Override
    public String toString() 
    {
        String s = "Emplacement " + (char)(this.numEmplacement + 'A') + " [" + this.x + "," + this.y + "]";  
        return s;  
    }

    public CarteUnique getCarteAssocie() {
        return this.carteAssocie;
    }

    public TypeJoueur getPossesseur() {
        return this.possesseur;
    }
}
