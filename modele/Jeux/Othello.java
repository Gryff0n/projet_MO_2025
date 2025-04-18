package modele.Jeux;

import java.util.*;

public class Othello implements Jeux{
    private final int taille = 8;
    private int[][] tableau = new int[taille][taille];
    private int nb_jetons_blancs =2;
    private int nb_jetons_noirs = 2;
    private int[][] tableauValeur = new int[taille][taille];

    /**
     * Méthode d'initialisation d'une partie de la session. Créer la disposition initiale du plateau d'othello.
     */
    public void initialiser(){
        for(int i = 0; i < taille; i++){
            for(int j = 0; j < taille; j++){
                if ((i==(taille-1)/2 && j==(taille-1)/2)||(i==(taille-1)/2+1 && j==(taille-1)/2+1)){
                    tableau[i][j]=2;
                }
                else if ((i==(taille-1)/2+1 && j==(taille-1)/2)||(i==(taille-1)/2 && j==(taille-1)/2+1)){
                    tableau[i][j]=1;
                }
                else {
                    tableau[i][j]=0;
                }
                if( (i == 0 || i == taille - 1) && (j == 0 || j == taille - 1) ) {

                    tableauValeur[i][j]=11;
                }
                else if(i == 0 || i == taille - 1 || j == 0 || j == taille - 1){
                    tableauValeur[i][j]=6;
                }
                else {
                    tableauValeur[i][j]=1;
                }
            }
        }
        nb_jetons_blancs =2;
        nb_jetons_noirs = 2;
    }

