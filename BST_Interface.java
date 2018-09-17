import java.util.Collection;
import java.util.Iterator;
public interface BST_Interface<E>
{
	public boolean add(E e);
	public boolean addAll(Collection<? extends E> c);
	public void clear();
	public boolean contains(E o);
	public E first();
	public boolean isEmpty();
	public Iterator<E> iterator();
	public E last();
	public boolean remove(E o);
	public int size();
	public int height();
	public int numChildren(E target);
}

