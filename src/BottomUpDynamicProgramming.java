
/**
 * a bottom-up solution to the Longest Increasing Subsequence
 * problem
 */

public class BottomUpDynamicProgramming {

	/**
	 * TODO
	 *
	 * Returns an array consisting of the longest increasing subsequence
	 * in a.
	 */
	public static int[] lis(int[] a) {
		int n = a.length;
		Result[] cache = new Result[n];


		if(n == 0)
		{
			return new int[]{};
		}


		cache[0] = new Result(1, -1);
		for(int i = 1; i < n; i ++)
		{
			int score = 1;
			int parent = -1;

			for(int j = 0; j < i; j++)
			{
				if(a[j] < a[i])
				{
					if(cache[j].score + 1 > score)
					{
						score = cache[j].score + 1;
						parent = j;
					}
				}
			}

			cache[i] = new Result(score, parent);
		}



		int[] highest = findHighest(cache);

//		System.out.println(highest.score);

		return findSequence(highest, cache, a);
	}


	public static int[] findHighest(Result[] cache)
	{

		 int[] highest = new int[2];

		for (int i = 0; i < cache.length; i++ )
		{
			if (cache[i].score > highest[0])
			{
				highest[0] = cache[i].score;
				highest[1] = i;
			}
		}

		return highest;
	}

	public static int[] findSequence(int[] highest, Result[] cache, int[] a)
	{
		int[] result = new int[highest[0]];
		int index = highest[1];
		for (int i = result.length-1; i >= 0; i--){
			result[i] = a[index];
			index = cache[index].parent;


		}

		return result;
	}



	/**
	 * test cases
	 */
	public static void main(String... args) {
		int[] a, b;
		a = new int[] { 5, 6, 1, 2, 9, 3, 4, 7, 4, 3 };
		b = lis(a);
		System.out.println(b[0]);
		assert 5 == b.length;

		assert 1 == b[0];
		assert 2 == b[1];
		assert 3 == b[2];
		assert 4 == b[3];
		assert 7 == b[4];
		a = new int[] { 2, 1, 5, 3, 6, 4, 2, 7, 9, 11, 8, 10 };
		b = lis(a);
		assert 6 == b.length;
		assert 2 == b[0];
		assert 5 == b[1];
		assert 6 == b[2];
		assert 7 == b[3];
		assert 9 == b[4];
		assert 11 == b[5];



		a = new int[]{};
		b = lis(a);
		assert 0 == b.length;



		a = new int[]{0,0,0,0,0,0};
		b = lis(a);
		assert 1 == b.length;
		assert 0 == b[0];

		a = new int[]{0};
		b = lis(a);
		assert 1 == b.length;
		assert 0 == b[0];



		a = new int[]{5,4,5,4,5,4,5,4,6,3};
		b = lis(a);
		assert 3 == b.length;
		assert 4 == b[0];
		assert 5 == b[1];
		assert 6 == b[2];




		System.out.println("All tests passed...");
	}
}

/**
 * A subproblem is to compute the length of the longest increasing
 * subsequence that ends with the i-th element in the array. The
 * result of solving such a subproblem is the score (i.e., the length
 * of the sequence) and the parent (i.e., the index of element in the
 * array that precedes the i-th one in the identified sequence).
 */
class Result {
	int score;
	int parent;

	Result(int score, int parent) {
		this.score = score;
		this.parent = parent;
	}

	public String toString() {
		return String.format("(%d,%d)", score, parent);
	}
}