    /**
     * créé un clone a l'identique du plateau actuel
     * @return le clone de la Partie
     */
    public Othello copier(){
        Othello othelloClone = new Othello();
        int[][] tab = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            System.arraycopy(tableau[i], 0, tab[i], 0, taille);
        }
        othelloClone.setTableau(tab);
        othelloClone.setNb_jetons_blancs(nb_jetons_blancs);
        othelloClone.setNb_jetons_noirs(nb_jetons_noirs);
        int[][] tabV= new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            System.arraycopy(tableauValeur[i], 0, tabV[i], 0, taille);
        }
        othelloClone.setTableauValeur(tabV);
        return othelloClone;
    }


    /**
     * Verifie si aucun coup possible autour d'un pion adverse.
     * @param numero le numéro du joueur dont on vérifie le coup.
     * @return true si le joueur indiqué n'a pas de coup possible, false sinon.
     */
    public boolean coupImpossible(int numero){
        boolean b = true;
        int i = 0;
        while (b && i < taille){
            int j = 0;
            while(b && j < taille){
                if (tableau[i][j] == 0){
                    b = !verifRetourneUnPion(i,j,numero);
                }
                j++;
            }
            i++;
        }
        return b;
    }

    /**
     * Vérifie qu'un coup indiqué est légal au sein du jeu.
     * @param l le numéro de ligne du coup
     * @param c le numéro de colonne du coup
     * @param numero le numéro du joueur jouant le coup
     * @return true si le coup est légal, false sinon
     */
    public boolean coupValide(int l, int c, int numero){
        // Si en dehors du plateau
        if (l < 0 || l > taille-1 || c < 0 || c > taille-1) {
            return false;
        }
        else if (tableau[l][c] == 0){
            //Vérifie si le coup permet de retourner au moins un pion
            return verifRetourneUnPion(l,c,numero);
        }
        else return false;
    }

    /**
     * Vérifie si depuis une case indiquée, un coup permettrait de retourner des pions adverses.
     * @param l la ligne de la case
     * @param c la colonne de la case
     * @param numero le numéro du joueur actuel
     * @return true si des pions adverses peuvent être retournés, false sinon.
     */
    public boolean verifRetourneUnPion(int l, int c, int numero) {
        // Numéro de l'adversaire
        int adversaire = (numero == 1) ? 2 : 1;
        boolean peutRetourner = false;
        // Parcourir les 8 directions autour du point (l, c)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Ignorer la direction nulle (0, 0)
                if ((dx == 0 && dy == 0) && tableau[l+dx][c+dy] != adversaire) {
                    continue;
                }
                // Vérifier si on peut retourner dans cette direction
                int x = l + dx;
                int y = c + dy;
                boolean trouveAdversaire = false;
                // Explorer cette direction jusqu'à rencontrer un bord ou un pion
                while (x >= 0 && x < taille && y >= 0 && y < taille) {
                    // Si la case est vide ou contient un pion du joueur initial, arrêter
                    if (tableau[x][y] == 0) {
                        break;
                    } else if (tableau[x][y] == numero) {
                        // Si un pion du joueur initial est trouvé après un ou plusieurs adversaires
                        if (trouveAdversaire) {
                            peutRetourner = true;
                        }
                        break;
                    } else if (tableau[x][y] == adversaire) {
                        // Marquer qu'au moins un pion adverse est trouvé dans cette direction
                        trouveAdversaire = true;
                    }
                    // Avancer dans la direction
                    x += dx;
                    y += dy;
                }
                if (peutRetourner) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Joue un coup sur le plateau, plaçant ainsi un nouveau pion a l'endroit indiqué,
     * retournant les pions adverses concernés et mettant a jour les nombres de pions de chaque joueurs.
     * @param l ligne du coup
     * @param c colonne du coup
     * @param numero numero du joueur actuel
     */
    public void jouerCoup(int l, int c, int numero){
        if(numero==1) nb_jetons_noirs++;
        else nb_jetons_blancs++;
        tableau[l][c]=numero;
        int adversaire = (numero == 1) ? 2 : 1;
        List<int[]> pionsPotentiels = new ArrayList<>();
        // Parcourir les 8 directions autour du point (l, c)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Ignorer la direction nulle (0, 0)
                if ((dx == 0 && dy == 0) && tableau[l+dx][c+dy] != adversaire) {
                    continue;
                }
                // Vérifier si on peut retourner dans cette direction
                int x = l + dx;
                int y = c + dy;
                boolean trouveAdversaire = false;
                pionsPotentiels.clear();

                // Explorer cette direction jusqu'à rencontrer un bord ou un pion
                while (x >= 0 && x < taille && y >= 0 && y < taille) {
                    // Si la case est vide ou contient un pion du joueur initial, arrêter
                    if (tableau[x][y] == 0) {
                        break;
                    } else if (tableau[x][y] == numero) {
                        // Si un pion du joueur initial est trouvé après un ou plusieurs adversaires
                        if (trouveAdversaire) {
                            for (int[] pion : pionsPotentiels) {
                                retourPion(pion[0],pion[1],numero);
                            }
                        }
                        break;
                    } else if (tableau[x][y] == adversaire) {
                        // Marquer qu'au moins un pion adverse est trouvé dans cette direction
                        trouveAdversaire = true;
                        pionsPotentiels.add(new int[]{x,y});
                    }
                    // Avancer dans la direction
                    x += dx;
                    y += dy;
                }
            }
        }
    }

    /**
     * Méthode qui change la couleur d'un pion déjà existant sur le plateau, et met a jour les nombres de pions des joueurs.
     * @param l ligne du pion
     * @param c colonne du pion
     * @param numero numéro du joueur ayant retourné le pion
     */
    public void retourPion(int l, int c, int numero){
        tableau[l][c]=numero;
        if(numero==1) {
            nb_jetons_noirs++;
            nb_jetons_blancs--;
        }
        else {
            nb_jetons_blancs++;
            nb_jetons_noirs--;
        }
    }

    /**
     * trouve les coordonnées de coups potentiels pour un joueur donné au tour actuel
     * @param numero le numero du joueur concerné
     * @return la liste des coordonnées des coups possibles sur le plateau
     */
    public List<int[]> coupsPotentiels(int numero){
        List<int[]> coupPotentiels = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (coupValide(i,j, numero)){
                    coupPotentiels.add(new int[]{i,j});
                }
            }
        }
        return coupPotentiels;
    }

    /**
     * renvoi la valeur des pions d'un joueur donné en l'état actuel du plateau
     * @param nbJoueur le numéro du joueur concerné
     * @return la valeur de ses pions
     */
    public int getValeur(int nbJoueur) {
        int nb_noirs = 0;
        int nb_blancs = 0;
        if (coupImpossible(1)&& coupImpossible(2)) {
            if ((nb_jetons_noirs > nb_jetons_blancs && nbJoueur == 1) || (nb_jetons_blancs > nb_jetons_noirs && nbJoueur == 2)) {
                return 1000;
            }
            if ((nb_jetons_noirs > nb_jetons_blancs && nbJoueur == 2) || (nb_jetons_blancs > nb_jetons_noirs && nbJoueur == 1)) {
                return -1000;
            }

        }
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if(tableau[i][j] == 1){
                    nb_noirs+=tableauValeur[i][j];
                }
                else if(tableau[i][j] == 2){
                    nb_blancs+=tableauValeur[i][j];
                }
            }
        }
        if(nbJoueur==1) {
            return nb_noirs;
        }
        else {
            return nb_blancs;
        }
    }


    public int checkVictoire() {
        if (nb_jetons_noirs > nb_jetons_blancs) {
            return 1;
        }
        else if (nb_jetons_noirs < nb_jetons_blancs) {
            return 2;
        }
        else return 0;
    }

    public String afficherScore() {
        return "nombres de jetons noirs : "+ nb_jetons_noirs +"\nnombres de jetons blancs :"+ nb_jetons_blancs;
    }

    public void setTableau(int[][] tableau) {
        this.tableau = tableau;
    }

    public void setNb_jetons_blancs(int nb_jetons_blancs) {
        this.nb_jetons_blancs = nb_jetons_blancs;
    }

    public void setNb_jetons_noirs(int nb_jetons_noirs) {
        this.nb_jetons_noirs = nb_jetons_noirs;
    }

    public void setTableauValeur(int[][] tableauValeur) {
        this.tableauValeur = tableauValeur;
    }

    public String getMessageDemanderCoup(int numero) {
        String couleur = (numero == 1) ? "avec les jetons noir" : "avec les jetons blanc";
        return couleur + " (ex: '1 A' ou 'P' pour passer son tour si aucun coup possible)";
    }

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder("  ");
        for (int i = 0; i < taille; i++){
            s.append("   ").append((char) ('A' + i));
        }
        s.append("\n");
        for (int i = 0; i < taille; i++) {
            s.append(" ").append(i + 1).append(" ");
            for (int j = 0; j < taille; j++) {
                if (tableau[i][j] == 0) {
                    s.append(" \uD83D\uDFE9 ");
                }
                else if (tableau[i][j] == 2) {
                    s.append(" \u26AA ");
                }
                else if (tableau[i][j] == 1){
                    s.append(" \u26AB ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }



}
