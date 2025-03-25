package modele.IA;

import modele.Partie;

import java.util.List;
import java.util.Random;

public class Aleatoire implements StrategiesIA {

    /**
     * application de la stratégie Aléatoire qui choisit un coup aléatoire parmi
     * tout les coups possibles pour l'IA.
     * @param partie la partie en son état actuel
     * @return le coup choisi aléatoirement par l'IA sous forme de coordonées {i,j}
     */
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
