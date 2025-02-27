package JeuDeLaLune.metier;

import JeuDeLaLune.Controleur;

public class Bot extends Joueur
{
    public Controleur ctrl;

    public Bot(Controleur ctrl)
    {
        super();
        this.ctrl = ctrl;
    }

    public void jouer()
    {
        int cpt = 0;
        Emplacement emplacementVidePotentiel = this.ctrl.getPlateauDeJeu().get(cpt);
        CarteUnique carteSelectionnee = this.mainCarteUnique[(int)(Math.random() * (this.mainCarteUnique.length - 1))];

        while (emplacementVidePotentiel.getCarteAssocie() != null) 
        {
            emplacementVidePotentiel = this.ctrl.getPlateauDeJeu().get(cpt);
            cpt++;    
        }
        emplacementVidePotentiel.associerCarte(carteSelectionnee);
        emplacementVidePotentiel.attribuerPossesseur(TypeJoueur.MACHINE);
        this.retirerCarte(carteSelectionnee);
        this.ajouterCarteAleatoire();
    }
}
