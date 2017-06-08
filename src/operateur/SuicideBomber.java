package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class SuicideBomber extends Attack {

	@Override
	public boolean isDoable(Robot r) {
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Cette entité n'est pas un robot");
		} else {
			r.suicideBomber();
		}

	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// TODO Auto-generated method stub
		
	}

}
