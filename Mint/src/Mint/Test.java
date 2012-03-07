package Mint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Test {

	/**
	 * @param args
	 * 
	 */
	static boolean arrayContains(int[] array, int elem) {
		for (int i : array) {
			if (i == elem) {
				return true;
			} 
		}
		return false;
	}

	public static void main(String[] args) {
		PriorityQueue<TreeNode> liveBranches = new PriorityQueue<TreeNode>();
		TreeNode n1 = new TreeNode(3);
		TreeNode n2 = new TreeNode(5);
		TreeNode n3 = new TreeNode(1);
		TreeNode n4 = new TreeNode(0);
		liveBranches.add(n1);
		liveBranches.add(n2);
		liveBranches.add(n3);
		liveBranches.add(n4);
		//System.out.println(liveBranches);
		while( liveBranches.size() > 0 ){
            TreeNode p = ( TreeNode )liveBranches.poll();
            
            System.out.println(p.cost);
        }
		int[] a = {3, 2, 5};
		Arrays.sort(a);
		System.out.println(Arrays.toString(a));
		
		Map map = new HashMap<Integer, String>();
		int[] denominations = {1, 5, 10, 17, 25};
		for (int i = 0; i < denominations.length; i++) {
			for (int j = i + 1; j < denominations.length; j++) {
				System.out.println();
				int coin = denominations[j] - denominations[i];
				if (!arrayContains(denominations, coin)) {
					map.put(coin, denominations[i] + " " + denominations[j]);

				}
			}
		}
		System.out.println(map);

	}

}
