import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Quicksort Algorithm, to the DoublyLinkedList implementation.
 */

public class DoublyLinkedList<T> implements List<T> {

	/**
	 * Node is a pair containing a data field and a pointers to
	 * the previous and next nodes in the list.
	 */
	class Node {
		T data;
		Node next, prev;

		Node(T data) {
			this(data, null, null);
		}

		Node(T data, Node prev, Node next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}

	protected Node head;  // always points to the headnode for this list
	protected int n;      // the number of nodes in this list, initially 0

	/**
	 * Creates the empty list.
	 */
	public DoublyLinkedList() {
		head = new Node(null);
		head.next = head.prev = head;
	}

	/**
	 *
	 *
	 * Rearranges the elements of this list so they are in order according
	 * to the supplied Comparator by using the Quicksort Algorithm.
	 * O(n log n) time
	 */

	public void sort(Comparator<T> comp) {
		// Call quicksortHelper to get the job done.

		quicksortHelper(comp, head.next, head.prev);

	}

	/**
	 *
	 *
	 * Sorts the elements in the list between low and high, inclusive, in the
	 * order specified by comp.
	 */
	private void quicksortHelper(Comparator<T> comp, Node low, Node high)
	{
//		System.out.println("low -> "+low.data);
//		System.out.println("high -> "+high.data);

		if(low != high && low != high.next)
		{

			Node pivotNode = partition(comp, low, high);

//			System.out.println("pibotNode.prev -> " + pivotNode.prev.data);
			quicksortHelper(comp, low, pivotNode.prev);
//			System.out.println("pibotNode.next -> " + pivotNode.next.data);
			quicksortHelper(comp, pivotNode.next, high);

		}

	}

	/**
	 *
	 *
	 * Partitions the elements in the list about the pivot (which is the
	 * data in the low node), and then returns the node containing the
	 * pivot.
	 * No mess with links between nodes, only manipulate data
	 */
	private Node partition(Comparator<T> comp, Node low, Node high)
	{
		T pivotData = low.data;
		Node pivot = low;
		low = low.next;

		outer:
		while (true)
		{
			while (comp.compare(high.data, pivotData) >= 0)
			{
				high = high.prev;
//				System.out.println("high -> " + high.data);
				if(isCrossed(high, low))
				{
					break outer;
				}
			}

			while (comp.compare(low.data, pivotData) <= 0)
			{
				low = low.next;
//				System.out.println("low -> " + low.data);
				if(isCrossed(high, low))
				{
					break outer;
				}
			}

//			if(isCrossed(high, low))
//			{
//				break outer;
//			}

			if(comp.compare(low.data, pivotData) > 0 && comp.compare(high.data, pivotData) < 0)
			{
//				System.out.println("before a swap | low -> " + low.data + " | high -> " + high.data);
				swap(high, low);
//				System.out.println("a swap happened | low -> " + low.data + " | high -> " + high.data);
			}
//			System.out.println("a swap happened | current list -> " + this.toString());
		}

		swap(high, pivot);

		return high;
	}

	/**
	 * Helper that checks if two nodes are crossed
	 * @param node1 Node
	 * @param node2 Node
	 * @return boolean
	 */
	private boolean isCrossed(Node node1, Node node2)
	{
		return node1.next == node2 || node2.prev == node1;
	}


	/**
	 *
	 * @param node1 Node
	 * @param node2 Node
	 */
	private void swap(Node node1, Node node2)
	{
		T tmpData = node1.data;
		node1.data = node2.data;
		node2.data = tmpData;

	}



	/**
	 * Inserts the value x at the end of this list.
	 */
	public void add(T x) {
		n++;
		Node last = head.prev;
		Node curr = new Node(x, last, head);
		last.next = curr;
		head.prev = curr;
	}

	/**
	 * Inserts the value x at the front of this list.
	 */
	public void addFront(T x) {
		n++;
		Node curr = new Node(x, head, head.next);
		head.next.prev = curr;
		head.next = curr;
	}

	/**
	 * Removes the element at index i from this list.
	 * @return the data in the removed node.
	 * @throw IndexOutOfBoundsException iff i is out of range
	 * for this list.
	 */
	public T remove(int i) {
		if (i < 0 || i >= size())
			throw new IndexOutOfBoundsException();
		Node p = head.next;
		while (i-- > 0)
			p = p.next;
		remove(p);
		return p.data;
	}

	/**
	 * Removes the node pointed to by p. This helper is called by the list's
	 * remove() and by the iterator's remove().
	 */
	private void remove(Node p) {
		n--;
		p.prev.next = p.next;
		p.next.prev = p.prev;
	}

	/**
	 * Returns the i-th element from this list, where i is a zero-based index.
	 * @throw IndexOutOfBoundsException iff i is out of range for this list.
	 */
	public T get(int i) {
		if (i < 0 || i >= size())
			throw new IndexOutOfBoundsException();
		Node p = head.next;
		while (i > 0) {
			p = p.next;
			i--;
		}
		return p.data;
	}

	/**
	 * Returns true iff the value x appears somewhere in this list.
	 */
	public boolean contains(T x) {
		Node p = head.next;
		while (p != head)
			if (p.data.equals(x))
				return true;
			else
				p = p.next;
		return false;
	}

	/**
	 * Returns the number of elements in this list.
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns an iterator for this list.
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node p = head.next;

			public boolean hasNext() {
				return p != head;
			}

			public T next() {
				T ans = p.data;
				p = p.next;
				return ans;
			}

			public void remove() {
				DoublyLinkedList.this.remove(p.prev);
			}
		};
	}

	/**
	 * Returns a string representing this list (resembling a Racket list).
	 */
	public String toString() {
		if (isEmpty())
			return "()";
		Iterator<T> it = iterator();
		StringBuilder ans = new StringBuilder("(").append(it.next());
		while (it.hasNext())
			ans.append(" ").append(it.next());
		return ans.append(")").toString();
	}

	/**
	 * test cases
	 */
	public static void main(String... args) {

		int[][] testMatrix = {
				{4, 3, 0, 6, 5, 7, 2, 8, 1},
				{1,2,3,4,5,6,7,8},
				{5,5,5,5,5},
				{5},
				{1,2},
				{1,2,1,2,1,2,1,2},
				{1,2,1,2,1,2,1}
		};

		for(int[] a : testMatrix)
		{
			// produce DLL to be tested
			DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
			for(int x : a)
			{
				xs.add(x);
			}

			// make ascending contrast data
			int[] aClone = a.clone();
			Arrays.sort(aClone);

			// sort DLL ascending
			xs.sort((x, y) -> x.compareTo(y));

			// run asserts
			for(int i = 0; i < aClone.length; i++)
			{
				assert aClone[i] == xs.get(i);
			}


			// make descending contrast data
			Integer[] aCloneReverse = Arrays.stream( a ).boxed().toArray( Integer[]::new );
			Arrays.sort(aCloneReverse, Collections.reverseOrder());

			// sort DLL descending
			xs.sort((x, y) -> y.compareTo(x));

			// run asserts
			for(int i = 0; i < aCloneReverse.length; i++)
			{
				assert aCloneReverse[i] == xs.get(i);
			}

		}


		System.out.println("All tests passed...");
	}
}

//
//interface List<T> extends Iterable<T> {
//	void add(T x);  // simple add
//	T remove(int i);
//	T get(int i);
//	boolean contains(T x);
//	int size();
//	default boolean isEmpty() {
//		return size() == 0;
//	}
//}