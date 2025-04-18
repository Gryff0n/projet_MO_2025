package modele.Jeux;

public interface Jeux {

    void initialiser();

    String toString();

    void jouerCoup(int l, int c, int numero);


    boolean coupImpossible(int numero);

    boolean coupValide(int l, int c, int numero);

    int checkVictoire();

    String afficherScore();

    String getMessageDemanderCoup(int numero);
}
