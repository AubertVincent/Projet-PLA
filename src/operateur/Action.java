package operateur;

import java.util.LinkedList;
import java.util.List;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickClassicAck;
import pickable.PickMoveDir;
import pickable.PickPickUp;
import pickable.PickPriority;
import pickable.PickRandomBar;
import pickable.PickRecall;
import pickable.PickSuccession;
import pickable.PickSuicideBomber;
import pickable.PickTunnel;
import sequence._IncompleteSequence;
import sequence._Sequence;

public abstract class Action implements _Sequence, _IncompleteSequence {

	// ↓ Miscellaneous methods ↓

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Check if a robot is able to execute an action
	 * 
	 * @param r
	 *            : Looks if the action is feasible for this robot
	 * @return If this action is doable for this robot then return true
	 */
	/**
	 *
	 * @return If this action is doable for this robot then return true
	 */
	protected abstract boolean isDoable(Robot r);

	// protected abstract void execute(Robot r) throws NotDoableException;

	@Override
	public boolean isAction() {
		return true;
	}

	@Override
	public boolean isTree() {
		return false;
	}

	@Override
	public boolean isEmptyLeaf() {
		return false;
	}

	/**
	 * Give the class of the pickable of the action
	 * 
	 * @return Returns a class which is the picakble of this action
	 */
	public abstract Class<? extends PickAble> getPickable();

	@Override
	public boolean isEmptyRootTree() {
		return false;
	}

	/**
	 * Execute robot's action
	 * 
	 * @param r
	 *            : The robot which will execute the action
	 * @throws NotDoableException
	 */
	public abstract void execute(Robot r) throws NotDoableException;

	public void addActionToActionList(Robot r) {
		r.addActionToActionList(this);
	}

	/**
	 * 
	 * @param x
	 *            : X coordinate of the robot which have this action
	 * @param y
	 *            : Y coordinate of the robot which have this action
	 * @param map
	 *            : Map of the game
	 * @return Returns the pickable of the action
	 */
	protected PickAble actionToPickAble(int x, int y, Map pickableMap) {
		PickAble pickAble;
		if (this.getClass().equals(ClassicAck.class)) {
			pickAble = new PickClassicAck(x, y, pickableMap);
		} else if (this.getClass().equals(MoveDir.class)) {
			pickAble = new PickMoveDir(x, y, pickableMap);
		} else if (this.getClass().equals(PickUp.class)) {
			pickAble = new PickPickUp(x, y, pickableMap);
		} else if (this.getClass().equals(Priority.class)) {
			pickAble = new PickPriority(x, y, pickableMap);
		} else if (this.getClass().equals(RandomBar.class)) {
			pickAble = new PickRandomBar(x, y, pickableMap);
		} else if (this.getClass().equals(Recall.class)) {
			pickAble = new PickRecall(x, y, pickableMap);
		} else if (this.getClass().equals(Succession.class)) {
			pickAble = new PickSuccession(x, y, pickableMap);
		} else if (this.getClass().equals(SuicideBomber.class)) {
			pickAble = new PickSuicideBomber(x, y, pickableMap);
		} else if (this.getClass().equals(Tunnel.class)) {
			pickAble = new PickTunnel(x, y, pickableMap);
		} else {
			pickAble = null;
		}
		return pickAble;
	}

	@Override
	public List<PickAble> sequenceToPickAbleList(int x, int y, Map pickableMap) {
		List<PickAble> pickAbleList = new LinkedList<PickAble>();
		if (!(this instanceof Explore)) {
			pickAbleList.add(this.actionToPickAble(x, y, pickableMap));
		}
		return pickAbleList;

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	// End(Miscellaneous methods)
}
