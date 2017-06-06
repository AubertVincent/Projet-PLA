package pickable;

import carte.Map;
import entite.Entity;
import personnages.Player;
import personnages.Robot;

public abstract class PickAble extends Entity {

	public PickAble(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

	/**
	 * Pick an entity ('picked' here) on the cell
	 * 
	 * @param e
	 *            The entity which is picking
	 */
	public static void pick(Entity e) {
		Class<PickAble> picked = Map.pickableEntity(e.getX(), e.getY());
		Map.freePick(picked, e.getX(), e.getY());
		if (e instanceof Robot) {
			int i = ((Robot) e).isToPlayer().besace.get(picked.getClass());
			((Robot) e).isToPlayer().besace.put(picked, i++);
		} else if (e instanceof Player) {
			int i = ((Player) e).besace.get(picked.getClass());
			((Player) e).besace.put(picked, i++);
		}

	}
}
