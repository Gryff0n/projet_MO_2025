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
        create(ihm.demanderNomJoueur(nbJoueurs + 1),"Noire");
        create(ihm.demanderNomJoueur(nbJoueurs + 1),"Blanc");
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
                while (!coupValide) {
                    coup=ihm.demanderCoup(joueurs[joueurCourant-1].getNom() + " ("+ joueurs[joueurCourant-1].getCouleur()+")").trim();
                    syntaxeValide=syntaxCheck(coup);
                    if (syntaxeValide) {
                        if (coup.charAt(0) == 'P'){
                            coupValide = partie.coupImpossible(joueurCourant);
                        }
                        else {
                            int l = Integer.parseInt(coup.substring(0,1))-1;
                            int c = Character.getNumericValue(coup.charAt(2))-10;
                            coupValide = partie.coupValide(l,c, joueurCourant);
                            if (coupValide) {
                                partie.jouerCoup(l,c,joueurCourant);
                            }
                        }
                    }
                }
                joueurCourant = joueurCourant % 2 + 1;
                ihm.afficher(partie.toString());

            }
        }

    }

    public void create(String nomJoueur, String couleur) {
        joueurs[nbJoueurs]=(new Joueur(nomJoueur,couleur));
        nbJoueurs++;
    }

    public boolean syntaxCheck(String coup) {
        //vérifie la syntaxe du coup entré par le joueur
        if (coup.isEmpty() || coup.length() > 3 || coup.length() == 2) {
            //mauvaise longueur
            ihm.afficher(coup);
            ihm.afficher("Mauvaise syntaxe (longueur)");
            return false;
        }
        else if(coup.length() == 1) {
            if (coup.charAt(0) != 'P') {
                //syntaxe du passage de tour incorrecte
                ihm.afficher("Mauvaise syntaxe (!P)");
                return false;
            }
            //passage de tour correct syntaxiquement
            return true;
        }
        char[] charCoup = coup.toCharArray();
        if (!(Character.isDigit(charCoup[0]) && charCoup[1] == ' ' && Character.isAlphabetic(charCoup[2]))) {
                //coup de bonne longueur mais mal écrit
                ihm.afficher("Mauvaise syntaxe (syn)");
                return false;
        }
        return true;
    }

    public Joueur getJoueur(int numero){
        return joueurs[numero-1];
    }
}