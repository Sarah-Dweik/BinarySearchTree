package finalTrial;

import firstTrial.StackQueue;
import firstTrial.tNode;

public class BST<T extends Comparable<T>> {

	Tnode<T> root; // point at null at first

	// Add to the tree (A binary tree)

	// helper method
	public void insert(T data) {
		Tnode newnode = new Tnode(data);
		if (root == null) {
			root = newnode;
		} else {
			insert(root, data);
		}
	}

	// recursive method
	public void insert(Tnode node, T data) {
		if ((node.getLeft() == null && node.getData().compareTo(data) > 0)) {
			Tnode newnode = new Tnode(data);
			node.setLeft(newnode);
		}
		if (node.getRight() == null && node.getData().compareTo(data) < 0) {
			Tnode newnode = new Tnode(data);
			node.setRight(newnode);
		}
		if (node.getData().compareTo(data) > 0 && node.getLeft() != null) {
			insert(node.getLeft(), data);
		}
		if (node.getData().compareTo(data) < 0 && node.getRight() != null) {
			insert(node.getRight(), data);
		}
	}

	public void traverseInOrder(Tnode node) {
		if (node != null) {
			traverseInOrder(node.getLeft());
			System.out.println(node + " ");
			traverseInOrder(node.getRight());
		}
	}

	public void traverseInOrderNoRecursion(Tnode root) {
		LStack stack = new LStack(); // linkedlist stack
		Tnode curr = root;
		while (curr != null || !stack.isEmpty()) {
			while (curr != null) {
				stack.push((Comparable) curr);
				curr = curr.getLeft();
			}

			// now pop the element where their order going to be from left most and up
			T element = (T) stack.pop();
			System.out.println(element);

			// resent curr to the right
			// curr= root.getRight();
			// start from the right of the left most
			curr = curr.getRight();
		}
	}

	// node left right
	public void traversePreOrderNoRec(Tnode root) {
		LStack stack = new LStack();
		
		stack.push((Comparable) root);
		while(!stack.isEmpty()) {
			Tnode curr =  (Tnode) stack.pop();
			System.out.println(curr.getData());
			
			if(curr.hasRight()) {
				stack.push((Comparable) curr.getRight());
			}
			
			if(curr.hasLeft()) {
				stack.push((Comparable) curr.getLeft());
			}
			
		}
	}
	
	//left right node 
	public void traversePostOrderNoRec(Tnode root) {
		LStack preorderstack = new LStack();
		LStack postorderstack = new LStack();
		
		//apply the pre order  node left right
		preorderstack.push((Comparable) root);
		while(!preorderstack.isEmpty()) {
			Tnode curr = (Tnode) preorderstack.pop();
			postorderstack.push((Comparable) curr);
			
			if(curr.hasRight()) {
				preorderstack.push((Comparable) curr.getRight());
			}
			
			if(curr.hasLeft()) {
				preorderstack.push((Comparable) curr.getLeft());
			}
		}
		// pop form the second stack to get the order reversed 
		while(!postorderstack.isEmpty()) {
			Tnode curr = (Tnode) postorderstack.pop();
			System.out.println(curr.getData());
		}
	}

