package personnages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import carte.Coordinates;
import entite.Direction;
import entite.Team;

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

	// TODO : get fonction

	public Object getRobotInfoFromClass(Class <?> classRobot, Robot robot){
		Map<Object, Robot> currentClassMap;
		for(Class<?> currentClass : fromClassToMap.keySet()){
			
			if(classRobot.equals(currentClass)){
				currentClassMap = fromClassToMap.get(currentClass);
				for(Object currentClassMapCell : currentClassMap.keySet()){
					if(currentClassMap.get(currentClassMapCell).equals(robot)){
						return currentClassMapCell;
					}
				}
			}
		}
		return null;
	}
	
	
	public Robot getRobotFromInfo(Object obj){
		Map<Object, Robot> currentClassMap;
		for(Class<?> currentClass : fromClassToMap.keySet()){
			if(currentClass.equals(obj.getClass())){
				currentClassMap = fromClassToMap.get(currentClass);
				for(Object currentClassMapObject : currentClassMap.keySet()){
					if(currentClassMapObject.equals(obj)){
						return currentClassMap.get(currentClassMapObject);
					}
				}
			}
		}
		return null;
	}
	
	
	// Test

	public static void main(String[] args) {
		RobotList myRobotList = new RobotList();
		carte.Map my_map = new carte.Map();
		Robot robot = new Robot(2, 4, my_map, Direction.NORTH, 1, 1, 1, 1, 5, 1, Team.ROUGE);
		Robot robot2 = new Robot(3, 4, my_map, Direction.NORTH, 1, 1, 1, 1, 5, 1, Team.ROUGE);
		Robot robot3 = new Robot(8, 10, my_map, Direction.NORTH, 1, 1, 1, 1, 5, 1, Team.ROUGE);
		myRobotList.add(new Coordinates(1, 1), robot);
		String my_name =new String("Mon robot");
		myRobotList.add(my_name, robot);
		myRobotList.add(1, robot);
		myRobotList.add(new Coordinates(1, 1), robot2);
		myRobotList.add(Direction.EAST, robot3);
		myRobotList.printList();

		// Get test
		Coordinates myCoord = (Coordinates) myRobotList.getRobotInfoFromClass(Coordinates.class, robot);
		System.out.print("coordonné du robot1 : " + myCoord.getX() + " ; " + myCoord.getY()+ "\n");
		System.out.print("Nom du robot1 : " + myRobotList.getRobotInfoFromClass(String.class, robot) + "\n");
		System.out.print("Direction robot 3 : " + myRobotList.getRobotInfoFromClass(Direction.class, robot3) + "\n");
		
		// Reverse Get test
		
		System.out.println("Coordonnée du robot 1 : " + myRobotList.getRobotFromInfo(myCoord).getX() + " ; " +myRobotList.getRobotFromInfo(myCoord).getY());
		System.out.println("Coordonnée du portant le nom " + my_name + " : " + myRobotList.getRobotFromInfo(my_name).getX() + " ; " +myRobotList.getRobotFromInfo(my_name).getY());
		// Remove test
		myRobotList.remove(robot);
		System.out.println("\n \n Remove test \n \n");
		myRobotList.printList();
	}

}
