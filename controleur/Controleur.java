package controleur;

import modele.IA.Aleatoire;
import modele.IA.MiniMax;
import modele.IA.StrategiesIA;
import modele.Jeux.Awale;
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
        //Choix Jeu
        Jeux jeux = new Othello();
        int game = 0;
        while (game !=1 && game !=2)
            game = ihm.demanderJeu();
        if (game==2) {
            jeux = new Awale();
        }
        //Création Joueur 1
        create(ihm.demanderNomJoueur(nbJoueurs + 1));
        //Variables IA
        String demandia="";
        boolean IA = false;
        StrategiesIA strat = null;
        Integer demandStrat = 0;
        //Création IA ou joueur 2 si Othello
        if(game==1) {
            //Demande IA ou J2
            while (!(demandia.equals("N"))&&!(demandia.equals("Y")))
                demandia=ihm.demanderIA();
            //pas IA, donc J2
            if (demandia.equals("N")) {
                create(ihm.demanderNomJoueur(nbJoueurs + 1));
            }
            //IA donc choix stratégie
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
        }
        //J2 car Awale
        else {
            create(ihm.demanderNomJoueur(nbJoueurs + 1));
        }
        //déroulement partie
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
                        coup=ihm.demanderCoup(joueurs[joueurCourant-1].getNom(), jeux.getMessageDemanderCoup(joueurCourant)).trim();
                        try {
                            syntaxeValide= syntaxCheck(coup);
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
     * méthode de vérification de la synatxe du coup rentré par le joueur actuel.
     * @param coup le coup entré par le joueur
     * @return true si le coup est valide syntaxiquement, false sinon
     */
    public boolean syntaxCheck(String coup) {
        //vérifie la syntaxe du coup entré par le joueur
        if (coup.isEmpty() || coup.length() > 3 || coup.length() == 2) {
            //mauvaise longueur
            throw new IllegalArgumentException("Mauvaise Syntaxe : Longueur");
        }
        else if(coup.length() == 1) {
            if (coup.charAt(0) != 'P') {
                //syntaxe du passage de tour incorrecte
                throw new IllegalArgumentException("Mauvaise Syntaxe : !P");
            }
            //passage de tour correct syntaxiquement
            return true;
        }
        char[] charCoup = coup.toCharArray();
        if (!(Character.isDigit(charCoup[0]) && charCoup[1] == ' ' && Character.isAlphabetic(charCoup[2]))) {
            //coup de bonne longueur mais mal écrit
            throw new IllegalArgumentException("Mauvaise Syntaxe : syn");
        }
        return true;
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