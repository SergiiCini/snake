package com.codenjoy.dojo.snake.moving.algorithm;

import java.util.*;

public class AStar {

    //costs for vertical and horizontal moves
    public static final int V_H_COST = 10;
    //cells of out grid
    private Cell[][] grid;

    //we define a priority queue for open cells
    //open Cells: the set of nodes to be evaluated
    //we put the node with the lowest cost in first
    private PriorityQueue<Cell> openCells;
    //closed Cells: the set of nodes already has evaluated
    private boolean[][] closedCells;
    //start cell:
    private int startX, startY;
    //end cell:
    private int endX, endY;
    public List<Cell> path = new ArrayList<>();


    public AStar(int width, int height, int sX, int sY, int eX, int eY, int[][] obstacles) {
        grid = new Cell[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<Cell>((Cell c1, Cell c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
        });
        startCell(sX, sY);
        endCell(eX, eY);

        //init heuristic and cells
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endX) + Math.abs(j - endY);
                grid[i][j].solution = false;
            }
        }
        grid[startX][startY].finalCost = 0;

        //we put the obstacles on the grid
        for (int i = 0; i < obstacles.length; i++) {
            addObstaclesOnCell(obstacles[i][0], obstacles[i][1]);
        }
    }

    public void addObstaclesOnCell(int x, int y) {
        grid[x][y] = null;
    }

    public void startCell(int x, int y) {
        startX = x;
        startY = y;
    }

    public void endCell(int x, int y) {
        endX = x;
        endY = y;
    }

    public void updateCostIfNeeded(Cell current, Cell t, int cost) {
        if (t == null || closedCells[t.x][t.y]) return;
        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openCells.contains(t);

        if (!isOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;

            if (!isOpen) openCells.add(t);
        }
    }

    public void process() {
        //we add the start location to open list
        openCells.add(grid[startX][startY]);
        Cell current;

        while (true) {
            current = openCells.poll();

            if (current == null)
                break;
            closedCells[current.x][current.y] = true;

            if (current.equals(grid[endX][endY])) return;

            Cell t;

            if (current.x - 1 >= 0) {
                t = grid[current.x - 1][current.y];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
            }

            if (current.y - 1 >= 0) {
                t = grid[current.x][current.y - 1];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
            }

            if (current.y + 1 < grid[0].length) {
                t = grid[current.x][current.y + 1];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
            }

            if (current.x + 1 < grid.length) {
                t = grid[current.x + 1][current.y];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
            }
        }
    }

    public void display() {
        System.out.println("Grid: ");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == startX && j == startY)
                    System.out.print("SO  "); //source cell
                else if (i == endX && j == endY)
                    System.out.print("DE  "); //destination cell
                else if (grid[i][j] != null)
                    System.out.printf("%-3d ", 0);
                else
                    System.out.print("OB  "); //block cell
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayScores() {
        System.out.println("\nScores for cells: ");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null)
                    System.out.printf("%-3d ", grid[i][j].finalCost);
                else
                    System.out.print("OB ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displaySolution() {
        if (closedCells[endX][endY]) {
            //we track back the path
            System.out.println("Path: ");
            Cell current = grid[endX][endY];
            System.out.print(current);
            grid[current.x][current.y].solution = true;

            while (current.parent != null) {
                path.add(current.parent);
                System.out.print(" -> " + current.parent);
                grid[current.parent.x][current.parent.y].solution = true;
                current = current.parent;
            }
            System.out.println("\n");

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (i == startX && j == startY)
                        System.out.print("SO  "); //source cell
                    else if (i == endX && j == endY)
                        System.out.print("DE  "); //destination cell
                    else if (grid[i][j] != null)
                        System.out.printf("%-3s ", grid[i][j].solution ? "X" : "0");
                    else
                        System.out.print("OB  "); //block cell
                }
                System.out.println();
            }
            System.out.println();
        } else
            System.out.println("No possible path.");
    }
}
