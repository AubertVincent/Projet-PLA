package reader;
import operateur.*;

public interface Sequence {

	public boolean isNil();
	public boolean isAction();
	public boolean isTree();
	
	public Sequence s();
	/*
	Operator noeud;
	Sequence fg;
	Sequence fd;

	public Sequence() {
		noeud = null;
		fg = null;
		fd = null;
	}

	public Sequence(Action noeud) {
		this.noeud = noeud;
		fg = null;
		fd = null;
	}

	public Sequence(Behavior noeud, Sequence fg, Sequence fd) {
		this.noeud = noeud;
		this.fg = fg;
		this.fd = fd;
	}*/

}
