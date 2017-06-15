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

	private static final String defaultInstructions = "(MC2S;MR2)";

	private GUI userInterface;
	private TextField textField;
	int textFieldHeight;

	private Color backgroundFieldColor = new Color(0f, 0f, 0f, 0.7f);

	private String receivedString;

	private _Sequence receivedSequence;
	private SequenceCorrector corrector = new SequenceCorrector();

	private boolean inputUpToDate;
	private boolean inputCorrect;

	// ↓ Constructor, update and render ↓

	/**
	 * 
	 * @param container
	 *            The context in which GUI components are created and rendered
	 * @param userInterface
	 *            The GUI from which components are retrieved
	 * @param instructions
	 *            Robot's instruction example
	 */
	protected GUIBehaviorInput(GameContainer container, GUI userInterface, String instructions) {
		Font defaultfont = container.getDefaultFont();
		textFieldHeight = 50;
		textField = new TextField(container, defaultfont, 0, userInterface.getWindowHeight() - textFieldHeight,
				userInterface.getWindowWidth(), textFieldHeight);
		textField.setText(instructions);
		inputUpToDate = false;
		inputCorrect = false;
		this.userInterface = userInterface;
		inputCorrect = false;
	}

	/**
	 * Creates a behavior input user interface with default instructions
	 * 
	 * @param container
	 *            The context in which GUI components are created and rendered
	 * @param userInterface
	 *            The GUI from which components are retrieved
	 */
	protected GUIBehaviorInput(GameContainer container, GUI userInterface) {
		this(container, userInterface, defaultInstructions);
	}

	/**
	 * Updates the state of the behavior input
	 * 
	 * @param container
	 *            The context in which GUI components are updated
	 * @param currentPlayer
	 *            The owner of the robot of which behavior should be updated
	 */
	protected void update(GameContainer container, Player currentPlayer) {
		// Check if user has to write its instructions
		if (!inputCorrect) {
			textField.setFocus(true);
			// get sent behavior instructions on an enter_key press
			if (this.textField.hasFocus()) {
				if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
					receivedString = textField.getText();
					System.out.println("> " + receivedString);
					receivedSequence = Reader.parse(receivedString);
					if (receivedSequence != null) {
						System.out.println("Received : " + receivedSequence.toString());
						corrector.set(currentPlayer.getBesace(), receivedSequence);
						inputCorrect = corrector.getCorrectness();
					}
				}
			}
			if (inputCorrect) {
				inputUpToDate = true;
				inputCorrect = false;
				this.textField.setFocus(false);

			}
		}
	}

	/**
	 * Renders the behavior input interface in the given container
	 * 
	 * @param container
	 *            The context in which GUI components are rendered
	 * @param g
	 *            A graphics context used to render primitives
	 */
	protected void render(GameContainer container, Graphics g) {
		g.setColor(Color.white);
		this.textField.setBackgroundColor(backgroundFieldColor);
		this.textField.setBorderColor(backgroundFieldColor);
		this.textField.render(container, g);

		// If the given behavior isn't correct, show the correction
		if (!inputCorrect) {
			corrector.drawCorrectedList(g, 10, userInterface.getWindowHeight() - (textFieldHeight - 20));
			// corrector.drawCorrectedList(g, 0, 0);
		}
	}

	// End(Constructor, update and render)

	// ↓ Getters and setters ↓

	/**
	 * Returns the _Sequence given by the user via the behavior input
	 * 
	 * @return The _Sequence given by the user via the behavior input
	 */
	public _Sequence getReceivedSequence() {
		return receivedSequence;
	}

	/**
	 * Returns the updateness of the _Sequence retrieved via
	 * {@link gui.GUIBehaviorInput#getReceivedSequence()}
	 * 
	 * @return The updateness of the _Sequence retrieved via
	 *         {@link gui.GUIBehaviorInput#getReceivedSequence()}
	 */
	public boolean getUpdateness() {
		return inputUpToDate;
	}

	/**
	 * Sets the updateness of the _Sequence retrieved via
	 * {@link gui.GUIBehaviorInput#getReceivedSequence()} to the given boolean
	 * 
	 * @param b
	 *            The value at which the updateness of the _Sequence retrieved
	 *            via {@link gui.GUIBehaviorInput#getReceivedSequence()} should
	 *            be set
	 */
	public void setUpdateness(boolean b) {
		inputUpToDate = b;
	}

	// End(Getters and setters)
}