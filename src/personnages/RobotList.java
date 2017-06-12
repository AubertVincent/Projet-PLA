package personnages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RobotList {

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

	// TEST
	private void printList() {
		Map<Object, Robot> currentClassMap;
		for (Map.Entry<Class<?>, Map<Object, Robot>> currentClass : fromClassToMap.entrySet()) {
			currentClassMap = currentClass.getValue();
			for (Map.Entry<Object, Robot> currentClassMapCell : currentClassMap.entrySet()) {
				System.out.println("Coordonnée de ce robot :" + currentClassMapCell.getValue().getX() + ";"
						+ currentClassMapCell.getValue().getY() + "  Class :"
						+ currentClassMapCell.getKey().getClass());
			}

		}
	}

	public void remove(Robot robot) {
		Map<Object, Robot> currentClassMap;

		for (Iterator<Map.Entry<Class<?>, Map<Object, Robot>>> itr = fromClassToMap.entrySet().iterator(); itr
				.hasNext();) {
			Map.Entry<Class<?>, Map<Object, Robot>> entry = itr.next();
			currentClassMap = entry.getValue();
			for (Iterator<Map.Entry<Object, Robot>> itrMap = currentClassMap.entrySet().iterator(); itrMap.hasNext();) {
				Map.Entry<Object, Robot> entryMap = itrMap.next();
				if (entryMap.getValue().equals(robot)) {
					itrMap.remove();
				}
			}
		}
	}

	public Object getRobotInfoFromClass(Class<?> classRobot, Robot robot) {
		Map<Object, Robot> currentClassMap;
		for (Class<?> currentClass : fromClassToMap.keySet()) {

			if (classRobot.equals(currentClass)) {
				currentClassMap = fromClassToMap.get(currentClass);
				for (Object currentClassMapCell : currentClassMap.keySet()) {
					if (currentClassMap.get(currentClassMapCell).equals(robot)) {
						return currentClassMapCell;
					}
				}
			}
		}
		return null;
	}

	public Robot getRobotFromInfo(Object obj) {
		Map<Object, Robot> currentClassMap;
		for (Class<?> currentClass : fromClassToMap.keySet()) {
			if (currentClass.equals(obj.getClass())) {
				currentClassMap = fromClassToMap.get(currentClass);
				for (Object currentClassMapObject : currentClassMap.keySet()) {
					if (currentClassMapObject.equals(obj)) {
						return currentClassMap.get(currentClassMapObject);
					}
				}
			}
		}
		return null;
	}

	public List<Robot> getRobotList() {
		List<Robot> robotList = new ArrayList<Robot>();
		Map<Object, Robot> currentClassMap;
		for (Iterator<Map.Entry<Class<?>, Map<Object, Robot>>> itr = fromClassToMap.entrySet().iterator(); itr
				.hasNext();) {
			Map.Entry<Class<?>, Map<Object, Robot>> entry = itr.next();
			currentClassMap = entry.getValue();
			for (Iterator<Map.Entry<Object, Robot>> itrMap = currentClassMap.entrySet().iterator(); itrMap.hasNext();) {
				Map.Entry<Object, Robot> entryMap = itrMap.next();
				if (!robotList.contains(entryMap.getValue())) {
					robotList.add(entryMap.getValue());
				}
			}
		}
		return robotList;
	}

}
