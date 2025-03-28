package modele.Jeux;

import modele.Joueur;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Awale implements Jeux {
    private int[] grenier = new int[2];
    private int[] cases = new int[12];

    public void initialiser() {
        Arrays.fill(cases, 0);
        Arrays.fill(grenier, 0);
    }

    public void jouerCoup(int l, int c, int numero){}

    public boolean syntaxCheck(String coup) {
        return false;
    }

    public boolean coupImpossible(int numero) {
        return false;
    }

    public boolean coupValide(int l, int c, int numero) {
        return false;
    }

    public int checkVictoire() {
        return 0;
    }

    public String afficherScore() {
        return "";
    }
    public String getMessageDemanderCoup() {
        return "";
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("grenier J2     ");
        for (int i = 0; i < 6; i++) {
            sb.append(cases[i]);
            if (i < 5) sb.append(" | ");  // Ajouter des séparateurs entre les cases
        }
        sb.append("     grenier J1");
        sb.append("\n    ").append(grenier[1]);
        if (grenier[1]>9) {
            sb.append("         ");
        }
        else {
            sb.append("          ");
        }
        for (int i = 6; i < 12; i++) {
            sb.append(cases[i]);
            if (i < 11) sb.append(" | ");  // Ajouter des séparateurs entre les cases
        }
        sb.append("         ").append(grenier[0]);
        return sb.toString();
    }
}
