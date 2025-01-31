package JeuDeLaLune;
import JeuDeLaLune.ihm.FramePlateau;
import JeuDeLaLune.metier.MetierPlateau;

public class Controleur 
{
    private MetierPlateau metier;
    private FramePlateau ihm;


    public Controleur()
    {
        this.metier = new MetierPlateau(this);
        this.ihm = new FramePlateau(this);
    }   

    public static void main(String[] args)
    {
        new Controleur();
    }
}
