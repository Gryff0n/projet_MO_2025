import controleur.Controleur;
import modele.Partie;
import vue.Ihm;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.create(ihm.demanderNomJoueur(1));
        controleur.create(ihm.demanderNomJoueur(2));

        /*Partie partie=new Partie();
        partie.initialiser();
        System.out.println(partie);
        System.out.println(controleur.getJoueur(1).getNom());*/
    }
}
