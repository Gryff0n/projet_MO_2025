package modele.IA;

import modele.Partie;

import java.util.*;

public class MiniMax implements StrategiesIA {

    /**
     * application de la stratégie MiniMax de l'IA qui choisi un coup en fonction de l'apport de point le plus haut possible
     * pour l'IA et le plus bas possible pour le Joueur adverse, en fonction d'une pondération donné pour chaque position sur le
     * plateau de jeu.
     * @param partie la partie en son état actuel
     * @return le coup choisi par l'IA sous la forme de coordonnées {i,j}
     */
    public int[] appliquerStrategie(Partie partie) {
        int maximum = -10000;
        int[] meilleurCoup = {-1};
        List<int[]> coupsPotentiels = partie.coupsPotentiels(2);
        for(int[] coup : coupsPotentiels){
            int min =10000;
            Partie p = partie.copier();
            p.jouerCoup(coup[0],coup[1],2);
            List<int[]> coupPotentiels2 = p.coupsPotentiels(1);
            for (int[] coup2 : coupPotentiels2) {
                Partie p2 = p.copier();
                p2.jouerCoup(coup2[0],coup2[1],1);
                int score =p2.getValeur(2)-p2.getValeur(1);
                if(score < min){
                    min = score;
                }
            }
            if (min > maximum) {
                maximum = min;
                meilleurCoup = coup;
            }
        }
        return meilleurCoup;
    }
}
