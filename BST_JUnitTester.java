import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * A Tester that tests for BST, BST_AdaptPattern, BST_RB class.
 * @author Madeline Yi-Chen, Lee <yil578@ucsd.edu>
 */
public class BST_JUnitTester {

    //constant that determine which kind of tree
    private static final int BST_ADAPT = 1;
    private static final int BST_NORMAL = 2;
    private static final int BST_RB = 3;
    
    /**
     * Tree instances (fixture) for this test. Can switch the treetype parameter
     * to test for differnet trees. Note that BST_AdaptPattern returns
     * different height() and numChildren() than BST and BST_RB.
     */
    private BST_Interface<Integer> tree = getWhatTree(BST_NORMAL);
    private BST_Interface<Integer> bst = getWhatTree(BST_RB);
    private Iterator<Integer> bstIter;
    private ArrayList<Integer> bstList = new ArrayList<Integer>();
    
    /**
     * Genenric factory method that returns a different tree instance.
     * @param treetype - the number representinng different tree instances
     * @return the tree instance
     */
    private static BST_Interface<Integer> getWhatTree(int treetype) {
        switch (treetype) {
            case BST_ADAPT: return new BST_AdaptPattern<Integer>();
            case BST_NORMAL: return new BST<Integer>();
            case BST_RB: return new BST_RB<Integer>();
            default: return new BST<Integer>();
        }
    } 

	@Test
	public void testEmptyTree() {
		tree.clear();
		assertEquals(0, tree.size());
		assertEquals(0, tree.height());
		assertEquals("[]", tree.toString());
	}

    /**
     * Set up the test fixture trees. Add 1~10 to the bstList and set up the
     * bst tree by addAlling the list collection.
     */
	@Before
	public void setUp() throws Exception {
		tree.add(5);
		tree.add(4);
		tree.add(2);
		tree.add(3);
		tree.add(6);
		tree.add(1);

        bstList.add(4);
        bstList.add(3);
        bstList.add(2);
        bstList.add(7);
        bstList.add(8);
        bstList.add(9);
        bstList.add(10);
        bstList.add(1);
        bstList.add(6);
        bstList.add(5); //bstList: 4 3 2 7 8 9 10 1 6 5
        bst.addAll(bstList); //bst: 1 2 3 4 5 6 7 8 9 10
        bstIter = bst.iterator(); //bst iterator
	}
    
    /**
     * Test BST_RB(Collection<? extends E> c). Test if this constructor
     * correcly constructs a RBT with the ArrayList bstList.
     */
    @Test
    public void testBST_RBConstructCollection() {
        BST_RB<Integer> rb = new BST_RB<Integer>(bstList);
        assertEquals(rb.toString(), bst.toString()); 
    }

