package com.shauryasen;

import java.util.*;

public class TicTacToe {
    static ArrayList<Integer> playerTurns = new ArrayList<Integer>();
    static ArrayList<Integer> cpuTurns = new ArrayList<Integer>();

    public static void main(String[] args) {
        int cpuPosition;
        int playerPosition;
        Random rand = new Random();

        // create an empty game board
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                              {'-', '+', '-', '+', '-'},
                              {' ', '|', ' ', '|', ' '},
                              {'-', '+', '-', '+', '-'},
                              {' ', '|', ' ', '|', ' '}};

    while (true){

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

        playerTurns.add(playerPosition);
        turn(playerPosition, gameBoard, true);

        // if someone won, then we can end the game.
        if (won(gameBoard)) {
            break;
        }

        // CPU TURN
        cpuPosition = rand.nextInt(9) + 1;
        // make sure that the position isn't taken
        while(playerTurns.contains(cpuPosition) || cpuTurns.contains(cpuPosition)) {
            cpuPosition = rand.nextInt(9) + 1;
        }
        cpuTurns.add(cpuPosition);
        turn(cpuPosition, gameBoard, false);

        // if someone won, then we can end the game.
        if (won(gameBoard)) {
            break;
        }

        // print out the board.
        printGameBoard(gameBoard);
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

    private static boolean won(char[][] gameBoard) {
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

        for (List cur : winPositions) {
            if(playerTurns.containsAll(cur)) {
                printGameBoard(gameBoard);
                System.out.println("congrats! you won!!");
                return true;
            } else if(cpuTurns.containsAll(cur)) {
                printGameBoard(gameBoard);
                System.out.println("The computer won :(");
                return true;
            } else if (playerTurns.size() + cpuTurns.size() == 9){
                printGameBoard(gameBoard);
                System.out.println("TIE GAME!");
                return true;
            }

        }

        return false;
    }

    private static void turn (int position, char[][] gameBoard, boolean playerTurn) {

        char symbol = playerTurn == true ? 'X' : 'O';

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
