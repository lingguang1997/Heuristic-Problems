package Mint;

public class Result {
    CombinationWithCost[] combinations;
    double cost;
    Result() {}
    Result(CombinationWithCost[] combinations, double cost) {
    	this.combinations = combinations;
    	this.cost = cost;
    }
}
