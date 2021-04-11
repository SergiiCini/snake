package com.codenjoy.dojo.snake.moving.algorithm;

// we define a cell of out grid
public class Cell {

    //coordinates
    public int x, y;
    // parent cell for path
    public Cell parent;
    // Heuristic cost of the current cell
    public int heuristicCost;
    //final cost
    public int finalCost; // G+H
    // G(n) - the cost of the path from the start node to n
    // H(n) - the heuristic that estimates the cost of the cheapest path from n to the goal
    public boolean solution; //if cell is a part of the solution path;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}
