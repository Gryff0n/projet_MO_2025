import controleur.Controleur;
import vue.Ihm;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.jouer();


        /*Partie partie=new Partie();
        partie.initialiser();
        System.out.println(partie);
        System.out.println(controleur.getJoueur(1).getNom());*/
    }
}
