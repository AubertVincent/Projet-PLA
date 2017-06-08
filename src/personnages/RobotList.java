package personnages;

import java.util.HashMap;
import java.util.Map;

public class RobotList<robotListInterger> {

	private Map<Class<?>, Map<Object, Robot>> fromClassToMap;

	public RobotList() {
		// robotListInteger = new HashMap<Integer, Robot>();
		// robotListCoordinates = new HashMap<Coordinates, Robot>();
		fromClassToMap = new HashMap<Class<?>, Map<Object, Robot>>();
	}

	public void add(Object obj, Robot robot) {
		// Search if the Map for the Class of obj already existss
		Class<?> objClass = obj.getClass();
		if (!fromClassToMap.containsKey(objClass)) {
			fromClassToMap.put(objClass, new HashMap<Object, Robot>());
		}
		fromClassToMap.get(objClass).put(obj, robot);
	}
	
	

}
