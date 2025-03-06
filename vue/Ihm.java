package vue;

import java.util.Scanner;

public class Ihm {
    public String demanderNomJoueur(int numero) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du joueur " + numero);
        return sc.nextLine();
    }

    public void afficher(String message) {
        System.out.println(message);
    }

    public String demanderCoup(String joueur) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Au tour de " + joueur);
        System.out.println("Entrez un coup (ex: '1 A' ou P pour passer son tour si aucun coup possible) :");
        return sc.nextLine();
    }
}
