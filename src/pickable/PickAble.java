package pickable;

import carte.Map;
import entite.Entity;
import personnages.Robot;

public abstract class PickAble extends Entity {

	public PickAble(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

	public void pick(Entity e) {
		Class <PickAble> ramasse = Map.ramassable(e.getX(),e.getY());
		Map.freePick(ramasse, e.getX(), e.getY());
		int i = ((Robot) e).isToPlayer().besace.get(ramasse.getClass());
		((Robot) e).isToPlayer().besace.put(ramasse, i++);

	}
}
