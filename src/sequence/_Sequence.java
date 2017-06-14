package sequence;

import exceptions.NotDoableException;
import personnages.Robot;

public interface _Sequence {

	public boolean isAction();

	public boolean isTree();

	public void addActionToActionList(Robot r) throws NotDoableException;
}
