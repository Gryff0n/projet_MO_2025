package modele.Jeux;

import java.util.Arrays;

public class Awale implements Jeux {
    private int[] grenier = new int[2];
    private int[][] cases = new int[2][6];
    private boolean fini = false;

    public void initialiser() {
         /*
         pour effectuer des tests de fin de partie
         cases[0]= new int[] {0,0,0,1,0,0};
         cases[1]= new int[] {0,0,0,0,0,2};
         */
        for (int i = 0; i < 2; i++) {
             Arrays.fill(cases[i], 4);
         }

         Arrays.fill(grenier, 0);
    }

    /**
     * Méthode permettant d'actualiser le plateau avec le coup correspondant
     * @param l la ligne du coup a jouer
     * @param c la colonne du coup a jouer
     * @param numero le numero du joueur courant
     */
    public void jouerCoup(int l, int c, int numero){
        //init variables
        int recup = 0;
        int nbGrains = cases[l][c];
        int[] caseDepart = new int[] {l,c};
        int[] caseCourante = caseDepart;
        cases[l][c] = 0;
        //répartition des grains dans les cases suivantes
        for (int i = 0; i < nbGrains; i++) {
            if(Arrays.equals(getSucc(caseCourante), caseDepart)){
                //si on a bouclé le plateau on saute la case de départ
                caseCourante=getSucc(getSucc(caseCourante));
            }
            else {
                caseCourante = getSucc(caseCourante);
            }
            //on ajoute une graine dans chaque case
            cases[caseCourante[0]][caseCourante[1]]++;
        }
        //SI ET UNIQUEMENT SI la dernière graine tombe sur une case ennemie
        if (caseCourante[0]==1 && numero==2 || caseCourante[0]==0 && numero==1)
            //si la graine contient 2 ou 3 graine après le coup, alors on commence a rembobiner
            while (cases[caseCourante[0]][caseCourante[1]] == 2 || cases[caseCourante[0]][caseCourante[1]] == 3) {
                recup += cases[caseCourante[0]][caseCourante[1]];
                cases[caseCourante[0]][caseCourante[1]] = 0;
                caseCourante = getPred(caseCourante);
            }
        //on ajoute au grenier du joueur correspondant le total de graines récuperé lors du tour
        grenier[numero-1]+=recup;
        //gestion de fin de partie si le coup cause la ligne du plateau du joueur courant de devenir vide
        if (Arrays.equals(cases[0], new int[]{0, 0, 0, 0, 0, 0}) || Arrays.equals(cases[1], new int[]{0, 0, 0, 0, 0, 0})) {
            fini = true;
            for(int i=0;i<cases.length;i++){
                for(int j=0; j<cases[i].length;j++){
                    if (Arrays.equals(cases[0], new int[]{0, 0, 0, 0, 0, 0}))
                        grenier[0]+=cases[i][j];
                    else
                        grenier[1]+=cases[i][j];
                    cases[i][j]=0;
                }
            }
        }
    }

    /**
     * Méthode permettant de créer un objet éphèmere identique a la partie en son état actuel
     * @return la copie de la partie d'Awale courante
     */
    public Awale copier() {
        Awale awale_copy = new Awale();
        for (int i = 0; i < cases.length; i++) {
            System.arraycopy(cases[i], 0, awale_copy.cases[i], 0, cases[i].length);
        }
        System.arraycopy(grenier, 0, awale_copy.grenier, 0, 2);
        return awale_copy;
    }

    /**
     * Méthode très utile permettant de savoir quel est la case suivante d'une case donnée sur
     * le plateau de Awale
     * @param coup les coordonées de la case actuelle
     * @return les coordonnées de la case suivante
     */
    public int[] getSucc(int[] coup) {
        if (coup[0] == 0) {
            if (coup[1]== 0)
                return new int[]{1,0};
            else
                return new int[]{0,coup[1]-1};
        }
        else {
            if(coup[1]==5)
                return new int[]{0,5};
            else
                return new int[]{1,coup[1]+1};
        }
    }

    /**
     * Méthode très utile permettant de savoir quel est la case précédente d'une case donnée sur le plateau
     * de Awale
     * @param coup les coordonées de la case actuelle
     * @return les coordonnées de la case précédente
     */
    public int[] getPred(int[] coup) {
        if (coup[0] == 1) {
            if (coup[1]== 0)
                return new int[]{0,0};
            else
                return new int[]{1,coup[1]-1};
        }
        else {
            if(coup[1]==5)
                return new int[]{1,5};
            else
                return new int[]{0,coup[1]+1};
        }
    }



    /**
     * Méthode vérifiant qu'un coup donné est légal, dans ce jeu cela signifie que les coordonnées du coup sont
     * sur la ligne du joueur correspondant et que ce coup ne va pas rafler toutes les graines de l'adversaire
     * @param l la ligne du coup a vérifier
     * @param c la colonne du coup a vérifier
     * @param numero le numéro du joueur qui a entré le coup
     * @return true si le coup est légal, false sinon
     */
    public boolean coupValide(int l, int c, int numero){
        // Si en dehors du plateau
        if (l < 0 || l > 2 || c < 0 || c > 6) {
            return false;
        }
        if (numero == 1 && l == 0 || numero == 2 && l == 1) {
            return false;
        }
        Awale prediction=copier();
        prediction.jouerCoup(l, c, numero);
        return !Arrays.equals(prediction.cases[numero - 1], new int[]{0, 0, 0, 0, 0, 0}) || prediction.fini ;
    }

    /**
     * Vérifie le gagnant d'une partie terminée.
     * @return le numéro du joueur gagnant, 0 si égalité
     */
    public int checkVictoire() {
        if (grenier[0]>grenier[1])
            return 1;
        if (grenier[1]>grenier[0])
            return 2;
        return 0;
    }

    /**
     * vérifie si il reste des coups possibles pour un joueur donné
     * @param numero le numéro du joueur concerné
     * @return true si il n'y a plus de coup possible pour le joueur, false sinon
     */
    public boolean coupImpossible(int numero){
        if (Arrays.equals(cases[0], new int[]{0, 0, 0, 0, 0, 0}) && Arrays.equals(cases[1], new int[]{0, 0, 0, 0, 0, 0})) {
            return true;
        }
        for(int i = 0 ; i < cases[numero%2].length;i++){
            if (cases[numero%2][i] != 0)
                if (coupValide(numero%2,i,numero))
                    return false;
        }
        return true;
    }

    public String afficherScore() {
        return ("grenier J1 : "+grenier[0]+"\ngrenier J2 : "+grenier[1]+"\n");
    }


    public String getMessageDemanderCoup(int numero) {
        String ligne = (numero == 1) ? "sur la ligne du bas" : "sur la ligne du haut";
        return ligne + " (ex: '1 A' ou P pour passer son tour)";
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("\n   A   B   C   D   E   F\n1  ");
        for (int i = 0; i < 6; i++) {
            sb.append(cases[0][i]);
            if (i < 5) sb.append(" | ");  // Ajouter des séparateurs entre les cases
        }
        sb.append("\n2  ");
        for (int i = 0; i < 6; i++) {
            sb.append(cases[1][i]);
            if (i < 5) sb.append(" | ");  // Ajouter des séparateurs entre les cases
        }
        sb.append("\n");
        sb.append("grenier J1 : ").append(grenier[0]).append("\ngrenier J2 : ").append(grenier[1]).append("\n");
        return sb.toString();
    }
}
