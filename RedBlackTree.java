package proj3;
/**
 * This class contains all the methods the RedBlackTree will use. It
 * also contains a nested class RedBlackNode. Most of this code I have
 * taken from the textbook or the online source code for the textbook. 
 * @author Nicholas Kim
 * @param <AnyType>
 * date: 4/7/14
 * section: 03
 */
public class RedBlackTree<AnyType extends Comparable<? super AnyType>> {
	// Initializing variables...
	private RedBlackNode<AnyType> header;
	private RedBlackNode<AnyType> nullNode;
	private RedBlackNode<AnyType> current;
	private RedBlackNode<AnyType> parent;
	private RedBlackNode<AnyType> grand;
	private RedBlackNode<AnyType> great;

	private static final int BLACK = 1; // BLACK must be 1
	private static final int RED = 0;
	/**
	 * This constructor sets up all the pointer for the root node.
	 */
	public RedBlackTree() {
		nullNode = new RedBlackNode<>(null);
		nullNode.left = nullNode.right = nullNode;
		header = new RedBlackNode<>(null);
		header.left = header.right = nullNode;
	}
	/**
	 * This nested class controls the nodes of the RedBlackTree. 
	 * @author Nicholas Kim
	 * @param <AnyType>
	 * date: 4/7/14
	 * section: 03
	 */
	private static class RedBlackNode<AnyType> {
		/**
		 * This constructor calls the other constructor to
		 * initialize the pointers of the node.
		 * @param theElement the element that this node will hold.
		 */
		RedBlackNode(AnyType theElement) {
			this(theElement, null, null);
		}
		/**
		 * This constructor is called my the main constructor.
		 * @param The element the element that the node holds.
		 * @param lt The left pointer of the node.
		 * @param rt The right pointer of the node.
		 */
		RedBlackNode(AnyType theElement, RedBlackNode<AnyType> lt,
				RedBlackNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
			color = RedBlackTree.BLACK;
		}

		AnyType element; // The data in the node
		RedBlackNode<AnyType> left; // Left child
		RedBlackNode<AnyType> right; // Right child
		int color; // Color
	}
	/**
	 * This method handles the Reorienting of the tree after insertion as happened.
	 * This method relies on rotate to reorient the tree. This method was mostly taken
	 * from the textbook.
	 * @param item The item that was inserted into the tree.
	 */
	private void handleReorient(AnyType item) {
		// Do the color flip
		current.color = RED;
		current.left.color = BLACK;
		current.right.color = BLACK;

		if (parent.color == RED) // Have to rotate
		{
			grand.color = RED;
			if ((item.compareTo(grand.element) < 0) != (item
					.compareTo(parent.element) < 0))
				parent = rotate(item, grand); // Start dbl rotate
			current = rotate(item, great);
			current.color = BLACK;
		}
		header.right.color = BLACK; // Make root black
	}
	/**
	 * This method inserts the element item into the tree. This method uses other methods
	 * in the class to insure the format of the red and black tree.
	 * @param item The object you want to insert into the tree.
	 */
	public void insert(AnyType item) {
		current = parent = grand = header;
		nullNode.element = item;

		while (compare(item, current) != 0) {
			great = grand;
			grand = parent;
			parent = current;
			current = compare(item, current) < 0 ? current.left : current.right;
			// Check if two red children; fix if so
			if (current.left.color == RED && current.right.color == RED)
				handleReorient(item);
		}

		// Insertion fails if already present
		if (current != nullNode) {
			return;
		}
		current = new RedBlackNode<>(item, nullNode, nullNode);

		// Attach to parent
		if (compare(item, parent) < 0)
			parent.left = current;
		else
			parent.right = current;
		handleReorient(item);
	}
	/**
	 * This method is used when inserting objects into the tree. This is taken
	 * mostly from the book.
	 * @param k2 The object you will rotate.
	 * @return The rotated child.
	 */
	private RedBlackNode<AnyType> rotateWithLeftChild(RedBlackNode<AnyType> k2) {
		RedBlackNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}
	/**
	 * This method is used when inserting objects into the tree. This is taken
	 * mostly from the book.
	 * @param k2 The object you will rotate.
	 * @return The rotated child.
	 */
	static RedBlackNode rotateWithRightChild(RedBlackNode k1) {
		RedBlackNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}
	/**
	 * This method compares the object item with one of the nodes of the tree.
	 * @param item An object you want to compare t with.
	 * @param t The node of the tree you want to compare.
	 * @return The compareTo result of item and t
	 */
	private final int compare(AnyType item, RedBlackNode<AnyType> t) {
		if (t == header)
			return 1;
		else
			return item.compareTo(t.element);
	}
	/**
	 * This method is used in the insert method. Mainly used to keep the format of the
	 * Red and Black tree.
	 * @param item The element you want to rotate.
	 * @param parent the parent of item.
	 * @return Rotated node.
	 */
	private RedBlackNode<AnyType> rotate(AnyType item,
			RedBlackNode<AnyType> parent) {
		if (compare(item, parent) < 0)
			return parent.left = compare(item, parent.left) < 0 ? rotateWithLeftChild(parent.left)
					: // LL
					rotateWithRightChild(parent.left); // LR
		else
			return parent.right = compare(item, parent.right) < 0 ? rotateWithLeftChild(parent.right)
					: // RL
					rotateWithRightChild(parent.right); // RR
	}
	/**
	 * This method calls the other printTree method with the root as its parameter.
	 */
	public void printTree() {
		printTree(this.header.right);
	}
	/**
	 * This method prints the elements in the RedBlackTree in infix order.
	 * @param x the node you want to start to transverse down.
	 */
	private void printTree(RedBlackNode<AnyType> x) {
		if (x != nullNode) {
			printTree(x.left);
			System.out.println(x.element.toString() + "\n");
			printTree(x.right);
		}
	}
	/**
	 * This method prints the root of the tree.
	 */
	public void printRoot() {
		if (header.right == nullNode || header.right.element == null) {
			System.out.println("This RBT is empty!");
		} else {
			System.out.println(header.right.element.toString());
		}
	}
	/**
	 * This method finds Object x in the tree and returns it if it is found.
	 * Otherwise it returns null.
	 * @param x The object you are looking for.
	 * @return null if x can not be found or returns the node if found.
	 */
	public AnyType retreiveIfItContains(AnyType x) {
		nullNode.element = x;
		current = header.right;

		for (;;) {
			if (x.compareTo(current.element) < 0)
				current = current.left;
			else if (x.compareTo(current.element) > 0)
				current = current.right;
			else if (current != nullNode)
				return current.element;
			else
				return null;
		}
	}

}
