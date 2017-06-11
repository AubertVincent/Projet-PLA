package test;

import java.util.ArrayList;
import java.util.List;

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

	public static List<Pair<? extends _Sequence, Correct>> correct(Besace besace, _Sequence seq) {
		_IncompleteSequence incSeq;
		try {
			incSeq = produceIncompleteSequence(besace, seq);
			return sequencesToCorrectedList(seq, incSeq);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static _IncompleteSequence produceIncompleteSequence(Besace besace, _Sequence seq)
			throws CloneNotSupportedException {
		// Will be used as a "current besace state" during tree exploration
		Besace besaceCopy = (Besace) besace.clone();

		// Base case
		if (seq.isAction()) {
			Action seqAct = (Action) seq;
			// Available leaf case
			if (besaceCopy.containsKey(seqAct.getPickable()) && besaceCopy.get(seqAct.getPickable()) > 0) {
				besaceCopy.remove(seqAct.getPickable());
				return (_IncompleteSequence) seq;
			}
			// Unavailable leaf case
			else {
				return new EmptyLeaf();
			}
		}
		// Recursive case
		else if (seq.isTree()) {
			Tree tree = (Tree) seq;
			// Available op case
			if (besaceCopy.containsKey(tree.getOpPickable()) && besaceCopy.get(tree.getOpPickable()) > 0) {
				besaceCopy.remove(tree.getOpPickable());
				return new IncompleteTree(tree.getOp(), produceIncompleteSequence(besaceCopy, tree.getLeft()),
						produceIncompleteSequence(besaceCopy, tree.getRight()));
			}
			// Unavailable op case
			else {
				return new EmptyRootTree(produceIncompleteSequence(besaceCopy, tree.getLeft()),
						produceIncompleteSequence(besaceCopy, tree.getRight()));
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

}
