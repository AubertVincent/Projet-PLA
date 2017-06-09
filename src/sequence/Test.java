package sequence;
 
import reader.Reader;

public class Test {
	
	public static void main(String[] args) {
		Reader.parse("((MC2E | RM) | (AC;(MC3W>MT8.3)))");
	}
	
	public void testos() {
		Reader.parse("(MC2E | (AC;(MC3N>MT8.3)))");
	}
	
}
