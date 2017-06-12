package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import operateur.Action;
import personnages.Besace;
import personnages.EmptyLeaf;
import sequence.EmptyRootTree;
import sequence.IncompleteTree;
import sequence.Tree;
import sequence._IncompleteSequence;
import sequence._Sequence;
import util.Correct;
import util.Pair;

public class SequenceCorrector {

	List<Pair<? extends _Sequence, Correct>> correctedList;
	boolean correct;

	public static List<Pair<? extends _Sequence, Correct>> correct(Besace besace, _Sequence seq) {
		_IncompleteSequence incSeq;
		try {
			incSeq = produceIncompleteSequence(besace, seq);
			return sequencesToCorrectedList(seq, incSeq);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static _IncompleteSequence produceIncompleteSequence(Besace besace, _Sequence seq)
			throws CloneNotSupportedException {
		return produceIncompleteSequenceAux(besace, seq).getFirst();
	}

	// FIXME : see execution of Test class
	// It produces an incorrect incSeq : at Emptyleaf & EmptyRootTree, it puts
	// old leaf and tree
	private static Pair<_IncompleteSequence, Besace> produceIncompleteSequenceAux(Besace besace, _Sequence seq)
			throws CloneNotSupportedException {
		// Will be used as a "current besace state" during tree exploration
		Besace besaceCopy = new Besace(besace);

		// Base case
		if (seq.isAction()) {
			Action seqAct = (Action) seq;
			// Available leaf case
			if (besaceCopy.containsKey(seqAct.getPickable()) && (besaceCopy.get(seqAct.getPickable()) > 0)) {
				besaceCopy.remove(seqAct.getPickable());
				return new Pair<_IncompleteSequence, Besace>((_IncompleteSequence) seq, besaceCopy);
			}
			// Unavailable leaf case
			else {
				return new Pair<_IncompleteSequence, Besace>((_IncompleteSequence) new EmptyLeaf(), besaceCopy);
			}
		}
		// Recursive case
		else if (seq.isTree()) {
			Tree tree = (Tree) seq;
			// Available op case
			if (besaceCopy.containsKey(tree.getOpPickable()) && (besaceCopy.get(tree.getOpPickable()) > 0)) {
				besaceCopy.remove(tree.getOpPickable());

				Pair<_IncompleteSequence, Besace> leftResultingPair = produceIncompleteSequenceAux(besaceCopy,
						tree.getLeft());
				besaceCopy = leftResultingPair.getSecond();
				_IncompleteSequence leftSeqInc = leftResultingPair.getFirst();

				Pair<_IncompleteSequence, Besace> rightResultingPair = produceIncompleteSequenceAux(besaceCopy,
						tree.getRight());
				besaceCopy = rightResultingPair.getSecond();
				_IncompleteSequence rightSeqInc = rightResultingPair.getFirst();

				return new Pair<_IncompleteSequence, Besace>(new IncompleteTree(tree.getOp(), leftSeqInc, rightSeqInc),
						besaceCopy);
			}
			// Unavailable op case
			else {

				Pair<_IncompleteSequence, Besace> leftResultingPair = produceIncompleteSequenceAux(besaceCopy,
						tree.getLeft());
				besaceCopy = leftResultingPair.getSecond();
				_IncompleteSequence leftSeqInc = leftResultingPair.getFirst();

				Pair<_IncompleteSequence, Besace> rightResultingPair = produceIncompleteSequenceAux(besaceCopy,
						tree.getRight());
				besaceCopy = rightResultingPair.getSecond();
				_IncompleteSequence rightSeqInc = leftResultingPair.getFirst();

				return new Pair<_IncompleteSequence, Besace>(new EmptyRootTree(leftSeqInc, rightSeqInc), besaceCopy);
			}
		}
		// Other case -> Exception
		else {
			try {
				throw new Exception("Unimplemented _Sequence subclass " + seq.getClass().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static List<Pair<? extends _Sequence, Correct>> sequencesToCorrectedList(_Sequence seq,
			_IncompleteSequence incSeq) {
		List<Pair<? extends _Sequence, Correct>> resultingList = new ArrayList<Pair<? extends _Sequence, Correct>>();
		resultingList = treeToCorrectedListAux(resultingList, seq, incSeq);
		return resultingList;
	}

	private static List<Pair<? extends _Sequence, Correct>> treeToCorrectedListAux(
			List<Pair<? extends _Sequence, Correct>> list, _Sequence seq, _IncompleteSequence incSeq) {
		// Base case
		if (seq.isAction()) {
			// Unavailable leaf case
			if (incSeq.isEmptyLeaf()) {
				list.add(new Pair<_Sequence, Correct>(seq, Correct.INCORRECT));
			}
			// Normal leaf case
			else {
				list.add(new Pair<_Sequence, Correct>(seq, Correct.CORRECT));
			}
		}
		// Recursive case
		else if (seq.isTree()) {
			Tree tree = (Tree) seq;
			// Unavailable root case
			if (incSeq.isEmptyRootTree()) {
				EmptyRootTree incTree = (EmptyRootTree) incSeq;
				list = treeToCorrectedListAux(list, tree.getLeft(), incTree.getLeft());
				list.add(new Pair<_Sequence, Correct>(seq, Correct.INCORRECT));
				list = treeToCorrectedListAux(list, tree.getRight(), incTree.getRight());
			}
			// Normal root case
			else {
				IncompleteTree incTree = (IncompleteTree) incSeq;
				list = treeToCorrectedListAux(list, tree.getLeft(), incTree.getLeft());
				list.add(new Pair<_Sequence, Correct>(seq, Correct.CORRECT));
				list = treeToCorrectedListAux(list, tree.getRight(), incTree.getRight());
			}
		}
		// Other case -> Exception
		else {
			try {
				throw new Exception("Unimplemented _Sequence subclass " + seq.getClass().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public void drawCorrectedList(Graphics g, int x, int y) {
		List<Pair<? extends _Sequence, Correct>> list = correctedList;
		for (Iterator<Pair<? extends _Sequence, Correct>> itr = list.iterator(); itr.hasNext();) {
			Pair<? extends _Sequence, Correct> currentPair = itr.next();
			Correct currentEltCorrectness = currentPair.getSecond();
			_Sequence currentSeq = currentPair.getFirst();
			switch (currentEltCorrectness) {
			case CORRECT:
				g.setColor(Color.green);
				break;
			case INCORRECT:
				g.setColor(Color.red);
				break;
			}
			if (currentSeq instanceof Action) {
				g.drawString(currentSeq.toString(), x, y);
				x += 40;
			} else if (currentSeq instanceof Tree) {
				g.drawString(((Tree) currentSeq).getOp().toString(), x, y);
				x += 10;
			}

		}
	}

	// ↓ Getters and setters ↓

	public void set(Besace besace, _Sequence receivedSequence) {
		correctedList = correct(besace, receivedSequence);
		correct = isCorrect(correctedList);
	}

	private static boolean isCorrect(List<Pair<? extends _Sequence, Correct>> correctedList2) {
		for (Iterator<Pair<? extends _Sequence, Correct>> itr = correctedList2.iterator(); itr.hasNext();) {
			Pair<? extends _Sequence, Correct> currentPair = itr.next();
			// The sequence isn't correct if one of the correctnesses in the
			// list is INCORRECT
			if (currentPair.getSecond() == Correct.INCORRECT) {
				return false;
			}
		}
		return true;
	}

	public boolean getCorrectness() {
		return isCorrect(correctedList);
	}

}
