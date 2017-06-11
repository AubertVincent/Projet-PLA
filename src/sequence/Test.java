package sequence;

import java.util.ArrayList;
import java.util.List;

import entite.Direction;
import operateur.ClassicAck;
import operateur.MoveDir;
import operateur.Priority;
import util.Correct;
import util.Pair;

public class Test {

	public static void main(String[] args) {
		testos();
	}

	public static void testos() {
		_Sequence seq = new Tree(new Priority(),
				new Tree(new Priority(), new MoveDir(Direction.NORTH, 5), new ClassicAck()),
				new Tree(new Priority(), new MoveDir(Direction.NORTH, 7), new ClassicAck()));

		_IncompleteSequence incSeq = new IncompleteTree(new Priority(),
				new IncompleteTree(new Priority(), (_IncompleteSequence) new MoveDir(Direction.NORTH, 5),
						(_IncompleteSequence) new ClassicAck()),
				new EmptyRootTree(new MoveDir(Direction.NORTH, 7), new ClassicAck()));

		System.out.println(seq.toString());
		System.out.println(incSeq.toString());
		List<Pair<? extends _Sequence, Correct>> maListos = treeToCorrectedList(seq, incSeq);
		System.out.println(maListos);

	}

	private static List<Pair<? extends _Sequence, Correct>> treeToCorrectedList(_Sequence seq,
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
				// return "RED" + seq.toString() + "End(RED)";
			}
			// Normal leaf case
			else {
				list.add(new Pair<_Sequence, Correct>(seq, Correct.CORRECT));
				// return seq.toString();
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
				// return "RED" + tree.getOp().toString() + "End(RED)" + "["
				// + toStringCorrected(tree.getLeft(), incTree.getLeft()) + ","
				// + toStringCorrected(tree.getRight(), incTree.getRight()) +
				// "]";
			}
			// Normal root case
			else {
				IncompleteTree incTree = (IncompleteTree) incSeq;
				list = treeToCorrectedListAux(list, tree.getLeft(), incTree.getLeft());
				list.add(new Pair<_Sequence, Correct>(seq, Correct.CORRECT));
				list = treeToCorrectedListAux(list, tree.getRight(), incTree.getRight());
				// return tree.getOp().toString() + "[" +
				// toStringCorrected(tree.getLeft(), incTree.getLeft()) + ","
				// + toStringCorrected(tree.getRight(), incTree.getRight()) +
				// "]";
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
