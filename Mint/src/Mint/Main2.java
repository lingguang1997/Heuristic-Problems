package Mint;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main2 {

	/**
	 * @param  N
	 * @author Lingguang Liu
	 */

	static final int DENOMINATION_NUMBER = 5;
	static final int DENOMINATION_BOUND = 50;
	static final int PRICE_NUMBER = 99;
	static final int MAX_CHANGE = 199;
	static int[] guesses = {1};

	static PriorityQueue<TreeNode> q = new PriorityQueue<TreeNode>();

	static Result computeCost(int[] coins, double N, double currentBestScore) {
		int[] coinsUsed = new int[201];

		CombinationWithCost[] result = new CombinationWithCost[100];

		coinsUsed[0] = 0;
		int[][] coinSolution = new int [201][coins.length];
		int[][] coinFun = new int [201][coins.length];
		for(int i=0;i<coins.length;i++)
		{
			coinSolution[0][i]=0;
			coinFun[0][i]=0;
		}
				
		int tmp=0;
		

		for (int cents = 1; cents <= MAX_CHANGE; cents++) {
			
			if(cents==100)
			{
				coinsUsed[100]=0;
				continue;
			}
			int minCoins = cents;
			int newCoin = 1;

			for (int j = 0; j < coins.length; j++) {
				if (coins[j] > cents) // Cannot use coin j
					continue;
				if (coinsUsed[cents - coins[j]] + 1 < minCoins) {
					minCoins = coinsUsed[cents - coins[j]] + 1;
					newCoin = coins[j];
					tmp=j;
				}
			}
			
			for(int i=0;i<coins.length;i++)
				coinSolution[cents][i]=coinSolution[cents-newCoin][i];
			coinSolution[cents][tmp]+=1;

			coinsUsed[cents] = minCoins;			
		}
		double total =0;
		for (int cents = 1; cents <= PRICE_NUMBER; cents++) {
			
			int minCoins = coinsUsed[cents];
			int give=cents;
			int back=0;

			for (int j = cents; j <= 199/*2*cents*/; j++) {
				
				if (coinsUsed[j] +  coinsUsed[j-cents]<= minCoins) {
					
					minCoins = coinsUsed[j] +  coinsUsed[j-cents];
					give=j;
					back=j-cents;
				}
				
			}
			result[cents] = new CombinationWithCost();
			int priceCost = 0;
			for(int i=0;i<coins.length;i++)
			{
				coinFun[cents][i]+=coinSolution[give][i];
				coinFun[cents][i]-=coinSolution[back][i];
				if (result[cents].combination == null) {
					result[cents].combination = new int[coins.length];
				}
				result[cents].combination[i]=coinFun[cents][i];
				priceCost += Math.abs(coinFun[cents][i]);
			}
			result[cents].cost = priceCost;
		
			coinsUsed[cents] = minCoins;
			total += (cents%5==0?N:1)*minCoins;
			if(total>currentBestScore)
				return new Result(result, Integer.MAX_VALUE -1);

		}
		
		return new Result(result, total);


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
			if (node.cost == Integer.MAX_VALUE && node.denominations.length == DENOMINATION_NUMBER) {
				break;
			}
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
               
                if (newNode.denominations.length == DENOMINATION_NUMBER) {
    				Result result = computeCost(newNode.denominations, N, minNode.cost);
    				newNode.cost = result.cost;
    				newNode.combinations = result.combinations;
				} else {
					newNode.cost = Integer.MAX_VALUE;
				}
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
		System.out.println("count = " + count + " N = " + N + "\nminNode cost: " + minNode.cost
				+ " denominations: " + Arrays.toString(minNode.denominations));
//		int i = 0;
//		for (CombinationWithCost c : minNode.combinations) {
//			System.out.println(i + " " + c);
//			i++;
//		}
	}

}
