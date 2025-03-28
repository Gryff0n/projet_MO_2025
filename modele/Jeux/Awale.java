package modele.Jeux;

import modele.Joueur;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Awale implements Jeux {
    private int[] grenier = new int[2];
    private int[][] cases = new int[2][6];

    public void initialiser() {
        for (int i = 0; i < 2; i++) {
            Arrays.fill(cases[i], 4);
        }
        Arrays.fill(grenier, 0);
    }

    public void jouerCoup(int l, int c, int numero){}

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

    public int[] getPred(int[] coup) {
        if (coup[0] == 1) {
            if (coup[1]== 0)
                return new int[]{0,0};
            else
                return new int[]{0,coup[1]+1};
        }
        else {
            if(coup[1]==5)
                return new int[]{1,5};
            else
                return new int[]{1,coup[1]-1};
        }
    }

    public boolean syntaxCheck(String coup) {
        //vérifie la syntaxe du coup entré par le joueur
        if (coup.length() != 3) {
            //mauvaise longueur
            throw new IllegalArgumentException("Mauvaise Syntaxe : Longueur");
        }
        char[] charCoup = coup.toCharArray();
        if (!(Character.isDigit(charCoup[0]) && charCoup[1] == ' ' && Character.isAlphabetic(charCoup[2]))) {
            //coup de bonne longueur mais mal écrit
            throw new IllegalArgumentException("Mauvaise Syntaxe : syn");
        }
        return true;
    }

    public boolean coupValide(int l, int c, int numero){
        // Si en dehors du plateau
        if (l < 0 || l > 2 || c < 0 || c > 6) {
            return false;
        }
        return (numero == 1 && l == 1 || numero == 2 && l == 2);
    }

    public int checkVictoire() {
        if (Arrays.equals(cases[0], new int[]{0, 0, 0, 0, 0, 0})) {
            return 1;
        }
        if (Arrays.equals(cases[1], new int[]{0, 0, 0, 0, 0, 0})) {
            return 2;
        }
        return 0;
    }

    public boolean coupImpossible(int numero){
        if (Arrays.equals(cases[0], new int[]{0, 0, 0, 0, 0, 0}) || Arrays.equals(cases[1], new int[]{0, 0, 0, 0, 0, 0})) {
            return true;
        };
        return false;
    }

    public String afficherScore() {
        return "grenier J1 : "+grenier[0]+"\ngrenier J2 : "+grenier[1]+"\n";
    }
    public String getMessageDemanderCoup(int numero) {
        String ligne = (numero == 1) ? "sur la ligne du bas" : "sur la ligne du haut";
        return ligne + " (ex: '1 A')";
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
        return sb.toString();
    }
}
