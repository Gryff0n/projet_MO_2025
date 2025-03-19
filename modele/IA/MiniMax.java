package modele.IA;

import modele.Partie;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MiniMax implements StrategiesIA {

    public int[] appliquerStrategie(Partie partie) {
        List<int[]> coupsPotentiels = partie.coupsPotentiels();
        Iterator<int[]> iterator = coupsPotentiels.iterator();
        Map<int[],Integer> meilleursCoups = new HashMap<>();
        while(iterator.hasNext()){
            int max =0 ;
            int[] coup = iterator.next();
            Partie p = partie.copie();
            List<int[]> coupJoue;
            p.jouerCoup(coup[0],coup[1],2);
            List<int[]> coupPotentiels2 = p.coupsPotentiels();
            Iterator<int[]> iterator2 = coupPotentiels2.iterator();
            while(iterator2.hasNext()){
                int min = 10000;
                int[] coup2 = iterator2.next();
                Partie p2 = p.copie();
                p2.jouerCoup(coup2[0],coup2[1],1);
                List<int[]> coupPotentiels3 = p2.coupsPotentiels();
                Iterator<int[]> iterator3 = coupPotentiels3.iterator();
                while(iterator3.hasNext()){
                    int[] coup3 = iterator3.next();
                    Partie p3 = p.copie();
                    p3.jouerCoup(coup3[0],coup3[1],1);
                    int score =p3.getValeur(2)-getValeur(1);
                    if(score < min){
                        min = score;
                    }
                }
                if(min > max){
                    max = min;
                }
            }
            meilleursCoups.put(coup,max);
        }
        int maximum = 0;
        int[] meilleurCoup = null;
        Iterator itMap = meilleursCoups.entrySet().iterator();
        while(itMap.hasNext()){
            int[] courant = (int[]) itMap.next();
            int val = meilleursCoups.get(courant);
            if(val > maximum){
                maximum = val;
                meilleurCoup = courant;
            }
        }
        return meilleurCoup;
    }
}
