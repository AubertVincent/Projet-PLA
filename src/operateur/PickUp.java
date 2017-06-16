package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickPickUp;

public class PickUp extends Action {

	// ↓ Constructor, update and render ↓

	// This action should never be called
	@Override
	protected boolean isDoable(Robot r) {
		// Always doAble
		return true;
	}

	@Override
	public void execute(Robot robot) throws NotDoableException {
		if (isDoable(robot)) {
			// Should never happen
			throw new NotDoableException("Impossible to pick");
		} else {

		}
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickPickUp.class;
	}

	// End(Constructor, update and render)

}
