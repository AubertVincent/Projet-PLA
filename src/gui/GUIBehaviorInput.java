package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import reader.Reader;

public class GUIBehaviorInput {

	GUI userInterface;
	TextField textField;
	String receivedString;
	String sentString;
	private boolean inputUpToDate;

	/**
	 * 
	 * @param container
	 *            the context in which GUI components are created and rendered
	 * @param WindowWidth
	 *            width the windows container
	 * @param WindowHeight
	 *            height of the windows container
	 * @param TextFieldHeight
	 *            height of the Textfield where you write your instruction for
	 *            the robots
	 * @param instructions
	 *            String with the example of instruction for the robots
	 */
	protected GUIBehaviorInput(GameContainer container, GUI userInterface, int WindowWidth, int WindowHeight,
			int TextFieldHeight, String instructions) {
		Font defaultfont = container.getDefaultFont();
		textField = new TextField(container, defaultfont, 0, WindowHeight - TextFieldHeight, WindowWidth,
				TextFieldHeight);
		textField.setText(instructions);
		this.userInterface = userInterface;
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
					userInterface.behaviorInputNeeded = false;
					this.textField.setFocus(false);
					System.out.println("> " + receivedString);
					Reader.parse(receivedString);
				}
			}
		}
	}

	public void changeText(String s) {
		textField.setText(s);
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
		// this.textField.setFocus(true);
	}

}