	// delete Node iteratively
	public Tnode delete(T data) {
		Tnode curr = root;
		Tnode parent = root;
		boolean isLeftChild = false;
		if (root == null) {
			return null; // tree is empty
		}

		// find the node to del and them check which case and apply
		while (curr != null && !curr.getData().equals(data)) {
			parent = curr;
			if (data.compareTo((T) curr.getData()) < 0) {
				curr = curr.getLeft();
				isLeftChild = true;
			} else {
				curr = curr.getRight();
				isLeftChild = false;
			}
			if (curr == null) {
				return null; // the element to del was not found
			}
		}

		//case1: leaf
		if (!curr.hasLeft() && !curr.hasRight()) {
			if (curr == root) {
				root = null;
			} else {
				if (isLeftChild) {
					parent.setLeft(null);
				} else {
					parent.setRight(null);
				}
			}
		}
		// case2: has 1 child
		else if (curr.hasLeft() && !curr.hasRight()) {
			if (curr == root) {
				root = curr.getLeft();
			} else if (isLeftChild) {
				parent.setLeft(curr.getLeft());
			} else {
				parent.setRight(curr.getLeft());
			}
		} else if (curr.hasRight() && !curr.hasLeft()) {
			if (curr == root) {
				root = curr.getRight();
			} else if (isLeftChild) {
				parent.setLeft(curr.getRight());
			} else {
				parent.setRight(curr.getRight());
			}
		}
		// case3: has two children
		else {
			Tnode succ = getSuccessor(curr);
			if (curr == root) {
				root = succ;
			} else if (isLeftChild) {
				parent.setLeft(succ);
			} else {
				parent.setRight(succ);
			}
			succ.setLeft(curr.getLeft());
		}

		return curr;
	}

	// succesor
	public Tnode getSuccessor(Tnode node) {
		Tnode parentSucc = node;
		Tnode succ = node;
		Tnode curr = node.getRight();
		while (curr != null) {
			parentSucc = succ;
			succ = curr;
			curr = curr.getLeft();
		}
		if (succ != node.getRight()) {
			parentSucc.setLeft(succ.getRight());
			succ.setRight(node.getRight());
		}
		return succ;
	}

	public void traversePreOrder(Tnode node) {
		if (node != null) {
			System.out.println(node.getData());

			if (node.getData() instanceof BST) {
				// Assuming traversePreOrder is a method in your BST class
				((BST) node.getData()).traversePreOrder(((BST) node.getData()).root);
			}

			traversePreOrder(node.getLeft());
			traversePreOrder(node.getRight());
		}
	}

	// left right node
	public void traversePostOrder(Tnode node) {
		if (node != null) {
			traversePostOrder(node.getLeft());
			traversePostOrder(node.getRight());
			System.out.println(node);
		}
	}

	public int size() {
		return (size(root));
	}

	public int size(Tnode node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = size(node.getLeft());
		if (node.hasRight())
			right = size(node.getRight());
		return left + right + 1;
	}

	public int height() {
		return (height(root));
	}

	public int height(Tnode node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = height(node.getLeft());
		if (node.hasRight())
			right = height(node.getRight());
		if (left > right) {
			return left + 1;
		} else
			return right + 1;

	}

