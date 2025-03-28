package main;

import controleur.Controleur;
import vue.Ihm;
import modele.Jeux.Awale;

//Itération 1

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.jouer();
    }
}
