package personnages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RobotList {

	private Map<Class<?>, Map<Object, Robot>> fromClassToMap;

	// ↓ Constructor, update and render ↓
	/**
	 * Used to create a new Robot List
	 */
	public RobotList() {
		fromClassToMap = new HashMap<Class<?>, Map<Object, Robot>>();
	}
	// End(Constructor)

	// ↓ Miscellaneous methods ↓
	/**
	 * Used to add a new robot to the player's robot list
	 * 
	 * @param obj
	 *            the object who reference the robot in the list
	 * @param robot
	 *            the to add at the list
	 */
	public void add(Object obj, Robot robot) {
		// Search if the Map for the Class of obj already existss
		Class<?> objClass = obj.getClass();
		if (!fromClassToMap.containsKey(objClass)) {
			fromClassToMap.put(objClass, new HashMap<Object, Robot>());
		}
		fromClassToMap.get(objClass).put(obj, robot);
	}

	/**
	 * Used to remove the given robot from the player's robot list
	 * 
	 * @param robot
	 *            the robot to remove
	 */
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
	// End(Miscellaneous methods)

	// ↓ Getters and setters ↓
	/**
	 * Used to get a robot's information from it
	 * 
	 * @param classRobot
	 *            the robot's reference
	 * @param robot
	 *            the robot to get information
	 * @return
	 */
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

	/**
	 * Used to get a robot in the rebolist from its reference
	 * 
	 * @param obj
	 *            the robot's reference
	 * @return the robot
	 */
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

	/**
	 * Used to get the whole player's robot list
	 * 
	 * @return the player's robot list
	 */
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
	// End(Getters and setters)

}
