package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickPickUp;

public class PickUp extends Action {

	@Override
	protected boolean isDoable(Robot r) {
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (isDoable(r)) {
			throw new NotDoableException("Impossible de ramasser"); // Should
																	// never
																	// happen
		} else {
			r.pickUp();

		}
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		if (isDoable(r)) {
			throw new NotDoableException("Impossible de ramasser");
		} else {
			r.cancelPickUp();
		}

	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickPickUp.class;
	}

}
