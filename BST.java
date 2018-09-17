
import java.util.*;

/**
 * BST class that implements the BST_Interface interface and extends the
 * BST_RB class. 
 *
 * @author Madeline Yi-Chen, Lee <yil578@ucsd.edu>
 */
public class BST<E extends Comparable <? super E>> extends BST_RB<E> 
    implements BST_Interface<E> {

    /**
     * Constructs a new, empty binary search tree, sorted according to the
     * natural ordering of its elements.
     */
    public BST() {
        super();
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
    public BST(Collection<? extends E> c) {
        super(c);
    }



}
