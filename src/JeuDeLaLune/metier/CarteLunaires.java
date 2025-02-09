package JeuDeLaLune.metier;

public enum CarteLunaires 
{
    //listes des phases lunaires
    NOUVELLE_LUNE       (0, 0),
    LUNE_GIBBEUSE_1     (1, 3),
    PREMIER_QUARTIER    (2, 2),
    PREMIER_CROISSANT   (3, 1),
    LUNE_GIBBEUSE_2     (4, 5),
    DERNIER_QUARTIER    (5, 6),
    DERNIER_CROISSANT   (6, 7),
    PLEINE_LUNE         (7, 4);

    private int id;
    private int ordre;

    private CarteLunaires(int id, int ordre)
    {
        this.id = id;
        this.ordre = ordre;
    }

    public int getId() {
        return id;
    }

    public int getOrdre() {
        return ordre;
    }

    public static CarteLunaires getCarteParId(int id)
    {
        for (CarteLunaires c : CarteLunaires.values()) 
        {
            if (c.getId() == id) 
            {
                return c;    
            }    
        }
        return null;
    }
}
