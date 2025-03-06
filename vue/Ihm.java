package vue;

import java.util.Scanner;

public class Ihm {
    public String demanderNomJoueur(int numero) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du joueur " + numero);
        return sc.nextLine();
    }
}
