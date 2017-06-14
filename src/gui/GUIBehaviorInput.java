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

	private static final String defaultInstructions = "MC1S";
	private GUI userInterface;
	private TextField textField;
	int textFieldHeight;

	private String receivedString;

	private _Sequence receivedSequence;
	private SequenceCorrector corrector = new SequenceCorrector();

	private boolean inputUpToDate;
	private boolean inputCorrect;
	private boolean setForCurrentRequest;

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
		inputUpToDate = false;
		inputCorrect = false;
		this.userInterface = userInterface;
		inputCorrect = false;
		setForCurrentRequest = false;
	}

	protected GUIBehaviorInput(GameContainer container, GUI userInterface, int WindowWidth, int WindowHeight) {
		this(container, userInterface, WindowWidth, WindowHeight, defaultInstructions);
	}

	/**
	 * 
	 * @param container
	 *            the context in which GUI components are created and rendered
	 */
	protected void update(GameContainer container, Player currentPlayer) {
		// Check if user has to write its instructions
		if (!inputCorrect && !setForCurrentRequest) {
			textField.setFocus(true);
			// get sent behavior instructions on an enter_key press
			if (this.textField.hasFocus()) {
				if (container.getInput().isKeyDown(Input.KEY_ENTER)) {
					receivedString = textField.getText();
					setForCurrentRequest = true;
					this.textField.setFocus(false);
					System.out.println("> " + receivedString);
					receivedSequence = Reader.parse(receivedString);
					corrector.set(currentPlayer.getBesace(), receivedSequence);
					inputCorrect = corrector.getCorrectness();
				}
			}
			if (inputCorrect) {
				inputUpToDate = true;
				inputCorrect = false;
				setForCurrentRequest = false;
			}
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

		// If the given behavior isn't correct, show the correction
		if (!inputCorrect) {
			corrector.drawCorrectedList(g, userInterface.getWindowWidth(), userInterface.getWindowHeight());
		}
	}

	public _Sequence getReceivedSequence() {
		return receivedSequence;
	}

	public boolean getUpdateness() {
		return inputUpToDate;
	}

	public void setUpdateness(boolean b) {
		inputUpToDate = b;
	}

}