    /**
     * Test add(E e). Test if add() returns false when adding duplicates and
     * if it successfully adds non-duplicates and returns true.
     */
    @Test
    public void testAdd() {
        for (int i = 1; i <= 10; i++) {
            assertFalse(bst.add(i));
        }
        assertTrue(bst.add(20));
        assertEquals(11, bst.size());
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20]", bst.toString());
    }

    /**
     * Test RedBlack add() Case1: if node is root.
     */
    @Test
    public void testRBAddCase1() {
        BST_RB<Integer> rb = new BST_RB<Integer>();
        rb.add(22);
        assertEquals("[22]", rb.toString());
        assertEquals(0, rb.numChildren(22));
    }

    /**
     * Test RedBlack add() Case2: if node's parent is black.
     */
    @Test
    public void testRBAddCase2() {
        BST_RB<Integer> rb = new BST_RB<Integer>();
        rb.add(22);
        rb.add(11);
        rb.add(33);
        assertEquals(2, rb.height());
        assertEquals(2, rb.numChildren(22));
    }

    /**
     * Test RedBlack add() Case3: if both parent and uncle are red.
     */
    @Test
    public void testRBAddCase3() {
        BST_RB<Integer> rb = new BST_RB<Integer>();
        rb.add(22);
        rb.add(11);
        rb.add(33);
        rb.add(55);
        assertEquals(3, rb.numChildren(22));
        assertEquals(1, rb.numChildren(33));
        assertEquals(3, rb.height());
    }

    /**
     * Test RedBlack add() Case4: if parent is red but uncle is black.
     */
    @Test
    public void testRBAddCase4() {
        BST_RB<Integer> rb = new BST_RB<Integer>();
        rb.add(22);
        rb.add(11);
        rb.add(33);
        rb.add(55);
        rb.add(44);
        assertEquals(4, rb.numChildren(22));
        assertEquals(2, rb.numChildren(44));
        assertEquals(0, rb.numChildren(11));
        assertEquals(3, rb.height());
    }

    /**
     * Test if addAll() method correcly add elements into the bst.
     */
    @Test
    public void testAddAll() {
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", bst.toString());
        assertEquals(10, bst.size());
    } 

    /**
     * Test clear(). Test if clear() correctly clears the elements in the tree.
     */
    @Test
    public void testClear() {
        bst.clear();
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
    }

    /**
     * Test contains(E o). Test if contains() returns true when the elements do
     * exist and returns false when they do not.
     */
    @Test
    public void testContains(){
        for (int i = 1; i<= 10; i++){
            assertTrue(bst.contains(i));
        }
        assertFalse(bst.contains(0));
        assertFalse(bst.contains(11));
    }

    /**
     * Test first(). Test if first() correcly returns the smallest element in
     * the tree.
     */
    @Test
    public void testFirst() {
        assertEquals(new Integer(1), bst.first());
        assertEquals(new Integer(1), tree.first());
        bst.add(0);
        assertEquals(new Integer(0), bst.first());
    }

    /**
     * Test last(). Test if last() correctly returns the largest element in the
     * tree.
     */
    @Test
    public void testLast() {
        assertEquals(new Integer(10), bst.last());
        assertEquals(new Integer(6), tree.last());
        bst.add(100);
        assertEquals(new Integer(100), bst.last());
    }

    /**
     * Test isEmpty(). Test if isEmpty() correcly returns true if the tree is
     * empty, false if the tree is not.
     */
    @Test
    public void testIsEmpty() {
        assertFalse(bst.isEmpty());
        assertFalse(tree.isEmpty());
        bst.clear();
        assertTrue(bst.isEmpty());
    }

    /**
     * Test iterator(). Test if the iterator correcly iterates the tree in
     * in-inorder traveral order with hasNext() and next(), and if it throws
     * NoSuchElementException when it iterates till the end when no element
     * exists.
     */
     @Test
     public void testIterator() {
        int i = 1;
        while (bstIter.hasNext()) {
            assertEquals(new Integer(i), bstIter.next());
            i++;
        }
        try {
            bstIter.next();
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            //correct!
        }
    } 

    /**
     * Test iterator() remove operation should throw
     * UnsupportedOperationException.
     */
    @Test
    public void testIteratorRemoveUnsupported(){
        bstIter.next();
        try {
            bstIter.remove();
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e){
            //correct!
        }
    } 
    
    /**
     * Test height() for BST_RB/BST. Test if height() correctly returns the
     * height of the bst.
     * Note that BST_AdaptPattern returns different height than BST_RB/BST.
     */
    @Test
    public void testHeight() {
        //height() for RBT!
        assertEquals(4, bst.height());
    }

    /**
     * Test numChildren() for BST_RB/BST. Test if numChildren() correctly 
     * returns the number of children nodes of the bst tree.
     * Note that BST_AdaptPattern returns different numChildren than BST_RB/BST.
     */
    @Test
    public void testNumChildren(){
        //numChildren() for RBT!
        assertEquals(0, bst.numChildren(1));
        assertEquals(1, bst.numChildren(2));
        assertEquals(9, bst.numChildren(3));
        assertEquals(0, bst.numChildren(4));
        assertEquals(2, bst.numChildren(5));
        assertEquals(0, bst.numChildren(6));
        assertEquals(6, bst.numChildren(7));
        assertEquals(0, bst.numChildren(8));
        assertEquals(2, bst.numChildren(9));
        assertEquals(0, bst.numChildren(10));
    }

    @Test
    public void testNumChildren2(){
        //numChildren() for RBT!
        assertEquals(0, tree.numChildren(1));
        assertEquals(2, tree.numChildren(2));
        assertEquals(0, tree.numChildren(3));
        assertEquals(5, tree.numChildren(4));
        assertEquals(1, tree.numChildren(5));
        assertEquals(0, tree.numChildren(6));
    }

    /**
     * Test remove() for BST_RB/BST with mutiple Cases combined. Test if 
     * remove() correctly removes the specified element that maintains RBT 
     * property.
     */
    @Test
    public void testRemove() {
        bst.remove(7);
        bst.remove(5);
        bst.remove(6);
        //toString()
        assertEquals("[1, 2, 3, 4, 8, 9, 10]", bst.toString());
        //size()
        assertEquals(7, bst.size());
        //height() for RBT!
        assertEquals(4, bst.height());
        //numChildren() for RBT!
        assertEquals(0, bst.numChildren(1));
        assertEquals(1, bst.numChildren(2));
        assertEquals(6, bst.numChildren(3));
        assertEquals(1, bst.numChildren(4));
        assertEquals(0, bst.numChildren(8));
        assertEquals(3, bst.numChildren(9));
        assertEquals(0, bst.numChildren(10));
    }
    
    @Test
    public void testRemove2() {
        tree.remove(2);
        tree.remove(5);
        assertEquals(4, tree.size());
        //height() for RBT!
        assertEquals(3, tree.height());
        //numChildren() for RBT!
        assertEquals(1, tree.numChildren(1));
        assertEquals(0, tree.numChildren(3));
        assertEquals(3, tree.numChildren(4));
        assertEquals(0, tree.numChildren(6));
    }

    @Test
	public void testAddUnique() {
		for (int n = 1; n <= 6; n++) {
			assertTrue(tree.contains(n));
		}
	}

	@Test
	public void testSize() {
		assertEquals(6, tree.size());
	}

	@Test
	public void testDepth() {
		assertEquals(3, tree.height());
	}

	@Test
	public void testToString() {
		assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testAddDuplicates() {
		for (int n = 1; n <= 6; n += 2)
			assertFalse(tree.add(n));
	}

	@Test
	public void testRemoveExistingLeaf() {
		assertTrue(tree.remove(1));
		assertEquals(5, tree.size());
		assertEquals("[2, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithEmptyRightChild() {
		assertTrue(tree.remove(4));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 3, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithEmptyLeftChild() {
		tree.add(7);
		assertTrue(tree.remove(6));
		assertEquals(6, tree.size());
		assertEquals("[1, 2, 3, 4, 5, 7]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithTwoChildren() {
		assertTrue(tree.remove(2));
		assertEquals(5, tree.size());
		assertEquals("[1, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveRoot() {
		assertTrue(tree.remove(5));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 3, 4, 6]", tree.toString());
	}

	@Test
	public void testRandomAddAndRemove() {
		Random rnd = new Random();

		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);

		for (int n = 0; n < 1000; n++) {
			int toAdd = rnd.nextInt(10);
			assertEquals(oracle.add(toAdd), tree.add(toAdd));
			int toRemove = rnd.nextInt(10);
			assertEquals(oracle.remove(toRemove), tree.remove(toRemove));
			int checkExists = rnd.nextInt(10);
			assertEquals(oracle.contains(checkExists), tree
					.contains(checkExists));
			assertEquals(oracle.size(), tree.size());
			assertEquals(oracle.toString(), tree.toString());
		}
	}

	@Test
	public void testOtherType(){
		BST_RB<String> stringTree = new BST_RB<String>();
		stringTree.add("D");
		stringTree.add("A");
		stringTree.add("C");
		stringTree.add("A");
		stringTree.add("B");
		assertEquals(4, stringTree.size());
		assertTrue(stringTree.contains("C"));
		stringTree.remove("C");
		assertFalse(stringTree.contains("C"));
	}
	
}
