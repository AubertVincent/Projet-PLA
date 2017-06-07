package operateur;

import personnages.*;

public abstract class Attack extends Action {
	
	public Attack(int x, int y) {
		super(x, y);
	}
	Character opponent;
	Character attacker;
	
}
