package operateur;

import personnages.Character;

public abstract class Attack extends Action {

	protected Character opponent;
	protected Character attacker;

	public Attack(Character opponent, Character attacker) {
		super();
		this.opponent = opponent;
		this.attacker = attacker;
	}

	public Attack() {
		super();
	}

}
