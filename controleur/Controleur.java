package controleur;

import modele.IA.Aleatoire;
import modele.IA.MiniMax;
import modele.IA.StrategiesIA;
import modele.Jeux.Jeux;
import modele.Joueur;
import modele.Jeux.Othello;
import vue.Ihm;

import java.util.*;

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
        Jeux jeux = new Othello();
        create(ihm.demanderNomJoueur(nbJoueurs + 1));
        String demandia="";
        boolean IA = false;
        StrategiesIA strat = null;
        Integer demandStrat = 0;
        while (!(demandia.equals("N"))&&!(demandia.equals("Y")))
            demandia=ihm.demanderIA();
        if (demandia.equals("N")) {
            create(ihm.demanderNomJoueur(nbJoueurs + 1));
        }
        else {
            create("IA");
            IA = true;
            while (demandStrat !=1 && demandStrat !=2)
                demandStrat = ihm.demanderStratIA();
            if (demandStrat==1) {
                strat = new Aleatoire();
            }
            else {
                strat = new MiniMax();
            }
        }
        boolean fini = false;
        while (!fini) {
            jeux.initialiser();
            ihm.afficher(jeux.toString());
            boolean partieTerminee = false;
            String coup;
            joueurCourant = 1;
            while (!partieTerminee){
                if (IA && joueurCourant==2) {
                    int[] c = strat.appliquerStrategie((Othello) jeux);
                    if (c[0]==-1) {
                        ihm.afficher("L'ordinateur a passé son tour.");
                    }
                    else {
                        jeux.jouerCoup(c[0],c[1], 2);
                        ihm.afficher("L'ordinateur a joué " + coupIA(c));
                    }
                }
                else {
                    boolean coupValide = false;
                    boolean syntaxeValide;
                    while (!coupValide) {
                        String couleur = (joueurCourant == 1) ? "noir" : "blanc";
                        coup=ihm.demanderCoup(joueurs[joueurCourant-1].getNom() + " ("+couleur+")", jeux.getMessageDemanderCoup()).trim();
                        try {
                            syntaxeValide= jeux.syntaxCheck(coup);
                        }
                        catch (IllegalArgumentException e) {
                            syntaxeValide=false;
                            ihm.afficher(e.getMessage());
                        }
                        if (syntaxeValide) {
                            if (coup.charAt(0) == 'P'){
                                coupValide = jeux.coupImpossible(joueurCourant);
                                if(!coupValide){
                                    ihm.afficher("Il vous reste des coups a jouer !");
                                }
                            }
                            else {
                                int l = Integer.parseInt(coup.substring(0,1))-1;
                                int c = Character.getNumericValue(coup.charAt(2))-10;
                                coupValide = jeux.coupValide(l,c, joueurCourant);
                                if (coupValide) {
                                    jeux.jouerCoup(l,c,joueurCourant);
                                }
                                else {
                                    ihm.afficher("Coup illégal !");
                                }
                            }
                        }
                    }
                }
                joueurCourant = joueurCourant % 2 + 1;
                ihm.afficher(jeux.toString());
                if((jeux.coupImpossible(1) && jeux.coupImpossible(2)) ){
                    partieTerminee=true;
                }

            }
            int gagnant = jeux.checkVictoire();
            ihm.afficher("PARTIE TERMINEE");
            ihm.afficher(jeux.afficherScore());
            if (gagnant==1) {
                ihm.afficher("--Joueur "+joueurs[0].getNom()+" a gagné !!--");
                joueurs[0].Victoire();
            }
            else if (gagnant==2) {
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
     */
    public void create(String nomJoueur) {
        joueurs[nbJoueurs]=(new Joueur(nomJoueur));
        nbJoueurs++;
    }

    /**
     * retransforme un coup joué par l'IA dans la syntaxe ligne : chiffre et colonne : lettre
     * @param coup le coup de l'IA sous forme de coordonées {i,j}
     * @return un string du coup de l'IA sous forme "Chiffre Lettre"
     */
    public String coupIA(int[] coup) {
        String ligne = String.valueOf(coup[0]+1);
        String colonne = String.valueOf((char) (coup[1]+65));
        return ligne + " " + colonne;
    }




}