package JeuDeLaLune.metier;

import java.util.ArrayList;
import java.util.List;

public class Joueur 
{
    private static final int TAILLE_MAIN = 3;

    private List<CarteLunaires> mainCarteLunaires;

    public Joueur()
    {
        this.mainCarteLunaires = new ArrayList<CarteLunaires>(Joueur.TAILLE_MAIN);
    }
   

    public void ajouterCarteAleatoire()
    {
        if(this.mainCarteLunaires.size() <= Joueur.TAILLE_MAIN)
        {
            this.mainCarteLunaires.add(CarteLunaires.values()[(int)(Math.random() * (CarteLunaires.values().length - 1))]);
        }
    }

    public void retirerCarte(int index)
    {
        if(this.mainCarteLunaires.size() > 0)
        {
            this.mainCarteLunaires.remove(index);
        }
    }
}
