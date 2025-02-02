package JeuDeLaLune.metier;

public class CarteUnique 
{
    private CarteLunaires carte;

    private static int nbCarte = 0;
    private int numCarte;

    private int origin_x;
    private int origin_y;

    private int x;
    private int y;

    public CarteUnique(CarteLunaires carte)
    {
        this.carte = carte;
        this.numCarte = nbCarte++;
    }

    public CarteLunaires getCarte() {
        return carte;
    }

    public int getNumCarte() {
        return numCarte;
    }

    public void reduireNum()
    {
        this.numCarte = nbCarte--;
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

    public int getOrigin_x() {
        return origin_x;
    }

    public int getOrigin_y() {
        return origin_y;
    }

    public void setOrigin_x(int origin_x) {
        this.origin_x = origin_x;
    }

    public void setOrigin_y(int origin_y) {
        this.origin_y = origin_y;
    }

    
    @Override
    public String toString() {
        return this.numCarte + " : " + this.carte.name() + "[" + this.x + "," + this.y + "]";
    }
}
