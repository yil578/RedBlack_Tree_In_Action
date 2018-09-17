import java.util.*;

/**
 * BST_AdaptPattern class that implements the BST_Interface interface by adapting
 * the TreeSet class from Java Collection Framework.
 *
 * @author Madeline Yi-Chen, Lee <yil578@ucsd.edu>
 */
public class BST_AdaptPattern<E extends Comparable <? super E>> 
    implements BST_Interface<E> {

    //instance variable: the backend TreeSet that is adapted by this tree
    private TreeSet<E> tree;

    /**
     * Constructs a new, empty binary search tree, sorted according to the
     * natural ordering of its elements.
     */
    public BST_AdaptPattern(){
        tree = new TreeSet();
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
    public BST_AdaptPattern(Collection<? extends E> c) {
        tree = new TreeSet(c);
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
        return tree.add(e);
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
        return tree.addAll(c);
    }

    /**
     * Removes all of the elements from this search tree.
     */
	public void clear() {
        tree.clear();
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
        return tree.contains(o);
    }

    /**
     * Returns the first (lowest) element currently in this search tree.
     * @return the first (lowest) element currently in this tree
     * @throws NoSuchElementException - if this tree is empty
     */
	public E first() {
        return tree.first();
    }

    /**
     * Returns true if this search tree contains no elements.
     * @return true if this tree contains no elements
     */
	public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this search tree in ascending
     * order.
     * @return an iterator over the elements in this tree in ascending order
     */
	public Iterator<E> iterator() {
        return new BST_AdaptPatternIterator();
    }

    /**
     * Returns the last (highest) element currently in this search tree.
     * @return the last (highest) element currently in this search tree
     * @throws NoSuchElementException - if this tree is empty
     */
	public E last() {
        return tree.last();
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
        return tree.remove(o);
    }

    /**
     * Returns the number of elements in this search tree (its cardinality)
     * @return the number of elements in this search tree (its cardinality)
     */
	public int size() {
        return tree.size();
    }

    /**
     * Returns the height of the search tree. An empty tree returns 0, a tree
     * with one element returns a height of 1, and size() for all other cases.
     * @return the height of the search tree
     */
	public int height() {
        return this.size();
    }

    /**
     * Returns -1 if the target node is in the tree.
     * @param target - the target element the node references to
     * @return -1 if the target node IS in the tree
     * @throws NoSuchElementException If target is not found in the tree
     * @throws IllegalArgumentException Any other problems
     */
	public int numChildren(E target) {
        try {
            if (contains(target)) return -1;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns a string representation of the tree. 
     * @return a string representation of the tree.
     */
    @Override
    public String toString() {
        return tree.toString();
    }

    /**
     * Inner iterator class for this BST_AdaptPattern tree. 
     */
    private class BST_AdaptPatternIterator implements Iterator<E> {

        private Iterator<E> treeSetIterator; //the backend TreeSet iterator

        /**
         * Construct an iterator.
         */
        private BST_AdaptPatternIterator() {
            treeSetIterator = tree.iterator();
        }

        /**
         * Returns true if the iteration has more elements
         * @return true if the iteration has more elements
         */
        public boolean hasNext() {
            return treeSetIterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException - if the iteration has no more
         * elements
         */
        public E next(){
            return treeSetIterator.next();
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

} //end BST_AdaptPattern class
