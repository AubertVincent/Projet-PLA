package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class PickUp extends Action {

	@Override
	protected boolean isDoable(Robot r) {
		return true;
	}

	@Override
	public void execute(Robot robot) throws NotDoableException {
		if (isDoable(robot)) {
			throw new NotDoableException("Impossible de ramasser"); // Should
																	// never
																	// happen
		} else {
			robot.pickUp();

		}
	}

	@Override
	public void cancel(Robot robot) throws NotDoableException {
		if (isDoable(r)) {
			throw new NotDoableException("Impossible de ramasser");
		} else {
			robot.cancelPickUp();
		}

	}

}
