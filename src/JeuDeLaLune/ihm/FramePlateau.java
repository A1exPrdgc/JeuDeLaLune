package JeuDeLaLune.ihm;

import javax.swing.JFrame;

import JeuDeLaLune.Controleur;

public class FramePlateau extends JFrame
{
    private Controleur ctrl;

    private PanelPlateau panel;

    public FramePlateau(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle("Le jeu de la lune");
        this.setSize(Controleur.WIDTH, Controleur.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        //this.setLocationRelativeTo(null);

        this.panel = new PanelPlateau(this.ctrl);

        this.add(this.panel);

        this.setVisible(true);
    }    

    public void majIhm()
    {
        this.panel.majIhm();
    }
}
