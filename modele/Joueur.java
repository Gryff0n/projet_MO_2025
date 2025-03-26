package modele;

public class Joueur {
    private String nom;
    private int score = 0;

    public Joueur(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public void Victoire() {
        score++;
    }
}
