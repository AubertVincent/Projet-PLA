package operateur;

import personnages.Character;

public abstract class Attack extends Action {

	public Attack() {
		super();
	}

	protected Character opponent;
	protected Character attacker;

}
