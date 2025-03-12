package vue;

import java.util.Scanner;

public class Ihm {
    private Scanner sc=new Scanner(System.in);
    public String demanderNomJoueur(int numero) {
        System.out.println("Entrez le nom du joueur " + numero);
        return sc.nextLine();
    }

    public void afficher(String message) {
        System.out.println(message);
    }

    public String demanderCoup(String joueur) {
        System.out.println("Au tour de " + joueur);
        System.out.println("Entrez un coup (ex: '1 A' ou 'P' pour passer son tour si aucun coup possible) :");
        return sc.nextLine();
    }

    public String redemPartie() {
        System.out.println("Voulez vous redémarrer une partie (Y/N) ?\n>>>");
        return sc.nextLine();
    }

    public String demanderIA() {
        System.out.println("Voulez vous jouez contre l'ordinateur (Y/N) ?\n>>>");
        return sc.nextLine();
    }
}
