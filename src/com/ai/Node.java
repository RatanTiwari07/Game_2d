package com.ai;

public class Node {

	boolean open;
	boolean solid;
	boolean checked;

	int gCost;
	int fCost;
	int hCost;

	Node parent;

	public int col;
   	public int row;

	public Node (int col , int row) {

		this.col = col;
		this.row = row;
	}
}
