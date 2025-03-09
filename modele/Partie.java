package modele;

import java.util.ArrayList;
import java.util.List;

public class Partie {
    private int taille = 8;
    private int[][] tableau = new int[taille][taille];
    private int nb_jetons_blancs =2;
    private int nb_jetons_noirs = 2;

    public void initialiser(){
        for(int i = 0; i < taille; i++){
            for(int j = 0; j < taille; j++){
                if ((i==(taille-1)/2 && j==(taille-1)/2)||(i==(taille-1)/2+1 && j==(taille-1)/2+1)){
                    tableau[i][j]=2;
                }
                else if ((i==(taille-1)/2+1 && j==(taille-1)/2)||(i==(taille-1)/2 && j==(taille-1)/2+1)){
                    tableau[i][j]=1;
                }
                else {
                    tableau[i][j]=0;
                }
            }
        }
    }

    public boolean coupImpossible(int numero){
        /* Verifie si aucun coup possible autour d'un pion adverse */
        boolean b = true;
        int i = 0;
        while (b && i < taille){
            int j = 0;
            while(b && j < taille){
                if (tableau[i][j] == 0){
                    b = !verifRetourneUnPion(i,j,numero);
                }
                j++;
            }
            i++;
        }
        return b;
    }

    public boolean coupValide(int l, int c, int numero){
        // Si en dehors du plateau
        if (l < 0 || l > taille-1 || c < 0 || c > taille-1) {
            return false;
        }
        else if (tableau[l][c] == 0){
            //Vérifie si le coup permet de retourner au moins un pion
            return verifRetourneUnPion(l,c,numero);
        }
        else return false;
    }

    public boolean verifRetourneUnPion(int l, int c, int numero) {
        // Numéro de l'adversaire
        int adversaire = (numero == 1) ? 2 : 1;
        boolean peutRetourner = false;
        // Parcourir les 8 directions autour du point (l, c)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Ignorer la direction nulle (0, 0)
                if ((dx == 0 && dy == 0) && tableau[l+dx][c+dy] != adversaire) {
                    continue;
                }
                // Vérifier si on peut retourner dans cette direction
                int x = l + dx;
                int y = c + dy;
                boolean trouveAdversaire = false;
                // Explorer cette direction jusqu'à rencontrer un bord ou un pion
                while (x >= 0 && x < taille && y >= 0 && y < taille) {
                    // Si la case est vide ou contient un pion du joueur initial, arrêter
                    if (tableau[x][y] == 0) {
                        break;
                    } else if (tableau[x][y] == numero) {
                        // Si un pion du joueur initial est trouvé après un ou plusieurs adversaires
                        if (trouveAdversaire) {
                            peutRetourner = true;
                        }
                        break;
                    } else if (tableau[x][y] == adversaire) {
                        // Marquer qu'au moins un pion adverse est trouvé dans cette direction
                        trouveAdversaire = true;
                    }
                    // Avancer dans la direction
                    x += dx;
                    y += dy;
                }
                if (peutRetourner) {
                    return peutRetourner;
                }
            }
        }
        return peutRetourner;
    }

    public void jouerCoup(int l, int c, int numero){
        if(numero==1) nb_jetons_noirs++;
        else nb_jetons_blancs++;
        tableau[l][c]=numero;
        int adversaire = (numero == 1) ? 2 : 1;
        List<int[]> pionsPotentiels = new ArrayList<>();
        // Parcourir les 8 directions autour du point (l, c)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Ignorer la direction nulle (0, 0)
                if ((dx == 0 && dy == 0) && tableau[l+dx][c+dy] != adversaire) {
                    continue;
                }
                // Vérifier si on peut retourner dans cette direction
                int x = l + dx;
                int y = c + dy;
                boolean trouveAdversaire = false;
                pionsPotentiels.clear();

                // Explorer cette direction jusqu'à rencontrer un bord ou un pion
                while (x >= 0 && x < taille && y >= 0 && y < taille) {
                    // Si la case est vide ou contient un pion du joueur initial, arrêter
                    if (tableau[x][y] == 0) {
                        break;
                    } else if (tableau[x][y] == numero) {
                        // Si un pion du joueur initial est trouvé après un ou plusieurs adversaires
                        if (trouveAdversaire) {
                            for (int[] pion : pionsPotentiels) {
                                System.out.println(pion[0]+" _ "+pion[1]);
                                retourPion(pion[0],pion[1],numero);
                            }
                        }
                        break;
                    } else if (tableau[x][y] == adversaire) {
                        // Marquer qu'au moins un pion adverse est trouvé dans cette direction
                        trouveAdversaire = true;
                        pionsPotentiels.add(new int[]{x,y});
                    }
                    // Avancer dans la direction
                    x += dx;
                    y += dy;
                }
            }
        }
    }

    public void retourPion(int l, int c, int numero){
        tableau[l][c]=numero;
        if(numero==1) {
            nb_jetons_noirs++;
            nb_jetons_blancs--;
        }
        else {
            nb_jetons_blancs++;
            nb_jetons_noirs--;
        }
    }

    public int[] getNb_jetons_plateau() {
        return new int[]{nb_jetons_noirs, nb_jetons_blancs};
    }

    @Override
    public String toString() {
        String s="  ";
        for (int i = 0; i < taille; i++){
            s+= "   "+(char) ('A' + i) ;
        }
        s+="\n";
        for (int i = 0; i < taille; i++) {
            s+=" "+(i+1)+" ";
            for (int j = 0; j < taille; j++) {
                if (tableau[i][j] == 0) {
                    s+=" \uD83D\uDFE9 ";
                }
                else if (tableau[i][j] == 2) {
                    s+=" \u26AA ";
                }
                else if (tableau[i][j] == 1){
                    s+=" \u26AB ";
                }
            }
            s+="\n";
        }
        return s;
    }

}
