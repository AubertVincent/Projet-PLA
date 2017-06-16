package sequence;

import java.util.List;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;

public interface _Sequence {

	// ↓ Miscellaneous methods ↓
	/**
	 * 
	 * @return true if this sequence is an action
	 */
	public boolean isAction();

	/**
	 * 
	 * @return true if this sequence is a tree
	 */
	public boolean isTree();

	/**
	 * 
	 * @param r
	 *            : Read a sequence and add all action to a action list
	 * @throws NotDoableException
	 */
	public void addActionToActionList(Robot r) throws NotDoableException;

	/**
	 * 
	 * @param x
	 *            : X coordinate of the robot which have this sequence
	 * @param y
	 *            : Y coordinate of the robot which have this sequence
	 * @param map
	 *            : Map of the game
	 * @return Returns the list of pickable objects that were used to create the
	 *         sequence
	 */
	public List<PickAble> sequenceToPickAbleList(int x, int y, Map map);

	// End(Miscellaneous methods)

}
