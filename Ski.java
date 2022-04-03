
public class Ski {

	private BinaryTreeNode<SkiSegment> root;
	
	
	public Ski(String[] data) {
		SkiSegment[] segments = new SkiSegment[data.length];
		
		for (int i = 0; i < data.length; i ++) {
			if (data[i] == "") {
				segments[i] = new SkiSegment(String.valueOf(i), data[i]);
			}
			else if (data[i] != null && data[i].contains("jump")) {
				segments[i] = new JumpSegment(String.valueOf(i), data[i]);
				segments[i].getClass();
			}
			else if (data[i] != null && data[i].contains("slalom")) {
				segments[i] = new SlalomSegment(String.valueOf(i), data[i]);
			}
			else {
				segments[i] = new SkiSegment(String.valueOf(i), data[i]);
			}
		}
		TreeBuilder<SkiSegment> treeBuild = new TreeBuilder<SkiSegment>();
		LinkedBinaryTree<SkiSegment> builtTree = treeBuild.buildTree(segments);
		root = builtTree.getRoot();
	}
	
	
	public BinaryTreeNode<SkiSegment> getRoot() {
		return root;
	}

	
	public void skiNextSegment(BinaryTreeNode<SkiSegment> node, ArrayUnorderedList<SkiSegment> sequence) {
		sequence.addToRear(node.getData());

		//Base case. A null left and right child node signifies a leaf.
		if (node.getLeft() == null && node.getRight() == null) {}
		
		//Recursive case.
		else {
			node = bestNode(node);
			skiNextSegment(node, sequence);
		}
	}
	
	
	/*
	 * This private helper class is responsible for determining the best child node
	 * given a parent node. It works by determining if any of the string representations
	 * of the node include words that reveal their type like "jump" or "slalom" and
	 * then casting the nodes to their respective types accordingly so I can invoke
	 * methods like ".getID", ".getDirection" and ".getHeight" and determine which
	 * node to take.
	 * I understand that this helper method could have been done a less messier way. For
	 * example, I could have had if and else-if statements in skiNextSegment each
	 * representing methods that take in BinaryTreeNode types <SkiSegment>, <SlalomSegment>,
	 * and <JumpSegment> (So casting and invoking ".toString()" is not required), but
	 * I did it this way anyways.
	 */
	private BinaryTreeNode<SkiSegment> bestNode(BinaryTreeNode<SkiSegment> node) {
		BinaryTreeNode<SkiSegment> leftChild = node.getLeft();
		BinaryTreeNode<SkiSegment> rightChild = node.getRight();
		
		//Checking if one of the nodes are null as nothing can be invoked on a null node.
		if (leftChild == null && rightChild != null) {
			return rightChild;
		}
		else if (leftChild != null && rightChild == null) {
			return leftChild;
		}
		
		//Determining if any jumps exist.
		else if (leftChild.toString().contains("jump") && rightChild.toString().contains("jump")) {
			JumpSegment leftSeg = (JumpSegment) leftChild.getData();
			JumpSegment rightSeg = (JumpSegment) rightChild.getData();
			if (leftSeg.getHeight() > rightSeg.getHeight()) {
				return leftChild;
			}
			else if (leftSeg.getHeight() <= rightSeg.getHeight()) {
				return rightChild;
			}
		}
		else if (leftChild.toString().contains("jump") && !rightChild.toString().contains("jump")) {
			return leftChild;
		}
		else if (!leftChild.toString().contains("jump") && rightChild.toString().contains("jump")) {
			return rightChild;
		}
		
		//Determining if any slaloms exist.
		else if (leftChild.toString().contains("slalom") && rightChild.toString().contains("slalom")) {
			SlalomSegment segLeft = (SlalomSegment) leftChild.getData();
			if (segLeft.getDirection().equals("L")) {
				return leftChild;
			}
			else {
				return rightChild;
			}
		}
		else if (leftChild.toString().contains("slalom") && !rightChild.toString().contains("slalom")) {
			SlalomSegment segLeft1 = (SlalomSegment) leftChild.getData();
			if (segLeft1.getDirection() == "L") {
				return leftChild;
			}
			else {
				return rightChild;
			}
		}
		else if (!leftChild.toString().contains("slalom") && rightChild.toString().contains("slalom")) {
			SlalomSegment segRight1 = (SlalomSegment) rightChild.getData();
			if (segRight1.getDirection() == "L") {
				return rightChild;
			}
			else {
				return leftChild;
			}
		}
		
		//If all if & else-if statements fail, the only possible case left is 2 regular segments.
		return rightChild;
	}
}
