package com.marc;

import java.awt.Point;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Marc
 * @version 1.0
 */

public class Znake {

    private final static int MAX_X = 40;
    private final static int MAX_Y = 10;

    boolean rich; //True when player gets gold
    boolean event;
    private final Point player;
    private final Point gold;
    private final Point door;
    private final Point snake;

    {
        rich = false;
        event = false;
        gold = new Point();
        player = new Point();
        snake = new Point();
        door = new Point();
    }

    public static void main(String[] args){
        System.out.println("~Welcome to Znake!~");
        System.out.println("Press w,a,s or d");
        Znake znake = new Znake();
        znake.startGame();
    }

    public void startGame() {
        setSpawnsRandom();
        while (true) {
            createMap();
            controls();
            znakeAI();
            checkForEvents();
            if (event) return;
            clearTerminal();
        }
    }

    private void controls() {
        switch (new Scanner(System.in).next()) {
            case "w":
                player.y = Math.max(0, player.y - 1);
                break;
            case "s":
                player.y = Math.min(9, player.y + 1);
                break;
            case "a":
                player.x = Math.max(0, player.x - 1);
                if (player.x == 0) player.x = 39;
                break;
            case "d":
                player.x = Math.min(39, player.x + 1);
                if (player.x == 39) player.x = 0;
                break;
            default:
                System.out.println("Press w,a,s or d");
                break;
        }


    }

    private void createMap() {
        for (int y = 0; y < MAX_Y; y++) {
            for (int x = 0; x < MAX_X; x++) {
                Point p = new Point(x, y);
                if (player.equals(p)) {
                    System.out.print("P");
                } else if (snake.equals(p)) {
                    System.out.print("S");
                } else if (gold.equals(p)) {
                    System.out.print("$");
                } else if (door.equals(p)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private void znakeAI() {
        if (player.x < snake.x) {
            snake.x--;
        } else if (player.x > snake.x) {
            snake.x++;
        }
        if (player.y < snake.y) {
            snake.y--;
        } else if (player.y > snake.y) {
            snake.y++;
        }
    }

    private void checkForEvents() {
        if (rich && player.equals(door)) {
            System.out.println("Won");
            event = true;
            return;
        }
        if (player.equals(snake)) {
            System.out.println("You Lost");
            event = true;
            return;
        }
        if (player.equals(gold)) {
            rich = true;
            gold.setLocation(-1, -1);
        }
    }

    private void clearTerminal() {
        for (int i = 0; i < 60; i++) {
            System.out.println();
        }
    }

    private void setSpawnsRandom() {
        int[] x = new int[8];
        int[] y = new int[8];
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            x[i] = random.nextInt(MAX_X);
            y[i] = random.nextInt(MAX_Y);
        }
        door.setLocation(x[0], y[0]);
        gold.setLocation(x[1], y[1]);
        snake.setLocation(x[2], y[2]);
        player.setLocation(x[3], y[3]);
        if (player.distance(snake) < 9) {
            setSpawnsRandom();
        } else if (gold.equals(door)) {
            setSpawnsRandom();
        }

    }
}
