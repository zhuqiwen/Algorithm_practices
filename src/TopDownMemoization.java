import java.util.*;
import java.util.List;

/**
 *
 * We implement a top-down memoized solution to the Longest Increasing
 * Subsequence problem
 */

public class TopDownMemoization {

	private static Map<SubProblem, Integer> cache = new HashMap<>();

	/**
	 * Returns the length of the longest increasing subsequence in the
	 * array a.
	 */
	public static int lis(int[] a) {
		cache.clear();
		// Initially, we consider the element at position 0 and our cap,
		// so far, is 0.
		SubProblem p = new SubProblem(0, 0);
		return lisHelper(a, p);
	}

	/**
	 * TODO
	 *
	 * Returns the result of solving the SubProblem described by p.
	 */
	public static int lisHelper(int[] a, SubProblem p) {

		// check if subproblem has been solved
		if(cache.containsKey(p))
		{
			return cache.get(p);
		}


		int ans = 0;
		if (p.pos < a.length)
		{
			int with = -1, without = 0;

			if(a[p.pos] > p.cap)
			{
				with = lisHelper(a, new SubProblem(p.pos + 1, a[p.pos]));
			}
//			else
//			{
//				with = -1;
//			}

			without = lisHelper(a, new SubProblem(p.pos + 1, p.cap));
			ans = Math.max(1 + with, without);
		}


		cache.put(p, ans);

		return ans;
	}

	/**
	 * TODO: Write a comprehensive battery of test cases.
	 */
	public static void main(String... args) {
		int[] a;

		a = new int[] { 5, 6, 1, 2, 9, 3, 4, 7, 4, 3 };
		assert 5 == lis(a);

		a = new int[] { 2, 1, 5, 3, 6, 4, 2, 7, 9, 11, 8, 10 };
		assert 6 == lis(a);

		a = new int[100];
		for (int i = 0; i < a.length; i++)
			a[i] = i + 1;
		assert a.length == lis(a);


		a = new int[]{5,4,5,4,5,4,5,4,6,3};
		int b = lis(a);
		assert 3 == b;

		a = new int[100];
		for (int i = 99; i >= 0; i--)
		{
			a[99 - i] = i;
		}
		assert 1 == lis(a);


		a = new int[0];
		assert 0 == lis(a);


		System.out.println("All tests passed...");
	}
}

/**
 * A SubProblem corresponds to one node in the decision tree. It is
 * described by two pieces of information: the index in the array of
 * the element under consideration (for inclusion in the sequence
 * being constructed), and the largest element in the sequence so far.
 */
class SubProblem {
	int pos;  // the index in the array of the element under consideration
	int cap;  // the largest value in the sequence already selected

	/**
	 * Constructs a problem from the given position and cap.
	 */
	SubProblem(int pos, int cap) {
		this.pos = pos;
		this.cap = cap;
	}

	/**
	 *
	 * Returns true iff the given object equals this object, field for field.
	 * If we don't override this method, then the hash map will not be able to
	 * find previously stored problems.
	 *
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof SubProblem)
		{
			SubProblem o = (SubProblem) obj;
			if(this.pos == o.pos && this.cap ==  o.cap)
			{
				return true;
			}
		}

		return false;

	}

	/**
	 *  Have to override hashCode; otherwise duplicate pairs may appear in HashMap
	 * Returns a nicely packed version of this SubProblem. This promotes good
	 * behavior of a hash map that uses SubProblems as keys.
	 */
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + pos;
		hash = hash * 31 + cap;
		return hash;
	}

	/**
	 * Returns a sensible textual version of this SubProblem.
	 */
	public String toString() {
		return String.format("(%d, %d)", pos, cap);
	}
}
