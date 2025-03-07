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
                boolean syntaxeValide = false;
                while (!syntaxeValide || !coupValide) {
                    coup=ihm.demanderCoup(joueurs[joueurCourant].getNom());
                    syntaxeValide=syntaxCheck(coup);
                    if (syntaxeValide) {
                        coupValide = partie.coupValide(coup,joueurCourant);
                    }
                }

            }
        }

    }

    public void create(String nomJoueur) {
        joueurs[nbJoueurs]=(new Joueur(nomJoueur));
        nbJoueurs++;
    }

    public boolean syntaxCheck(String coup) {
        //vérifie la syntaxe du coup entré par le joueur
        if (coup.isEmpty() || coup.length() > 3 || coup.length() == 2) {
            //mauvaise longueur
            System.out.println("Mauvaise syntaxe (longueur)");
            return false;
        }
        else if(coup.length() == 1) {
            if (coup.charAt(0) != 'P') {
                //syntaxe du passage de tour incorrecte
                System.out.println("Mauvaise syntaxe (!P)");
                return false;
            }
            //passage de tour correct syntaxiquement
            return true;
        }
        char[] charCoup = coup.toCharArray();
        if (!(Character.isDigit(charCoup[0]) && charCoup[1] == ' ' && Character.isAlphabetic(charCoup[2]))) {
                //coup de bonne longueur mais mal écrit
                System.out.println("Mauvaise syntaxe (syn)");
                return false;
        }
        return true;
    }

    public Joueur getJoueur(int numero){
        return joueurs[numero-1];
    }
}