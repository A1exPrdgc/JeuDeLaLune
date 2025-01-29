package metier;

import java.util.ArrayList;
import java.util.List;

public class Emplacement 
{
    private int x;
    private int y;

    private static int nbEmplacements = 0;
    private int numEmplacement;

    private List<Emplacement> lstEmplacementsVoisins;

    public Emplacement(int x, int y)
    {
        this.numEmplacement         = nbEmplacements++;
        this.lstEmplacementsVoisins = new ArrayList<Emplacement>();

        this.x = x;
        this.y = y;
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

    public void ajouterVoisin(Emplacement voisin)
    {
        this.lstEmplacementsVoisins.add(voisin);
    }

    public void retirerVoisin(Emplacement voisin)
    {
        this.lstEmplacementsVoisins.remove(voisin);
    }

    public int getNumEmplacement() {
        return this.numEmplacement;
    }
}
