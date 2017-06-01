package operateur;

import java.util.Random;

public class Aleatoire extends Comportement {

	public boolean test() {
		Random r = new Random();
		int n = r.nextInt(2);
		return (n == 0);
	}
	
	public void aleatoire(Action a, Action b){
		//TODO Créer une nouvelle classe qui prend une séquence et qui gère l'execution des actions
		if (test()){
			a.execute();
		}else{
			b.execute();
		}
	}
}
