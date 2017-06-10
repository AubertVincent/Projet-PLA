package pickable;

import carte.Map;
import entite.Entity;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import operateur.PickUp;
import operateur.Priority;
import operateur.RandomBar;
import operateur.Recall;
import operateur.Succession;
import operateur.SuicideBomber;
import operateur.Tunnel;

public abstract class PickAble extends Entity {

	public PickAble(int x, int y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

	public boolean isPickAble() {
		return true;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	public PickAble actionToPickable(Action act) {
		PickAble picka = null;
		if (act.getClass().equals(ClassicAck.class)) {
			picka = new PickClassicAck(1, 1, entityMap);
		}
		if (act.getClass().equals(MoveDir.class)) {
			picka = new PickMoveDir(1, 1, entityMap);
		}
		if (act.getClass().equals(PickUp.class)) {
			picka = new PickPickUp(1, 1, entityMap);
		}
		if (act.getClass().equals(Priority.class)) {
			picka = new PickPriority(1, 1, entityMap);
		}
		if (act.getClass().equals(RandomBar.class)) {
			picka = new PickRandomBar(1, 1, entityMap);
		}
		if (act.getClass().equals(Recall.class)) {
			picka = new PickRecall(1, 1, entityMap);
		}
		if (act.getClass().equals(Succession.class)) {
			picka = new PickSuccession(1, 1, entityMap);
		}
		if (act.getClass().equals(SuicideBomber.class)) {
			picka = new PickSuicideBomber(1, 1, entityMap);
		}
		if (act.getClass().equals(Tunnel.class)) {
			picka = new PickTunnel(1, 1, entityMap);
		}
		return picka;
	}

}
