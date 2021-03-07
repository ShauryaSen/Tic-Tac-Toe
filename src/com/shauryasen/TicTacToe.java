package com.shauryasen;

import java.util.*;
import java.util.ArrayList;


import static java.sql.Types.NULL;

public class TicTacToe {
    static ArrayList<Integer> playerTurns = new ArrayList<Integer>();
    static ArrayList<Integer> cpuTurns = new ArrayList<Integer>();
    static ArrayList<Integer> available = new ArrayList<Integer>();
    static ArrayList<Integer> availableAdder = new ArrayList<Integer>();
    static Integer addBack = 0;
    //List huh = Arrays.asList(1,2,3,4,5,6,7,8,9);
    // static List<Integer> available = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    /*
    static int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    static List available = Arrays.asList(arr);
    */

    // create an empty game board
    static char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '}};

    public static void main(String[] args) {
        // this is so dumb but add all the numbers to available NOTHING ELSE WORKED
        available.add(1);
        available.add(2);
        available.add(3);
        available.add(4);
        available.add(5);
        available.add(6);
        available.add(7);
        available.add(8);
        available.add(9);

        int cpuPosition;
        int playerPosition;

        while (true){

            // CPU TURN

            //cpuPosition = rand.nextInt(9) + 1;
            //while(playerTurns.contains(cpuPosition) || cpuTurns.contains(cpuPosition)) {
            //    cpuPosition = rand.nextInt(9) + 1;
            //}

            // ask the cpu to create a new position that takes all the moves possible (to find the best move it can make)
            cpuPosition = findBestMove();

            cpuTurns.add(cpuPosition);
            available.remove((Integer) cpuPosition);
            turn(cpuPosition, "cpu");

            // if someone won, then we can end the game.
            if (won(false) != 10) {
                break;
            }

            // print the board
            printGameBoard(gameBoard);

            // PLAYER TURN
            // ask for user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter number 1-9 corresponding with the game board: ");
            playerPosition = scanner.nextInt();
            // make sure that the position isn't taken.
            while(playerTurns.contains(playerPosition) || cpuTurns.contains(playerPosition)) {
                System.out.print("That position is already taken! Try another: ");
                playerPosition = scanner.nextInt();
            }

            // this adds all the turns that the player has done, all the turns so far, and deletes from available slots
            playerTurns.add(playerPosition);
            available.remove((Integer) playerPosition);
            turn(playerPosition, "human");

            // if someone won, then we can end the game.
            if (won(false) != 10) {
                break;
            }

        }

    }

    private static int findBestMove() {
        int score;
        int bestScore = -1000;
        int bestMove = NULL;

        // loop through every immediate move we can make at the current board.
        for (Integer i = 1; i < 9; i++) {

            if (available.contains(i)) {

                // try all the possible spots
                turn(i, "cpu");
                cpuTurns.add(i);
                available.remove(i);

                score = (minimax(0, false));

                // undo the changes
                cpuTurns.remove(i);
                available.add(i);
                Collections.sort(available);

                turn(i, "erase");

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;

                }
            }

        }

        return bestMove;
    }

    private static int minimax(int depth, boolean maximizingPlayer) {
        if (won(true) != 10) {
            return (won(true));
        }

        int score;
        int bestScore;

        if (maximizingPlayer) {
            bestScore = -1000;

            for (Integer i = 1; i < 9; i++) {
                // check if each spot in the board is available - if so then evaluate it
                if (available.contains(i)) {

                    // try all the possible spots
                    turn(i, "cpu");
                    cpuTurns.add(i);
                    available.remove(i);

                    score = minimax(depth + 1, false);

                    // undo the changes
                    cpuTurns.remove(i);
                    available.add(i);
                    Collections.sort(available);

                    turn(i, "erase");

                    bestScore = Math.max(score, bestScore);

                }

            }
            return bestScore;

        } else {
            bestScore = 1000;
            for (Integer i = 1; i < 9; i++) {

                if (available.contains(i)) {

                    // try all the possible spots
                    turn(i, "human");
                    playerTurns.add(i);
                    available.remove(i);

                    score = minimax(depth + 1, true);

                    // undo the changes
                    playerTurns.remove(i);
                    available.add(i);
                    Collections.sort(available);

                    turn(i, "erase");

                    bestScore = Math.min(score, bestScore);

                }

            }

            return bestScore;
        }
    }


    private static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard ) {
            for (char cur : row) {
                System.out.print(cur);
            }
            System.out.println();
        }
    }

    private static int won(boolean fake) {
        int tie;
        // store all win conditions
        List topRow = Arrays.asList(1,2,3);
        List midRow = Arrays.asList(4,5,6);
        List botRow = Arrays.asList(7,8,9);
        List leftCol = Arrays.asList(1,4,7);
        List midCol = Arrays.asList(2,5,8);
        List rightCol = Arrays.asList(3,6,9);
        List cross1 = Arrays.asList(1,5,9);
        List cross2 = Arrays.asList(7,5,3);

        // creates a loopable list with all the win positions
        List<List> winPositions = new ArrayList<List>();
        winPositions.add(topRow);
        winPositions.add(midRow);
        winPositions.add(botRow);
        winPositions.add(leftCol);
        winPositions.add(midCol);
        winPositions.add(rightCol);
        winPositions.add(cross1);
        winPositions.add(cross2);

        if (fake == true) {
            for (List cur : winPositions) {
                if (playerTurns.containsAll(cur)) {
                    return -1;
                } else if (cpuTurns.containsAll(cur)) {
                    return 1;
                }
            }
            if (playerTurns.size() + cpuTurns.size() == 9) {
                return 0;
            }

        } else {
            for (List cur : winPositions) {
                if(playerTurns.containsAll(cur)) {
                    printGameBoard(gameBoard);
                    System.out.println("congrats! you won!!");
                    return -1;
                } else if(cpuTurns.containsAll(cur)) {
                    printGameBoard(gameBoard);
                    System.out.println("The computer won :(");
                    return 1;
                }
            }
            if (playerTurns.size() + cpuTurns.size() == 9) {
                printGameBoard(gameBoard);
                System.out.println("TIE GAME!");
                return 0;

            }

        }

        return 10;
    }

    private static void turn (int position, String player) {

        char symbol;

        if (player.equals("human")) {
            symbol = 'X';
        } else if (player.equals("cpu")) {
            symbol = 'O';
        } else {
            symbol = ' ';
        }

        switch (position) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }

        //System.out.println("not a valid number");

    }


}
