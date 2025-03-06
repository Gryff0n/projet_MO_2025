package vue;

import java.util.Scanner;

public class Ihm {
    public String demanderNomJoueur(int numero) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du joueur " + numero);
        while (!sc.hasNextLine()) {
            if (sc.hasNextLine()) {
                return sc.nextLine();
            }
        }
        return null;
    }
}
