package modele.IA;

import modele.Partie;

import java.util.*;

public class MiniMax implements StrategiesIA {

    public int[] appliquerStrategie(Partie partie) {
        List<int[]> coupsPotentiels = partie.coupsPotentiels(2);
        Iterator<int[]> iterator = coupsPotentiels.iterator();
        Map<Integer,int[]> meilleursCoups = new HashMap<>();
        while(iterator.hasNext()){
            int max =0 ;
            int[] coup = iterator.next();
            Partie p = partie.copie();
            List<int[]> coupJoue;
            if (coup == null){
                return new int[]{-1};
            }
            p.jouerCoup(coup[0],coup[1],2);
            List<int[]> coupPotentiels2 = p.coupsPotentiels(1);
            Iterator<int[]> iterator2 = coupPotentiels2.iterator();
            while(iterator2.hasNext()){
                int min = 10000;
                int[] coup2 = iterator2.next();
                Partie p2 = p.copie();
                if (coup2 != null){
                    p2.jouerCoup(coup2[0],coup2[1],1);
                }
                List<int[]> coupPotentiels3 = p2.coupsPotentiels(2);
                Iterator<int[]> iterator3 = coupPotentiels3.iterator();
                while(iterator3.hasNext()){
                    int[] coup3 = iterator3.next();
                    Partie p3 = p.copie();
                    if (coup != null){
                        p3.jouerCoup(coup3[0],coup3[1],2);
                    }
                    int score =p3.getValeur(2)-p3.getValeur(1);
                    if(score < min){
                        min = score;
                    }
                }
                if(min > max){
                    max = min;
                }
            }
            meilleursCoups.put(max,coup);
        }
        int maximum = -10000;
        int[] meilleurCoup = null;
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
