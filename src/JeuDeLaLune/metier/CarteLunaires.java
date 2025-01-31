package JeuDeLaLune.metier;

public enum CarteLunaires 
{
    //listes des phases lunaires
    NOUVELLE_LUNE       (0),
    PREMIER_CROISSANT   (1),
    PREMIER_QUARTIER    (2),
    LUNE_GIBBEUSE_1     (3),
    PLEINE_LUNE         (4),
    LUNE_GIBBEUSE_2     (5),
    DERNIER_QUARTIER    (6),
    DERNIER_CROISSANT   (7);

    private int id;

    private CarteLunaires(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public CarteLunaires getCarteParId(int id)
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
