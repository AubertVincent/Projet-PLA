package sequence;

import java.util.List;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;

public interface _Sequence {

	public boolean isAction();

	public boolean isTree();

	public void execute(Robot r) throws NotDoableException;

	public List<PickAble> sequenceToPickAbleList(int x, int y, Map map);

}
