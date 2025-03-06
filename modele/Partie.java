package modele;

public class Partie {
    int[][] tableau = new int[8][8];

    public void initialiser(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tableau[i][j] = 0;
            }
        }
    }

    public boolean coupValide(int l, int c, Joueur j){
        if (l < 0 || l > 7 || c < 0 || c > 7) {
            return false;
        } else if (tableau[l][c] != 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tableau[i][j] == 0) {
                    s+=" \uD83D\uDFE9 ";
                } else if (tableau[i][j] == 1) {
                    s+=" \u26AA ";
                }
                else {
                    s+=" \u26AB ";
                }
            }
            s+="\n";
        }
        return s;
    }
}
