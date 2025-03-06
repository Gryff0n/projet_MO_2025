package controleur;

import modele.Joueur;
import modele.Partie;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Joueur[] joueurs = new Joueur[2];
    int nbJoueurs = 0;
    int joueurCourant = 1;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void jouer() {
        while (nbJoueurs < 2) {
            create(ihm.demanderNomJoueur(nbJoueurs + 1));
        }
        boolean fini = false;
        while (!fini) {
            Partie partie = new Partie();
            partie.initialiser();
            ihm.afficher(partie.toString());
            boolean partieTerminee = false;
            String coup;
            while (!partieTerminee){
                boolean coupValide = false;
                while (!coupValide){
                    coup=ihm.demanderCoup(joueurs[joueurCourant].getNom());
                    coupValide = partie.coupValide(coup,joueurCourant);
                }

            }
        }

    }

    public void create(String nomJoueur) {
        joueurs[nbJoueurs]=(new Joueur(nomJoueur));
        nbJoueurs++;
    }
    public Joueur getJoueur(int numero){
        return joueurs[numero-1];
    }
}
