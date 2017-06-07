package gui;

import java.awt.MouseInfo;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

public class GUIBehaviorInput {

	TextField textField;
	String receivedString;
	String sentString;
	boolean inputUpToDate;

	/**
	 * 
	 * @param container
	 *            the context in which GUI components are created and rendered
	 * @param WindowWidth
	 *            width of the windows container
	 * @param WindowHeight
	 *            height of the windows container
	 * @param TextFieldHeight
	 *            height of the Textfield where you write your instruction for
	 *            the robots
	 * @param instructions
	 *            String with the example of instruction for the robots
	 */
	protected GUIBehaviorInput(GameContainer container, int WindowWidth, int WindowHeight, int TextFieldHeight,
			String instructions) {
		Font defaultfont = container.getDefaultFont();
		this.textField = new TextField(container, defaultfont, 0, WindowHeight - TextFieldHeight, WindowWidth,
				TextFieldHeight);
		this.textField.setText(instructions);
	}

	/**
	 * 
	 * @param container
	 *            the context in which GUI components are created and rendered
	 */
	protected void update(GameContainer container) {
		// Check if you have write your instruction and press enter one time
		if (!inputUpToDate) {
			// get sent behavior instructions on an enter_key press
			if (this.textField.hasFocus()) {
				if (container.getInput().isKeyDown(Input.KEY_ENTER)) {
					receivedString = textField.getText();
					inputUpToDate = true;
					GUI.behaviorInputNeeded = false;
					this.textField.setFocus(false);
					System.out.println("> " + receivedString);
				}
			}
		}

		// Get (and print in console to test) mouse position in case of a click
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
			double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
			System.out.println("X:" + mouseX);
			System.out.println("Y:" + mouseY);
		}
	}

	/**
	 * 
	 * @param container
	 *            the context in which GUI components are created and rendered
	 * @param g
	 *            a graphics context that can be used to render primitives
	 */
	protected void render(GameContainer container, Graphics g) {
		g.setColor(Color.white);
		Color backgroundField = new Color(0f, 0f, 0f, 0.7f);
		this.textField.setBackgroundColor(backgroundField);
		this.textField.setBorderColor(backgroundField);
		this.textField.render(container, g);
		inputUpToDate = false;
//		this.textField.setFocus(true);
	}

}