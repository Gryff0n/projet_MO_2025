package modele.IA;

import modele.Jeux.Othello;

import java.util.List;
import java.util.Random;

public class Aleatoire implements StrategiesIA {

    /**
     * application de la stratégie Aléatoire qui choisit un coup aléatoire parmi
     * tout les coups possibles pour l'IA.
     * @param othello la partie en son état actuel
     * @return le coup choisi aléatoirement par l'IA sous forme de coordonées {i,j}
     */
    public int[] appliquerStrategie(Othello othello) {
        List<int[]> coupsPotentiels;
        coupsPotentiels= othello.coupsPotentiels(2);
        if (coupsPotentiels.isEmpty()) {
            return new int[]{-1};
        }
        int x = new Random().nextInt(coupsPotentiels.size());
        return coupsPotentiels.get(x);
    }
}
