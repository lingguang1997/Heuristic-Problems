package Mint;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main1 {

	/**
	 * @param  N
	 * @author Lingguang Liu 
	 */

	static final int DENOMINATION_NUMBER = 5;
	static final int DENOMINATION_BOUND = 50;
	static final int PRICE_NUMBER = 99;
	static int[] guesses = {1};

	static PriorityQueue<TreeNode> q = new PriorityQueue<TreeNode>();

	static Result computeCost(int[] denominations, double N) {
		CombinationWithCost[] result = new CombinationWithCost[PRICE_NUMBER + 1];
		CombinationWithCost cwc0 = new CombinationWithCost(0);
		cwc0.combination = new int[denominations.length];
		result[0] = cwc0;

		for (int price = 1; price < PRICE_NUMBER + 1; price++) {
			CombinationWithCost min = new CombinationWithCost();
			int r = 0;
			//System.out.println("current price: " + price);
			for (int j = 0; j < denominations.length && price >= denominations[j]; j++) {
				int cost = result[price - denominations[j]].cost + 1;
				if (cost < min.cost) {
					min.cost = cost;
					r = j;
				}				
			}
			min.combination = new int[denominations.length];
			System.arraycopy(result[price - denominations[r]].combination, 0, min.combination, 0, denominations.length);
			(min.combination)[r] += 1;
			result[price] = min;
			//System.out.println("price: " + price + " result for this price: " + result[price]);
		}
		int totalCost = 0;
		for (int i = 0; i < result.length; i++) {
			if (i % 5 == 0) {
				totalCost += N * result[i].cost;
			} else {
				totalCost += result[i].cost;
			}
		}

		return new Result(result, totalCost);

	}
	
	static boolean arrayContains(int[] array, int elem) {
		for (int i : array) {
			if (i == elem) {
				return true;
			} 
		}
		return false;
	}

	public static void main(String[] args) {
		double N = 0;
		if (args.length == 1) {
			N = Double.parseDouble(args[0]);
		} else {
			System.out.println("Please type an integer, try again. ");
			System.exit(0);
		}
		TreeNode root = new TreeNode(guesses, 0);
		q.add(root);

		TreeNode minNode = root;

		int offset = 1;
		int count = 0;
		while (!q.isEmpty()) {
			TreeNode node = q.poll();
			if (node.cost < minNode.cost) {
				minNode = node;
			}
			count ++;
			//System.out.println(Arrays.toString(node.denominations) + " layer: " + node.layer + " cost: " + node.cost);
			if (node.denominations.length < DENOMINATION_NUMBER && node.layer <= DENOMINATION_BOUND) {
				TreeNode newNode0 = new TreeNode(node, node, node.layer + 1);
				q.add(newNode0);
				// prevent adding duplicated coin
				int coin = node.layer + offset;
				while (arrayContains(node.denominations, coin)) {
					offset++;
					coin = node.layer + offset;
				}
				TreeNode newNode = new TreeNode(node.denominations, coin, node, Integer.MAX_VALUE, node.layer + 1);
				//System.out.println("denomination: " + Arrays.toString(newNode0.denominations) + " layer: " + newNode0.layer);

				//System.out.println("add coin: " + coin + " layer:" + (node.layer + 1));
				Result result = computeCost(newNode.denominations, N);
				newNode.cost = result.cost;
				newNode.combinations = result.combinations;
				//System.out.println("denomination: " + Arrays.toString(newNode.denominations) + " layer: " + newNode.layer);

				q.add(newNode);
			}
		}
		System.out.println("{\"denominations\": " + Arrays.toString(minNode.denominations) + ",");
		System.out.println(" \"exchanges\": [" + minNode.combinations[1] + ",");
	    for (int i = 2; i < minNode.combinations.length - 1; i++) {
			System.out.println("              " + minNode.combinations[i] + ",");
		}
	    System.out.println("              " + minNode.combinations[minNode.combinations.length - 1] + "]}");

//		System.out.println("count = " + count + " N = " + N + "\nminNode cost: " + minNode.cost
//				+ " denominations: " + Arrays.toString(minNode.denominations));
//		int i = 0;
//		for (CombinationWithCost c : minNode.combinations) {
//			System.out.println(i + " " + c);
//			i++;
//		}
	}

}
