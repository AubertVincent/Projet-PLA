package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class PickUp extends Action {

	@Override
	protected boolean isDoable(Robot r) {
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (isDoable(r)) {
			throw new NotDoableException("Imoossible de ramasser"); // Should
																	// never
																	// happen
		} else {
			r.pickUp();

		}
	}

}
