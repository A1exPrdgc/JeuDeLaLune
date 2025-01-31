package JeuDeLaLune.test;

import JeuDeLaLune.metier.Emplacement;
import JeuDeLaLune.metier.MetierPlateau;

public class Test 
{
    public static void main(String[] args) 
    {
        MetierPlateau plateau = new MetierPlateau(null);

        plateau.genererPlateau(8, 50, 50, 950, 950, 100, 100);

        for (Emplacement e : plateau.getPlateauDeJeu()) 
        {
            System.out.println(e.afficherEmplacementsAvecVoisins());    
        }
    }    
}
