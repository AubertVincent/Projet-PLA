package pickable;

import java.util.List;

public class Picked {

	public List<Class<PickAble>> picked;

	public Picked(List<Class<PickAble>> picked) {
		super();
		this.picked = picked;
	}

	public void add(Class<PickAble> classPicked) {
		picked.add(classPicked);
	}

	public int size() {
		return picked.size();
	}

	public Class<PickAble> get(int i) {
		return picked.get(i);
	}

	public void remove(int i) {
		picked.remove(i);

	}

}
