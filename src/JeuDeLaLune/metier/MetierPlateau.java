package JeuDeLaLune.metier;

import java.util.ArrayList;
import java.util.List;

import JeuDeLaLune.Controleur;

public class MetierPlateau 
{
    private List<Emplacement> plateauDeJeu;
    @SuppressWarnings("unused")
    private Controleur ctrl;

    public MetierPlateau(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.plateauDeJeu = new ArrayList<Emplacement>();
    }

    public boolean ajouterEmplacementPlateau(Emplacement emplacement)
    {
        for (Emplacement e : this.plateauDeJeu) 
        {
            if (emplacement.equals(e)) 
            {
                return false;
            }    
        }
        this.plateauDeJeu.add(emplacement);
        return true;
    }

    public List<Emplacement> getPlateauDeJeu() {
        return this.plateauDeJeu;
    }

    public void genererPlateau(int nbEmplacements, int minX, int minY, int maxX, int maxY, int frequenceX, int frequenceY)
    {
        //place les emplacements sur le plateau de manière aléatoire
        for (int i = 0; i < nbEmplacements; i++) 
        {
            Emplacement emplacement;
            do {
                int cooX = MetierPlateau.genererChiffreAleatoireEntreMinMaxAFrequence(minX, maxX, frequenceX);
                int cooY = MetierPlateau.genererChiffreAleatoireEntreMinMaxAFrequence(minY, maxY, frequenceY);
    
                emplacement = new Emplacement(cooX, cooY);
            } while (this.estEmplacementDejaExistant(emplacement));

            this.ajouterEmplacementPlateau(emplacement);
        }

        //fait les connections entre les emplacements (jusq'à 3 connections par emplacements)
        for (int i = 0; i < nbEmplacements; i++) 
        {
            Emplacement emplacementCible = this.getEmplacementAUnIndice(i);

            int nbVoisins = 3;//MetierPlateau.genererChiffreAleatoireEntreMinMaxAFrequence(3, 5, 0);

            int nbVoisinAAjouter = nbVoisins - emplacementCible.getLstEmplacementsVoisins().size();
            
            if (nbVoisinAAjouter > 0) 
            {
                for (int j = 0; j < nbVoisinAAjouter; j++) 
                {
                    Emplacement emplacementProche = this.getEmplacementLePlusProche(emplacementCible);
                    if(!emplacementCible.equals(emplacementProche))
                    {
                        emplacementCible.ajouterVoisin(emplacementProche);
                        emplacementProche.ajouterVoisin(emplacementCible);
                    }
                }   
            }
        }
    }

    public boolean estEmplacementDejaExistant(Emplacement emplacement)
    {
        for (Emplacement e : this.plateauDeJeu) 
        {
            if(e.equals(emplacement))
            {
                return true;
            }    
        }
        return false;
    }

    public Emplacement getEmplacementAUnIndice(int indice)
    {
        int vraiIndice = indice;
        if(indice + 1 > this.plateauDeJeu.size())
        {
            vraiIndice = this.plateauDeJeu.size() - 1;
        }
        if(indice < 0)
        {
            vraiIndice = 0;
        }
        return this.plateauDeJeu.get(vraiIndice);
    }

    
    public Emplacement getEmplacementLePlusProche(Emplacement emplacement)
    {
        if(!this.plateauDeJeu.isEmpty())
        {
            Emplacement emplacementLePlusProche = this.plateauDeJeu.get(0);
            double distanceLaPlusCourte = MetierPlateau.calculDistanceLaPlusCourte(emplacement, emplacementLePlusProche);

            for (Emplacement e : plateauDeJeu) 
            {   
                double distance = MetierPlateau.calculDistanceLaPlusCourte(emplacement, e);
                if(distance < distanceLaPlusCourte                      &&
                    !e.equals(emplacement)                              &&
                    !emplacement.getLstEmplacementsVoisins().contains(e))
                {
                    distanceLaPlusCourte = distance;
                    emplacementLePlusProche = e;
                }
            }   
            return emplacementLePlusProche;
        }
        return null;
    }

    public static double calculDistanceLaPlusCourte(Emplacement emplacement1, Emplacement emplacement2)
    {
        //System.out.println("sqrt((" + emplacement2.getY() + " - " + emplacement1.getY() + ")² + (" + emplacement2.getX() + " - " + emplacement1.getX() + ")²) = " + Math.sqrt(MetierPlateau.carre(emplacement2.getY() - emplacement1.getY()) + MetierPlateau.carre(emplacement2.getX() - emplacement1.getX())));
        return Math.sqrt(Math.pow(emplacement2.getY() - emplacement1.getY(), 2) + Math.pow(emplacement2.getX() - emplacement1.getX(), 2));
    }

    public static int genererChiffreAleatoireEntreMinMaxAFrequence(int min, int max, int frequence)
    {
        int vraiFrequence;

        if (frequence == 0) 
        {
            vraiFrequence = 1;
        }
        else
        {
            vraiFrequence = frequence;
        }

        int res = (int)((Math.random() * (max + 1 - min)) + min);

        return res - (res % vraiFrequence);
    }
}
