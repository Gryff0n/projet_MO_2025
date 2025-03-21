package modele.IA;

import modele.Partie;

import java.util.*;

public class MiniMax implements StrategiesIA {

    public int[] appliquerStrategie(Partie partie) {
        Map<Integer,int[]> meilleursCoups = new HashMap<>();
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
            meilleursCoups.put(min,coup);
        }
        int maximum = -10000;
        int[] meilleurCoup = {-1};
        for (Integer key : meilleursCoups.keySet()) {;
            int[] coup = meilleursCoups.get(key);
            if(key > maximum){
                maximum = key;
                meilleurCoup = coup;
            }
        }
        return meilleurCoup;
    }
}
