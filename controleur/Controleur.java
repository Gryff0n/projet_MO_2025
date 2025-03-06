package controleur;

import modele.Joueur;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Joueur[] joueurs = new Joueur[2];
    int nbJoueurs = 0;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void create(String nomJoueur) {
        joueurs[nbJoueurs]=(new Joueur(nomJoueur));
        nbJoueurs++;
    }
}
