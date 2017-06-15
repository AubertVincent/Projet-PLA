package operateur;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import sequence._Sequence;

public abstract class Behavior {

	public abstract void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public abstract Class<? extends PickAble> getPickable();

	public abstract PickAble behaviorToPickAble(int x, int y, Map map);

}