	public Tnode findMin(Tnode node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	public Tnode findMinRec(Tnode node) {
		if (node != null) {
			if (node.hasLeft()) {
				return findMinRec(node.getLeft());
			}
			return node;
		}
		return null; // node does not exist
	}

	public Tnode findMax(Tnode node) {
		while (node.getRight() != null) {
			node = node.getRight();
		}
		return node;
	}

	public Tnode findMaxRec(Tnode node) {
		if (node != null) {
			if (node.hasRight()) {
				return findMaxRec(node.getRight());
			}
			return node;
		}

		return null;
	}

	public T find(T data) {
		if (root != null) {
			return find(root, data);
		}
		return null; // tree is not created
	}

	public T find(Tnode node, T data) {
		if (node != null) {
			if (node.getData().compareTo(data) == 0) {
				return (T) node.getData(); // Return the data when found
			} else if (node.getData().compareTo(data) > 0 && node.hasLeft()) {
				return find(node.getLeft(), data);
			} else if (node.getData().compareTo(data) < 0 && node.hasRight()) {
				return find(node.getRight(), data);
			}
		}
		return null; // was not found
	}


	// either has two children or (leaf)
	public boolean isFull(tNode node) {
		if (node == null) {
			return true; // it is empty
		}
		if (node.hasLeft() && !node.hasRight()) {
			return false;
		}
		if (node.hasRight() && !node.hasLeft()) {
			return false;
		} else
			return isFull(node.getLeft()) && isFull(node.getRight());

	}
	
	public void levelTraversalFinal() {
		StackQueue qu = new StackQueue();
		qu.enqueue((Comparable) root);
		while(!qu.isEmpty()) {
			Tnode t = (Tnode) qu.dequeue();
			System.out.println(t.getData());
			if(t.hasLeft()) {
				qu.enqueue((Comparable) t.getLeft());
			}
			if(t.hasRight()) {
				qu.enqueue((Comparable) t.getRight());
			}
		}
	}
	
	//the level traversal concept is important in figure out of a BST compleet or not 
	//in any level if a leaf is found and then it has one child after it then the tree is not complete 
	public boolean isComplete() {
		StackQueue qu = new StackQueue();
		qu.enqueue((Comparable) root);
		//you must use this to identify when left is allowed to empty and when it is not
		boolean allow = false ; //default and it become true when node does not has left
		while(!qu.isEmpty()) {
			Tnode t = (Tnode) qu.dequeue();
			
			if (t.hasLeft()) {
				if(allow==true) return false;
				qu.enqueue((Comparable) t.getLeft());
			}
			else {
				 allow =true; //means that one child is missing so ir is not complete
			}
			if(t.hasRight()) {
				if(allow==true)return false;
				qu.enqueue((Comparable) t.getRight());
			}
			else 
				allow = true;
		}
		return true; //if no false statments were returned 
	}
	
	public boolean isFull() {
		StackQueue qu = new StackQueue();
		qu.enqueue((Comparable) root);
		while(!qu.isEmpty()) {
			Tnode t = (Tnode) qu.dequeue();
			
			if (t.hasLeft()) {
				qu.enqueue((Comparable) t.getLeft());
			}
			else {
				return false; //means that one child is missing so ir is not complete
			}
			if(t.hasRight()) {
				qu.enqueue((Comparable) t.getRight());
			}
			else 
				return false;
		}
		return true; //if no false statments were returned 
	}
	
	public int CountNodes() {
		StackQueue qu = new StackQueue();
		qu.enqueue((Comparable) root);
		int count = 0;
		while(!qu.isEmpty()) {
			Tnode t = (Tnode) qu.dequeue();
			
			if (t.hasLeft()) {
				qu.enqueue((Comparable) t.getLeft());
			}
			
			if(t.hasRight()) {
				qu.enqueue((Comparable) t.getRight());
			}
			count ++ ;
		}
		return count; //if no false statments were returned 
	}
	
	public int countLevels() {
		int levels = 0;
		StackQueue qu = new StackQueue();
		qu.enqueue((Comparable) root);
		int currentLevelNode=1;
		
		while(!qu.isEmpty()) {
			int newlevelnode = 0;
			
			while(currentLevelNode>0) {
				Tnode t = (Tnode) qu.dequeue();
				currentLevelNode--;
				
				if(t.hasLeft()) {
					qu.enqueue((Comparable) t.getLeft());
					newlevelnode++;
				}
				
				if(t.hasRight()) {
					qu.enqueue((Comparable) t.getRight());
					newlevelnode++;
				}
				
			}
			levels++;
			currentLevelNode=newlevelnode;
		}
		return levels;
	}
	

	// level traversal using stack or queue
	public void levelTraversal() {
		StackQueue qu = new StackQueue();
		if (root != null) {
			levelTraversal(root, height(), qu);
			printPop(qu);
		}
	}

	public void printPop(StackQueue qu) {
		while (!qu.isEmpty()) {
			System.out.println(qu.dequeue());
		}
	}

	public void levelTraversal(Tnode node, int h, StackQueue qu) {
		if (h == 1) {
			qu.enqueue(node.getData());
		}

		else {

			if (node != null && node.hasLeft()) {
				qu.enqueue(node.getData());
				levelTraversal(node.getLeft(), h - 1, qu);// the recursion call is befor the addition to avoid
															// repetition

			}
			if (node != null && node.hasRight()) {
				qu.enqueue(node.getData());
				levelTraversal(node.getRight(), h - 1, qu);

			}
		}
	}

}
