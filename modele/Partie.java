package modele;

public class Partie {
    private int[][] tableau = new int[8][8];

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
        } else return tableau[l][c] == 0;
    }

    @Override
    public String toString() {
        String s="   ";
        int nbColonne = tableau[0].length;
        int nbLigne = tableau.length;
        for (int i = 0; i < nbColonne; i++){
            s+= " "+(char) ('A' + i)+" " ;
        }
        s+="\n";
        for (int i = 0; i < nbLigne; i++) {
            s+=" "+i+" ";
            for (int j = 0; j < nbColonne; j++) {
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
