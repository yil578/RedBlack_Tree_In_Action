import java.util.*;

/**
 * BST_RB class that implements a Red-Black Balance BST Tree and implements the
 * BST_Interface interface.
 *
 * @author Madeline Yi-Chen, Lee <yil578@ucsd.edu>
 */
public class BST_RB<E extends Comparable <? super E>> 
    implements BST_Interface<E> {

    private Node root;
    private int size;

    /** Inner RBTree Node class **/
    private class Node {
        private E e; //the data in the Node
        private Node left; //left child pointer
        private Node right; //right child pointer
        private Node parent; //parent pointer
        private boolean red; //true: red; false: black

        /**
         * Construct a node with specified data and color
         * @param e - the data of the node
         * @param red - true if the node is red, false if black
         */
        private Node(E e, boolean red){
            this.e = e;
            this.red = red;
        }
    }

    /**
     * Constructs a new, empty binary search tree, sorted according to the
     * natural ordering of its elements.
     */
    public BST_RB() {
        //root default: null
        //size default: 0
    }

    /**
     * Constructs a new binary search tree containing the elements in the 
     * specified collection, sorted according to the natural ordering of its
     * elements.
     * @param c - collection whose elements will comprise the new BST
     * @throws ClassCastException - if the elements in c are not Comparable, or
     * are not mutually comparable
     * @throws NullPointerException - if the specified collection is null
     */
    public BST_RB(Collection<? extends E> c) {
        addAll(c);
    }
    
    /**
     * Adds the specified element to this binary search tree if it is not already
     * present.
     * @param e - element to be added to this tree
     * @return true if this tree did not already contain the specified element
     * @throws ClassCastException - if the specified object cannot be compared
     * with the elements currently in this tree
     * @throws NullPointerException - if the specified element is null and this
     * tree uses natural ordering, or its comparator does not permit null
     * elements
     */
	public boolean add(E e) {
        if (e == null)
           throw new NullPointerException();
        try {
            if (contains(e)) return false;

            //if did not already contain such element, construct a new red node
            Node newNode = new Node(e, true);
            BSTinsert(newNode); //insertion that will maintain BST order
            rbBalance(newNode); //rebalance after inserting the new node
            size ++;
            return true;
        } catch (ClassCastException cce) {
            throw cce;
        }
    }

    /**
     * Add all of the elements in the specified collection to this search tree.
     * @param  c - collection containing elements to be added to this tree
     * @return true if this tree changed as a result of the call
     * @throws ClassCastException - if the elements provided cannot be compared
     * with the elements currently in the tree
     * @throws NullPointerException - if the specified collection is null or if
     * any element is null and this tree uses natural ordering, or its comparator
     * does not permit null elements
     */
	public boolean addAll(Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException();
        try {
            boolean hasAdded = false;
            Iterator<? extends E> iter = c.iterator();
            while (iter.hasNext()) {
                //as long as one element has been successfully added
                if (add(iter.next())) //duplicates won't be added(false)
                    hasAdded = true;
            }
            return hasAdded;
        } catch (ClassCastException cce) {
            throw cce;
        }
    }

    /**
     * Removes all of the elements from this search tree.
     */
	public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Returns true if this search tree contains the specified element
     * @param  o - element to be checked for containment in this tree
     * @return true if this tree contains the specified element
     * @throws ClassCastException - if the specified object cannot be compared
     * with the elements currently in the tree
     * @throws NullPointerException - if the specified element is null and this
     * tree uses natural ordering, or its comparator does not permit null
     * elements
     */
	public boolean contains(E o) {
        if (o == null)
            throw new NullPointerException();
        try {
            return (BSTSearch(o) != null);
        } catch (ClassCastException cce) {
            throw cce;
        }
    }
    
    /**
     * Returns the first (lowest) element currently in this search tree.
     * @return the first (lowest) element currently in this tree
     * @throws NoSuchElementException - if this tree is empty
     */
	public E first() {
        if (isEmpty())
            throw new NoSuchElementException();

        //smallest element is the root's leftmost child
        Node cur = root;
        while (cur.left != null){
             cur = cur.left;
        }
        return cur.e;
    }
    
    /**
     * Returns true if this search tree contains no elements.
     * @return true if this tree contains no elements
     */
	public boolean isEmpty() {
        return (root == null);
    }

    /**
     * Returns an iterator over the elements in this search tree in ascending
     * order.
     * @return an iterator over the elements in this tree in ascending order
     */
	public Iterator<E> iterator() {
        return new BST_RBIterator();
    } 

    /**
     * Returns the last (highest) element currently in this search tree.
     * @return the last (highest) element currently in this search tree
     * @throws NoSuchElementException - if this tree is empty
     */
	public E last() {
        if (isEmpty())
            throw new NoSuchElementException();

         //largest element is the root's rightmost child
         Node cur = root;
         while (cur.right != null){
             cur = cur.right;
         }
         return cur.e;
    }

    /**
     * Removes the specified element from this search tree if it is present.
     * @param o - object to be removed from this tree, if present
     * @return true if this tree contained the specified element
     * @throws ClassCastException - if the specified object cannot be compared
     * with the elements currently in this tree
     * @throws NullPointerException - if the specified element is null and this
     * tree uses natural ordering, or its comparator does not permit null
     * elements
     */
	public boolean remove(E o) {
        if (o == null)
            throw new NullPointerException();
        try {
            Node node = BSTSearch(o);
            if (node == null) return false; //can't find the key
            else {
                rbRemoveNode(node);
                size --;
                return true;
            }
        } catch (ClassCastException cce) {
            throw cce;
        }
    }
   
    /**
     * Returns the number of elements in this search tree (its cardinality)
     * @return the number of elements in this search tree (its cardinality)
     */
	public int size() {
        return size;
    }

    /**
     * Returns the height of the search tree. An empty tree returns 0, a tree
     * with one element returns a height of 1.
     * @return the height of the search tree
     */
	public int height() {
        if (isEmpty())
            return 0;
        return heightRecur(root);
    }

    /**
     * Returns the number of children of the Node that references target.
     * @param target - the target element the node references to
     * @return the number of children of the node that references the target
     * @throws NoSuchElementException If target is not found in the tree
     * @throws IllegalArgumentException Any other problems, eg.NullPointer,
     * ClassCastException, ...
     */
	public int numChildren(E target) {
        if (target == null)
            throw new IllegalArgumentException();
        Node node;
        try {
            node = BSTSearch(target); 
        } catch (Exception e){
            throw new IllegalArgumentException();
        }
        if (node == null) //target not found
            throw new NoSuchElementException();
        else
            return numNodes(node)-1;
    }

    /**
     * Returns a string representation of the tree. 
     * @return a string representation of the tree.
     */
    @Override
    public String toString() {
       ArrayList<E> list = new ArrayList<E>();
       Iterator<E> iter = iterator();
       while (iter.hasNext()) {
           list.add(iter.next());
       }
       return list.toString();
    }

    /************** Private Helper Methods **********/

     /**
      * Return the number of nodes (including all children and itself) from a 
      * specified node. Helper for numChildren().
      * @param node - the starting node to calculate number of nodes
      * @return the number of nodes from a specified node
      */
     private int numNodes(Node node) {
         //base case when the node is null
         if (node == null)
             return 0;
         //base case when the node has no children 
         if (node.left == null && node.right == null) 
             return 1;
         //recursively adding up the nodes from left and right subtree (adding
         //an extra 1 is the node itself)
         return (numNodes(node.left) + numNodes(node.right) + 1);
    }

    /**
     * Return the height of the tree from the specified node by recursively 
     * returning the longer path between the left and right subtree of a node. 
     * Helper method for height().
     * @param node - the starting node to calculate the height from
     * @return - the height (number of nodes from  starting node to the farthest
     * leaf node, inclusive)
     */
    private int heightRecur(Node node){
        //base case when the node is null
        if (node == null) 
            return 0;
        //recursively return the longer path between the left and right subtree
        return 1 + Math.max(heightRecur(node.left), heightRecur(node.right));
    }

    /**
     * Return the node in the BST with matching key, <tt>null</tt> if cannot
     * found. Helper for contains() and RBT remove(key) and numChildren(key).
     * @param key - the key to search
     * @return the node with matching key
     */
    private Node BSTSearch(E key) {
        //search from the root
        Node cur = root;
        while (cur != null){
            if (key.compareTo(cur.e) == 0)
                return cur;
            else if (key.compareTo(cur.e) < 0)
                cur = cur.left;
            else
                cur = cur.right;
        }
        return null;
    }

    /**
     * Set the child of a parent to a specified child. Helper for
     * rbReplaceChild().
     * @param parent - the parent node
     * @param isLeftChild - true if intend to set left child, false if right child
     * @param child - the child node to be set
     */
    private void rbSetChild(Node parent, boolean isLeftChild, Node child){
        if (isLeftChild)
            parent.left = child;
        else
            parent.right = child;
        //update parent pointer 
        if (child != null) 
            child.parent = parent;
    }

    /**
     * Replace the current child of a parent with another new child.
     * Helper for rbRotate and BSTremoveNode().
     * @param parent - the parent node
     * @param curChild - the current child
     * @param newChild - the new child to replace the current child 
     */
    private void rbReplaceChild(Node parent, Node curChild, Node newChild) {
        if (parent.left == curChild)
            rbSetChild(parent, true, newChild);
        else if (parent.right == curChild) 
            rbSetChild(parent, false, newChild);
        else 
            return;
    }

    /**
     * Rotate left at the specified node. Helper for rbBalance() and
     * rbPrepForRemoval().
     * @param - the node at which the rotation is performed
     */
    private void rbRotateLeft(Node node){
        //save the left child of the node's right child
        Node rightLeftChild = node.right.left;
        if (node.parent != null) //node is not root
            //update parent's child pointer to point to the node's right child
            rbReplaceChild(node.parent, node, node.right);
        else {
            root = node.right;
            root.parent = null; //important!! (update parent pointer)
        }
        //update right child's left child to point to the node 
        rbSetChild(node.right, true, node);
        //update the node's right child to point to the right child's former
        //left child
        rbSetChild(node, false, rightLeftChild);
    }

    /**
     * Rotate right at the specified node. Helper for rbBalance() and
     * rbPrepForRemoval().
     * @param - the node at which the rotation is performed
     */
    private void rbRotateRight(Node node){
        //save the right child of the node's left child
        Node leftRightChild = node.left.right;
        if (node.parent != null)
            //update parent's child pointer to point to the node's left child
            rbReplaceChild(node.parent, node, node.left);
        else {
            root = node.left;
            root.parent = null; //important!! (update parent pointer)
        }
        rbSetChild(node.left, false, node);
        rbSetChild(node, true, leftRightChild);
    }

    /**
     * Rebalance to maintain the red black balance tree after operations.
     * Helper for the RBT add().
     * @param node -the current node to be rebalanced
     */
    private void rbBalance (Node node) {
        Node par = node.parent;
        Node grandparent = getGrandparent(node);
        Node uncle = getUncle(node);
        //Case1: if node is root
        if (par == null){
            node.red = false;  //color the node black
            return;
        }
        //Case2: if node's parent is black
        if (!par.red) return;
        //Case3: if parent and uncle are both red (grandparent is not null)
        if (uncle != null && uncle.red) {
            par.red = false; //color parent and uncle black
            uncle.red = false;
            grandparent.red = true; //color grandparent red
            rbBalance(grandparent); //recursively rebalance grandparent
            return;
        }
        //Case4: if parent is red but uncle is black (grandparent is not null)
        if (grandparent != null) {
            if (node == par.right && par == grandparent.left){
                rbRotateLeft(par);
                //update current node and parent after rotation (grandparent doesn't
                //change)
                node = par;
                par = node.parent;
            } else if (node == par.left && par == grandparent.right){
                rbRotateRight(par);
                //update current node and parent after rotation (grandparent doesn't
                //change)
                node = par;
                par = node.parent;
            }
            par.red = false; //color parent black
            grandparent.red = true; //color grandparent red
            if (node == par.left)
                rbRotateRight(grandparent);
            else 
                rbRotateLeft(grandparent);
        } else {
            return;
        }
    }

    /**
     * Get the grandparent node of a node.
     * Helper for rbBalance().
     * @param node - the current node
     * @return the grandparent of the node, <tt>null</tt> if the node has no
     * grandparent
     */
    private Node getGrandparent(Node node) {
        //if node is root, it has no parent which also means has no grandparent
        if (node.parent == null) return null;
        return node.parent.parent;
    }

    /**
     * Get the uncle node of a node.
     * Helper for rbBalance().
     * @param node - the current node
     * @return the uncle of the node, <tt>null</tt> if the node has no uncle 
     */
    private Node getUncle(Node node){
        Node grandparent = getGrandparent(node);
        //if no grandparent, no uncle
        if (grandparent == null) return null;
        if (grandparent.left == node.parent)
            return grandparent.right;
        else 
            return grandparent.left;
    }

    /**
     * Insert a node that maintains BST order. Helper for the RBT add().
     * add() will guard against duplicates and null elements.
     * @param node - the node to insert
     */
    private void BSTinsert(Node node) {
        //if the tree is empty
        if (root == null) {
            root = node;
            node.parent = null; //important!!! (update parent pointer)
        } else {
            //traverse from the root to find where to insert
            Node cur = root;
            while (cur != null) {
               //if the node to be inserted is smaller than cur node
               if ((node.e).compareTo(cur.e) < 0) {
                   if (cur.left == null) {
                       cur.left = node;
                       node.parent = cur; //update parent pointer
                       cur = null; //immediately exit the loop once inserted 
                   } else 
                       cur = cur.left;
               //if the node to be inserted is greater than cur node 
               } else {
                   if (cur.right == null) {
                       cur.right = node;
                       node.parent = cur; //update parent pointer
                       cur = null; //immediately exit the loop once inserted
                   } else 
                       cur = cur.right;
               }
            }
        }
    }
    
    /**
     * Remove a node of a RB tree that will maintain RBT property.
     * Helper for the RBT remove(key).
     * @param node - the node to be removed
     */
    private void rbRemoveNode(Node node){
        //if the node has two children
        if (node.left != null && node.right != null) {
            Node pred = rbGetPredecessor(node);
            E predData = pred.e;
            rbRemoveNode(pred); //recursively remove the predecessor node
            node.e = predData; //replace the node's data with the pred's data
            return;
        }

        //if the node is black, restructure the tree to prepare for removal 
        if (!node.red)
            rbPrepForRemoval(node);
        //remove the node using BSTremoveNode()
        BSTremoveNode(node);
    }

    /**
     * Remove the node from the tree that willmaintain BST property.
     * Helper for rbRemoveNode(node).
     * @param node - the node to be removed
     */
    private void BSTremoveNode(Node node) {
        if (node == null) return;
        
        //Case1: Remove internal node with 2 children
        if (node.left != null && node.right != null) {
            //find successor (leftmost child of right subtree)
            Node suc = node.right;
            while (suc.left != null) {
                suc = suc.left;
            }
            node.e = suc.e; //replace the node's data with the succ's data
            BSTremoveNode(suc); //recursively remove successor node
        }

        //Case2: Remove root node (with 1 or 0 chidren)
        else if (node == root)  {
            if (node.left != null) //has a left child
                root = node.left;
            else  //has a right child or 0 child
                root = node.right;
            if (root != null)
                root.parent = null; //update new root's parent pointer
        }

        //Case3: Remove internal node with left child only
        else if (node.left != null)
            rbReplaceChild(node.parent, node, node.left);

        //Case4: Remove internal node with right chld only OR leaf node
        else
            rbReplaceChild(node.parent, node, node.right);
    }

    /**
     * Get the predecessor node (left subtree's rightmost child) of a node.
     * Helper for rbRemoveNode() which will handle if node has no left child.
     * @param node - the node to get the predecessor
     * @return the predecessor node 
     */
    private Node rbGetPredecessor(Node node) {
        //starting from the left subtree's root
        Node cur = node.left; 
        //keep looking for the rightmost child
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur;
    }

    /**
     * Get the sibling of a node.
     * Helper for rbPrepForRemoval().  
     * @param node - the node to get the sibling
     * @return the sibling, <tt>null</tt> if has no sibling 
     */
    private Node rbGetSibling(Node node) {
        //if node is not root
        if (node.parent != null) {
            if (node == node.parent.left)
                return node.parent.right;
            else
                return node.parent.left;
        } 
        //if node is root
        return null;
    }

    /**
     * Determine if the node is non-null and red (can check for null node).
     * Helper for rbPrepForRemoval().  
     * @param node - the node to be determined whether it's red
     * @return true if the node is non-null and red, false otherwise
     */
    private boolean rbNonNullAndRed(Node node) {
        if (node == null) return false;
        return (node.red);
    }

    /**
     * Determine if the node is null OR black (null node consider to be black).
     * Helper for rbPrepForRemoval().  
     * @param node - the node to be determined whether it's null or black
     * @return true if the node is null or black, false otherwise
     */
    private boolean rbNullOrBlack(Node node) {
        if (node == null) return true;
        return (!node.red);
    }

    /**
     * Determine if the node's both children are black.
     * Helper for rbPrepForRemoval().  
     * @param node - the node to be checked
     * @return true if the node's both children are black, false otherwise
     */
    private boolean rbBothChildrenAreBlack(Node node){
        if (node.left != null && node.left.red)
            return false;
        if (node.right != null && node.right.red)
            return false;
        return true;
    }

    /**
     * Restructure the tree (prepare for removal of black node) to maintain the
     * RBT black-path-length property. Helper for rbRemoveNode().
     * @param node - the node to be removed
     */
    private void rbPrepForRemoval(Node node) {
        if (rbCase1(node)) 
           return;
        Node sibling = rbGetSibling(node);
        if (rbCase2(node, sibling))
            sibling = rbGetSibling(node); //after action, update sibling
        if (rbCase3(node, sibling))
            return;
        if(rbCase4(node, sibling))
            return;
        if (rbCase5(node, sibling))
            sibling = rbGetSibling(node); //after action, update sibling
        if (rbCase6(node, sibling))
            sibling = rbGetSibling(node); //after action, update sibling

        sibling.red = node.parent.red;
        node.parent.red = false;
        if (node == node.parent.left){
            sibling.right.red = false;
            rbRotateLeft(node.parent);
        } else {
            sibling.left.red = false;
            rbRotateRight(node.parent);
        }
    }


    /**------------Prepare For Removal Cases --------------**/

    /**
     * Prepare for removal Case 1: Node is red or node's parent is null.
     * Action: none
     * Process additional cases after action: no
     * @param node - the node to be removed
     * @return true if is Case 1, false otherwise
     */
    private boolean rbCase1(Node node) {
        if (node.red || node.parent == null)
            return true;
        else 
            return false;
    }

    /**
     * Prepare for removal Case 2: Sibling node is red.
     * Action: Color parent red and sibling black. If node is left child of
     * parent, rotate left at parent node, otherwise rotate right at parent
     * node.
     * Process additional cases after action: Yes
     * @param node - the node to be removed
     * @param sibling - the sibling of the node
     * @return true if is Case 2, false otherwise
     */
    private boolean rbCase2(Node node, Node sibling) {
        if (sibling.red) {
            node.parent.red = true;
            sibling.red = false;
            if (node == node.parent.left)
                rbRotateLeft(node.parent);
            else
                rbRotateRight(node.parent);
            return true;
        }
        return false;
    }
   
   /**
    * Prepare for removal Case 3: Parent is black and both of sibling's children
    * are black.
    * Action: Color sibling red and call removal preparation function on parent.
    * Process additional cases after action: No
    * @param node - the node to be removed
    * @param sibling - the sibling of the node
    * @return true if is Case 3, false otherwise
    */
    private boolean rbCase3(Node node, Node sibling) {
        if (!node.parent.red && rbBothChildrenAreBlack(sibling)){
            sibling.red = true;
            rbPrepForRemoval(node.parent);
            return true;
        }
        return false;
    }

    /**
     * Prepare for removal Case 4: Parent is red and both of sibling's children
     * are black.
     * Action: Color parent black and sibling red.
     * Process additional cases after action: No
     * @param node - the node to be removed
     * @param sibling - the sibling of the node
     * @return true if is Case 4, false otherwise
     */
    private boolean rbCase4(Node node, Node sibling) {
        if (node.parent.red && rbBothChildrenAreBlack(sibling)) {
            node.parent.red = false;
            sibling.red = true;
            return true;
        }
        return false;
    }

    /**
     * Prepare for removal Case 5: Sibling's left child is red, sibling's right
     * child is black, and node is left child of parent.
     * Action: Color sibling red and sibling's left child black. Rotate right at
     * sibling.
     * Process additional cases after action: Yes
     * @param node - the node to be removed
     * @param sibling - the sibling of the node
     * @return true if is Case 5, false otherwise
     */
     private boolean rbCase5(Node node, Node sibling) {
         if (rbNonNullAndRed(sibling.left) && rbNullOrBlack(sibling.right)
             && node == node.parent.left) {
             sibling.red = true;
             sibling.left.red = false;
             rbRotateRight(sibling);
             return true;
         }
         return false;
     }

     /**
      * Prepare for remove Case 6: Sibling's left child is black, sibling's
      * right child is red, and node is right child of parent.
      * Action: Color sibling red and sibling's right child black. Rotate left
      * at sibling.
      * Process additional cases after action: Yes
      * @param node - the node to be removed
      * @param sibling - the sibling of the node
      * @return true if is Case 6, false otherwise
      */
     private boolean rbCase6(Node node, Node sibling) {
         if (rbNullOrBlack(sibling.left) && rbNonNullAndRed(sibling.right)
             && node == node.parent.right) {
             sibling.red = true;
             sibling.right.red = false;
             rbRotateLeft(sibling);
             return true;
         }
         return false;
     }

     /**
      * Inner iterator class for this BST_RB tree. Iterates through the tree using
      *  inorder traversal. 
      */
     private class BST_RBIterator implements Iterator<E> {

        private Stack<Node> nodesStack; 
        private Node cursor; //the node whose element that a call next() will return,
        //which is the top of stack
        
        /**
         * Construct an iterator.Initialize the iterator(cursor and stack).
         */
        private BST_RBIterator() {
            //initialize the stack 
            nodesStack = new Stack<Node>(); 
            cursor = root;
            while (cursor != null) { 
                nodesStack.push(cursor); //push the left subtree nodes as it goes
                cursor = cursor.left; //iteratively visit left subtree nodes
            }
        }

        /**
         * Returns true if the iteration has more elements
         * @return true if the iteration has more elements
         */
        public boolean hasNext() {
            return !nodesStack.empty();     
        }
        
        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException - if the iteration has no more
         * elements
         */
        public E next(){
            if (!hasNext())
                throw new NoSuchElementException();

            cursor = nodesStack.pop(); //update the cursor after each next() 
            E data = cursor.e; //save the data 
            //immeidiately after pop, update/reinitialzie cursor to be its successor node
            cursor = cursor.right; //start from the right subtree's root
            while (cursor != null) { //push the left subtree nodes
                nodesStack.push(cursor);
                cursor = cursor.left;
            }
            return data;
        }
        
        /**
         * Removes from the underlying collection the last element returned by
         * this iterator. This operation is not supported by this iterator.
         * @throws UnsupportedOperationException - this operation is not
         * supported by this iterator.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }

     } //end iterator class 

} //end BST_RB class
