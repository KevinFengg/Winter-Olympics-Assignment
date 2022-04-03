
public class TreeBuilder<T> {

	public LinkedBinaryTree<T> buildTree (T[] data) {
		LinkedQueue<T> dataQueue = new LinkedQueue<T>();
		LinkedQueue<BinaryTreeNode<T>> parentQueue = new LinkedQueue<BinaryTreeNode<T>>();
		BinaryTreeNode<T> parent;
		
		//Enqueueing all elements of data into dataQueue
		for (int i = 0; i < data.length; i ++) {
			dataQueue.enqueue(data[i]);
		}
		
		/*
		 * Creating a linked binary tree and enqueueing the root node onto
		 * parentQueue. dataQueue needs to be dequeue so that the same node
		 * is not added onto the tree twice.
		 */
		LinkedBinaryTree<T> bTree = new LinkedBinaryTree<T>(dataQueue.first());
		parentQueue.enqueue(bTree.getRoot());
		dataQueue.dequeue();
		
		while (!dataQueue.isEmpty()) {
			BinaryTreeNode<T> a = new BinaryTreeNode<T>(dataQueue.dequeue());
			BinaryTreeNode<T> b = new BinaryTreeNode<T>(dataQueue.dequeue());
			parent = parentQueue.dequeue();
			
			/*
			 * Determining if a or b are null. ".getData() == null" is used to
			 * find null objects in TestTreeBuilder and ".getData().toString().contains("null")"
			 * is used to determine if the string representation of .getData() has
			 * the word "null" in it for TestSki. 
			 */
			if (a.getData() != null) {
				if (!a.getData().toString().contains("null")) {
					parent.setLeft(a);
					parentQueue.enqueue(a);
				}
			}
			
			if (b.getData() != null) {
				if (!b.getData().toString().contains("null")) {
					parent.setRight(b);
					parentQueue.enqueue(b);
				}
			}
		}
		
		return bTree;
	}
}
