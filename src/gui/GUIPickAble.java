package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pickable.PickAble;

public class GUIPickAble {

	GUI mainUserInterface;
	PickAble pick;
	Image image;

	// Given EVERY POSSIBLE doable action (by the Player), gives the paths to
	// its animation and its number of sprites
	protected static Map<Class<? extends PickAble>, String> pickPath = new HashMap<Class<? extends PickAble>, String>();
	static {
		List<Class<? extends PickAble>> possiblePickAbleList = PickAble.getPossiblePickAbleList();
		for (Iterator<Class<? extends PickAble>> pickAble = possiblePickAbleList.iterator(); pickAble.hasNext();) {
			Class<? extends PickAble> currentPickAble = pickAble.next();
			pickPath.put(currentPickAble, "res/pickAble/" + currentPickAble.getSimpleName() + ".png");
		}
	}

	public GUIPickAble(PickAble pickAble) {
		String pathToImage = pickPath.get(pickAble.getClass());
		try {
			image = new Image(pathToImage);
		} catch (SlickException e) {
			System.out.println("Couldn't load pickable sprite");
			e.printStackTrace();
		}
		pick = pickAble;
	}

	public void render() {
		int x = pick.getX();
		int y = pick.getY();
		float xPx = mainUserInterface.cellToPixelX(x);
		float yPx = mainUserInterface.cellToPixelY(y);
		image.draw(xPx, yPx);
	}

}