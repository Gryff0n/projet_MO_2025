package controleur;

import modele.Joueur;
import modele.Partie;
import vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Controleur {
    private Ihm ihm;
    private Joueur[] joueurs = new Joueur[2];
    int nbJoueurs = 0;
    int joueurCourant = 1;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    /**
     * Méthode principale du controlleur, gère le démarrage/redémarrage d'une partie, son déroulement général
     * ainsi que les conditions de fin de partie et de fin de session.
     */
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
                boolean syntaxeValide;
                while (!coupValide) {
                    coup=ihm.demanderCoup(joueurs[joueurCourant-1].getNom() + " ("+ joueurs[joueurCourant-1].getCouleur()+")").trim();
                    syntaxeValide=syntaxCheck(coup);
                    if (syntaxeValide) {
                        if (coup.charAt(0) == 'P'){
                            coupValide = partie.coupImpossible(joueurCourant);
                            if(!coupValide){
                                ihm.afficher("Il vous reste des coups a jouer !");
                            }
                        }
                        else {
                            int l = Integer.parseInt(coup.substring(0,1))-1;
                            int c = Character.getNumericValue(coup.charAt(2))-10;
                            coupValide = partie.coupValide(l,c, joueurCourant);
                            if (coupValide) {
                                partie.jouerCoup(l,c,joueurCourant);
                            }
                            else {
                                ihm.afficher("Coup illégal !");
                            }
                        }
                    }
                }
                joueurCourant = joueurCourant % 2 + 1;
                ihm.afficher(partie.toString());
                if((partie.coupImpossible(1) && partie.coupImpossible(2)) || partie.getNb_jetons_plateau()[0]+partie.getNb_jetons_plateau()[1]==64 ){
                    partieTerminee=true;
                }

            }
            ihm.afficher("PARTIE TERMINEE");
            ihm.afficher("nombres de jetons noirs : "+partie.getNb_jetons_plateau()[0] );
            ihm.afficher("nombres de jetons blancs : "+partie.getNb_jetons_plateau()[1] );
            if (partie.getNb_jetons_plateau()[0]>partie.getNb_jetons_plateau()[1]) {
                ihm.afficher("--Joueur "+joueurs[0].getNom()+" a gagné !!--");
                joueurs[0].Victoire();
            }
            else if (partie.getNb_jetons_plateau()[0]<partie.getNb_jetons_plateau()[1]) {
                ihm.afficher("--Joueur "+joueurs[1].getNom()+" a gagné !!--");
                joueurs[1].Victoire();
            }
            else ihm.afficher("--EX AEQUO--");
            String redem = "";
            while (!Objects.equals(redem, "N") && !Objects.equals(redem, "Y")) {
                redem = ihm.redemPartie();
            }
            if (redem.equals("N")) {
                fini = true;
            }
        }
        ihm.afficher("FIN DE LA SESSION");
        ihm.afficher("SCORES FINAUX");
        ihm.afficher("Joueur 1 alias "+joueurs[0].getNom()+" : "+joueurs[0].getScore());
        ihm.afficher("Joueur 2 alias "+joueurs[1].getNom()+" : "+joueurs[1].getScore());
        if (joueurs[0].getScore()>joueurs[1].getScore()) {
            ihm.afficher("------Vainqueur de la session : " + joueurs[0].getNom() + " !!------");
        }
        else if (joueurs[0].getScore()<joueurs[1].getScore()) {
            ihm.afficher("------Vainqueur de la session : " + joueurs[1].getNom() + " !!------");
        }
        else ihm.afficher("------Les joueurs sont EX AEQUO !!------");
    }

    /**
     * méthode de création d'un joueur pour une nouvelle session.
     * @param nomJoueur le nom du joueur créé
     * @param couleur la couleur de ses pions
     */
    public void create(String nomJoueur, String couleur) {
        joueurs[nbJoueurs]=(new Joueur(nomJoueur,couleur));
        nbJoueurs++;
    }

    /**
     * méthode de vérification de la synatxe du coup rentré par le joueur actuel.
     * @param coup le coup entré par le joueur
     * @return true si le coup est valide syntaxiquement, false sinon
     */
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

    public int[] coupAléatoire(Partie partie) {
        List<int[]> coupPotentiels = new ArrayList<>();
        for (int i = 0; i < partie.getTaille(); i++) {
            for (int j = 0; j < partie.getTaille(); j++) {
                if (partie.coupValide(i,j, 2)){
                    coupPotentiels.add(new int[]{i,j});
                }
            }
        }
        int x = new Random().nextInt(coupPotentiels.size());
        return coupPotentiels.get(x);
    }

}