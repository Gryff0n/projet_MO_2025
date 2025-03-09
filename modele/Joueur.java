package modele;

public class Joueur {
    private String couleur;
    private String nom;
    private int score = 0;

    public Joueur(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getScore() {
        return score;
    }

    public void Victoire() {
        score++;
    }
}
