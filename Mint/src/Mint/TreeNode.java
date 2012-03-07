package Mint;

import java.util.Arrays;


public class TreeNode implements Comparable<TreeNode> {

	int[] denominations;

	TreeNode parent = null;

	double cost = Integer.MAX_VALUE;
	
	CombinationWithCost[] combinations;

	int layer = 0;

	TreeNode() {}
	
	TreeNode(int cost) {
		this.cost = cost;
	}
	
	TreeNode(int[] denom, int layer) {
		this.denominations = denom;
		this.layer = layer;
	}

	TreeNode(int[] denom, int coin, int layer) {
		this.denominations = new int[denom.length + 1];
		System.arraycopy(denom, 0, denominations, 0, denom.length);
		this.denominations[denom.length] = coin;
		this.layer = layer;
	}
	
	TreeNode(TreeNode node, TreeNode parent, int layer) {
		this.denominations = node.denominations;
		this.parent = parent;
		this.cost = node.cost;
		this.layer = layer;
	}

	TreeNode(int[] denom, int coin, TreeNode parent, int cost, int layer) {
		denominations = new int[denom.length + 1];
		System.arraycopy(denom, 0, denominations, 0, denom.length);
		denominations[denom.length] = coin;
		this.parent = parent;
		this.cost = cost;
		this.layer = layer;
	}

	@Override
	public int compareTo(TreeNode o) {
		if (this.cost < o.cost) {
			return -1;
		}
		if (this.cost > o.cost) {
			return 1;
		}
		if (this.layer > o.layer) {
			return -1;
		}
		if (this.layer < o.layer) {
			return 1;
		}
		return 0;
	}

    public String ToString() {
		return "denominations: " + Arrays.toString(denominations) + "\ncost: " + cost + "\n";
	}

}
