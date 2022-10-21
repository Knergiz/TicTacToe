import javax.xml.bind.SchemaOutputResolver;
import java.lang.invoke.SwitchPoint;
import java.util.Scanner;

public class TicTacToe {


    public enum playerType{
        player1, player2
    }

    public enum stoneType{
        x,o,empty
    }

    public enum gameStates{
        playing, victory, draw
    }


    public static stoneType playerToStone(playerType player){
        switch (player){
            case player1: return stoneType.x;
            case player2: return stoneType.o;
        }
        return stoneType.empty;
    }

    public static String stoneToChar(stoneType stone){
        switch (stone){
            case x: return "X";
            case o: return "O";
            case empty: return ".";
        }
        return "";
    }

    public static void main(String[] args) {
        gameStates state = gameStates.playing;
        boolean isDraw = true;
        playerType currentPlayer = playerType.player1;
        Scanner scanner = new Scanner(System.in);

        //board
        stoneType[][] board = new stoneType[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = stoneType.empty;
            }
        }

        while (state == gameStates.playing){
            int x = 0;
            int y;

            switch (currentPlayer){
                case player1: System.out.println("Player 1 turn");
                    break;
                case player2: System.out.println("Player 2 turn");
                    break;
            }

            do {
                //input
                System.out.println("Please enter your X-Coordinate:");
                x = scanner.nextInt() - 1;
                System.out.println("Please enter your Y-Coordinate:");
                y = scanner.nextInt() - 1;

                if (x > 2 || y > 2 || y < 0 || x < 0){
                    System.out.println("Please enter valid coordinates");
                } else if (board[x][y] != stoneType.empty){
                    System.out.println("Please enter an empty space");
                }

            } while (x > 2 || y > 2 || y < 0 || x < 0 || board[x][y] != stoneType.empty);

            //place unit
            board[x][y] = playerToStone(currentPlayer);

            //draw screen
            for (int i = board.length-1; i >= 0; i--) {
                for (int j = 0; j < board[i].length; j++) {
                    System.out.print(stoneToChar(board[j][i]) + " ");
                }
                System.out.println("");
            }

            //check if game is over
            //horizontal Check
            for (int i = 0; i < board.length; i++) {
                stoneType firstStoneOfRow = board[i][0];
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == stoneType.empty) break;
                    if (board[i][j] != firstStoneOfRow) break;
                    if (j == board[i].length-1) state = gameStates.victory;
                }
            }

            //vertical check
            for (int i = 0; i < board.length; i++) {
                stoneType firstStoneOfColumn = board[0][i];
                for (int j = 0; j < board[i].length; j++) {
                    if (board[j][i] == stoneType.empty) break;
                    if (board[j][i] != firstStoneOfColumn) break;
                    if (j == board[i].length-1) state = gameStates.victory;
                }
            }

            stoneType btmLeft = board[0][0];
            //diagonal (Bottom left to top right)
            if (btmLeft != stoneType.empty && btmLeft == board[1][1] && btmLeft == board[2][2]) state = gameStates.victory;;

            stoneType topLeft = board[0][2];
            //diagonal (Top left to bottom right)
            if (topLeft != stoneType.empty && topLeft == board[1][1] && topLeft == board[2][0]) state = gameStates.victory;

            if (state != gameStates.victory){
                state = gameStates.draw;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j] == stoneType.empty){
                            state = gameStates.playing;
                            break;
                        }
                    }
                }
            }

            if (state == gameStates.playing) {
                switch (currentPlayer) {
                    case player1:
                        currentPlayer = playerType.player2;
                        break;
                    case player2:
                        currentPlayer = playerType.player1;
                        break;
                }
            }
        }

        switch (state){
            case victory:
                System.out.println("Player " + (currentPlayer == playerType.player1? "1" : "2") + " wins");
                break;
            case draw:
                System.out.println("Draw");
                break;
            case playing:
                System.out.println("ERROR");
                break;
        }



    }
}
