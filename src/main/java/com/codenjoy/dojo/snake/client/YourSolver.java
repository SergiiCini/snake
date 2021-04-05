package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.snake.moving.algorithm.AStar;
import com.codenjoy.dojo.snake.moving.algorithm.Cell;

import java.util.*;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private Board board;

    @Override
    public String get(Board board) {
        this.board = board;
        Point head = board.getHead();
        List<Point> snake = board.getSnake();
        List<Point> stones = board.getStones();
        System.out.println("Stone X " + stones.get(0).getX());
        System.out.println("Stone Y " + stones.get(0).getY());

        List<Point> apples = board.getApples();
        List<Point> walls = board.getWalls();
        Direction snakeDirection = board.getSnakeDirection();

        AStar aStar = new AStar(14, 14, head.getX(), head.getY(), apples.get(0).getX(), apples.get(0).getY(),
                new int [][]{{0, 1}, {1,1}}
                );

        aStar.display();
        aStar.process(); //apply A* Algorithm
        aStar.displayScores(); //display scores on grip
        aStar.displaySolution(); //display solution path;

        ArrayList<Cell> pathArr = new ArrayList<>(aStar.path);
        System.out.println("Before" + pathArr);
        Collections.reverse(pathArr);
        System.out.println("After" + pathArr);

        if (pathArr.size() > 1 && head.getX() < pathArr.get(1).x) return Direction.RIGHT.toString();
        else if (pathArr.size() == 1 && head.getX() < apples.get(0).getX()) return Direction.RIGHT.toString();
        if (pathArr.size() > 1 && head.getX() > pathArr.get(1).x) return Direction.LEFT.toString();
        else if (pathArr.size() == 1 && head.getX() > apples.get(0).getX()) return Direction.LEFT.toString();
        if (pathArr.size() > 1 && head.getY() > pathArr.get(1).y) return Direction.DOWN.toString();
        else if (pathArr.size() == 1 && head.getY() > apples.get(0).getY()) return Direction.DOWN.toString();
        if (pathArr.size() > 1 && head.getY() < pathArr.get(1).y) return Direction.UP.toString();
        else if (pathArr.size() == 1 && head.getY() < apples.get(0).getY()) return Direction.UP.toString();
        return "Hello world";
    }

//    public Point[][] getSnakeObs(List<Point> snake,List<Point> stones){
//        //add snake size to obs
//        List<Point> obstacles = new ArrayList<>(snake);
//        obstacles.add(stones.get(0));
//
//        Point [] ob = new Point[snake.size()];
//        for(int i = 0; i <= snake.size()-1; i++ ){
//            snakeArr[i] = snake.get(i);
//        }
//        //add stone as a obs
//
//        System.out.println("!!!!!" + Arrays.toString(snakeArr));
//
//        return snakeArr;
//    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                "http://46.101.224.244/codenjoy-contest/board/player/6hf6bh3dtllytzryc4er?code=8977979856950315301",
                new YourSolver(),
                new Board());
    }

}
