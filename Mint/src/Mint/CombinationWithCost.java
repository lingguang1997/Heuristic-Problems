package Mint;

import java.util.Arrays;


public class CombinationWithCost {
	int[] combination;
	int cost;
	CombinationWithCost() { this.cost = Integer.MAX_VALUE; }
	CombinationWithCost(int cost) { this.cost = cost; }
	public String toString() {
		return Arrays.toString(combination);
	}
}
