package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import personnages.Player;
import reader.Reader;
import sequence._Sequence;
import test.SequenceCorrector;

public class GUIBehaviorInput {

	private static final String defaultInstructions = "(MC5N;AC) / (MC7N;AC))";
	GUI userInterface;
	TextField textField;
	int textFieldHeight;

	String receivedString;
	String sentString;

	private boolean inputUpToDate;
	private _Sequence receivedSequence;

	private SequenceCorrector corrector = new SequenceCorrector();

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
			String instructions) {
		Font defaultfont = container.getDefaultFont();
		textFieldHeight = 50;
		textField = new TextField(container, defaultfont, 0, WindowHeight - textFieldHeight, WindowWidth,
				textFieldHeight);
		textField.setText(instructions);
		this.userInterface = userInterface;
	}

	protected GUIBehaviorInput(GameContainer container, GUI userInterface, int WindowWidth, int WindowHeight) {
		new GUIBehaviorInput(container, userInterface, WindowWidth, WindowHeight, defaultInstructions);
	}

	/**
	 * 
	 * @param container
	 *            the context in which GUI components are created and rendered
	 */
	protected void update(GameContainer container, Player currentPlayer) {
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
					receivedSequence = Reader.parse(receivedString);
				}
			}
			// If input was just updated, check if it is correct
			if (inputUpToDate) {
				corrector.set(currentPlayer.getBesace(), receivedSequence);

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

		// If the given behavior isn't correct, show the correction
		if (!corrector.getCorrectness()) {
			corrector.drawCorrectedList(g, userInterface.getWindowWidth(), userInterface.getWindowHeight());
		}
	}

}