package modele.IA;

import modele.Partie;

import java.util.List;
import java.util.Random;

public class Aleatoire implements StrategiesIA {

    public int[] appliquerStrategie(Partie partie) {
        List<int[]> coupsPotentiels;
        coupsPotentiels=partie.coupsPotentiels(2);
        if (coupsPotentiels.isEmpty()) {
            return new int[]{-1};
        }
        int x = new Random().nextInt(coupsPotentiels.size());
        return coupsPotentiels.get(x);
    }
}
