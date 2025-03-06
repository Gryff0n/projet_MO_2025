package modele;

public class Partie {
    private int taille = 8;
    private int[][] tableau = new int[taille][taille];

    public void initialiser(){
        for(int i = 0; i < taille; i++){
            for(int j = 0; j < taille; j++){
                if ((i==(taille-1)/2 && j==(taille-1)/2)||(i==(taille-1)/2+1 && j==(taille-1)/2+1)){
                    tableau[i][j]=1;
                }
                else if ((i==(taille-1)/2+1 && j==(taille-1)/2)||(i==(taille-1)/2 && j==(taille-1)/2+1)){
                    tableau[i][j]=2;
                }
                else {
                    tableau[i][j]=0;
                }
            }
        }
    }

    public boolean coupValide(int l, int c, Joueur j){
        if (l < 0 || l > taille-1 || c < 0 || c > taille-1) {
            return false;
        } else return tableau[l][c] == 0;
    }

    @Override
    public String toString() {
        String s="   ";
        for (int i = 0; i < taille; i++){
            s+= " "+(char) ('A' + i)+" " ;
        }
        s+="\n";
        for (int i = 0; i < taille; i++) {
            s+=" "+(i+1)+" ";
            for (int j = 0; j < taille; j++) {
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
