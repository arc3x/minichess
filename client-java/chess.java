import java.util.Vector;
import java.util.Stack;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class chess {
    //used for unit tests
    public static void main(String [] args) {
		System.out.println("test");

		//preset();
        //System.out.println(moveGreedy());
        //undo();
        //System.out.println(moveNegamax(2,9));
		//print_board();
		test_calcMaxAlphaBetaTime();
		test_calcAvgAlphaBetaTransTime();
		//test_calcAvgAlphaBetaTime();
		//test_calcAvgNegamaxTime();
		//test_calcMaxNegamaxTime();
        //test_playGames();
        //reset();
        //System.out.println(moveNegamaxAlphabetaTrans(5,999));
	}
    
	//BEGIN: TESTING FUNCTIONS
    
    
    
    //repeadedly playes games. used to measure running time of a game. Used for tuning AI to the chess clock
    //exit condition is chess clock expiration
    //may loop forever
    public static void test_playGames() {
        System.out.println("Playing many many games");               
        
        while (true) {
            reset();
            double white_time = 300000;
            double black_time = 300000;
            while (winner() == '?') { 
                if (side == 'W') {
                    long start_time = System.nanoTime();
                    moveNegamaxWrapper((int)white_time);
                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time)/1e6;
                    white_time -= difference;
                    if (white_time <= 0) {
                        print_board();
                        System.out.println("Last move took: "+difference);
                        System.out.println("White Clock: "+white_time);
                        System.out.println("Black Clock: "+black_time);
                        System.out.println("White player overtime!");
                        return;
                    }
                } else if (side == 'B') {
                    long start_time = System.nanoTime();
                    moveNegamaxWrapper((int)black_time);
                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time)/1e6;
                    black_time -= difference;
                    if (black_time <= 0) {
                        print_board();
                        System.out.println("Last move took: "+difference);
                        System.out.println("White Clock: "+white_time);
                        System.out.println("Black Clock: "+black_time);
                        System.out.println("Black player overtime!");
                        return;
                    }
                }
                //print_board();
                //System.out.println("White Clock: "+white_time);
                //System.out.println("Black Clock: "+black_time);
                //System.out.println();                
            }
            print_board();
            System.out.println("Winner: "+winner());
            System.out.println("White Clock: "+white_time);
            System.out.println("Black Clock: "+black_time);
            System.out.println();
        }                    
    }
    
    //calculates average times for several different depths of negamax searches. this data is used to manually tune the AI
    public static void test_calcAvgAlphaBetaTransTime() {
        System.out.println("Calculating avearage alphabeta trans negamax time");
            
        for (int i=3; i<=6; i++) {
            reset();
            int count=0;
            double time=0;
            while (winner() == '?') {            
                long start_time = System.nanoTime();
                moveNegamaxAlphabetaTrans(i,999);
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                time += difference;
                count++;                    
            }
            System.out.println("Depth "+i+" Average runtime: "+(time/count)+" milliseconds");  
        }                                
    }
    
    //calculates average times for several different depths of negamax searches. this data is used to manually tune the AI
    public static void test_calcAvgNegamaxTime() {
        System.out.println("Calculating avearage negamax time");
            
        for (int i=3; i<=6; i++) {
            reset();
            int count=0;
            double time=0;
            while (winner() == '?') {            
                long start_time = System.nanoTime();
                moveNegamax(i,999);
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                time += difference;
                count++;                    
            }
            System.out.println("Depth "+i+" Average runtime: "+(time/count)+" milliseconds");  
        }                                
    }
    
    //calculates max times for several different depths of negamax searches. this data is used to manually tune the AI
    public static void test_calcMaxNegamaxTime() {
        System.out.println("Calculating maximum negamax time");
        
        for (int i=3; i<=7; i++) {
            reset();
            double time=0;
            while (winner() == '?') {            
                long start_time = System.nanoTime();
                moveNegamax(i,999);
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                if (difference > time)
                    time = difference;
            }
            System.out.println("Depth 4 Max runtime: "+(time)+" milliseconds");
        }                                 
    }
    
    public static void test_calcAvgAlphaBetaTime() {
        System.out.println("Calculating avearage alphabeta negamax time");
            
        for (int i=3; i<=7; i++) {
            reset();
            int count=0;
            double time=0;
            while (winner() == '?') {            
                long start_time = System.nanoTime();
                moveAlphabeta(i,999);
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                time += difference;
                count++;                    
            }
            System.out.println("Depth "+i+" Average runtime: "+(time/count)+" milliseconds");  
        }                                
    }
    
    //calculates max times for several different depths of alphabeta negamax searches. this data is used to manually tune the AI
    public static void test_calcMaxAlphaBetaTime() {
        System.out.println("Calculating maximum negamax time");              

        for (int i=3; i<=15; i++) {
            reset();
            int count=0;
            double time=0;
            while (winner() == '?') {            
                long start_time = System.nanoTime();
                moveAlphabeta(i,999);
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                if (difference > time)
                    time = difference;
            }
            System.out.println("Depth "+i+" Max runtime: "+time+" milliseconds");    
        }            
                  
    }
    
	

	public static void preset() {
		// reset the state of the game / your internal variables - note that this function is highly dependent on your implementation
		turn = 30;
		side = 'B';
		board[0][0] = '.';
		board[0][1] = 'k';
		board[0][2] = 'q';
		board[0][3] = 'q';
		board[0][4] = '.';

		board[1][0] = 'R';
		board[1][1] = '.';
		board[1][2] = 'Q';
		board[1][3] = '.';
		board[1][4] = '.';

		board[2][0] = '.';
		board[2][1] = '.';
		board[2][2] = '.';
		board[2][3] = 'N';
		board[2][4] = '.';

		board[3][0] = '.';
		board[3][1] = '.';
		board[3][2] = 'P';
		board[3][3] = 'P';
		board[3][4] = 'q';

		board[4][0] = '.';
		board[4][1] = '.';
		board[4][2] = 'B';
		board[4][3] = '.';
		board[4][4] = '.';

		board[5][0] = '.';
		board[5][1] = '.';
		board[5][2] = '.';
		board[5][3] = '.';
		board[5][4] = 'K';
	}
	//END: TESTING FUNCTIONS

	private static int turn = 1;
	private static char side = 'W';
	private static char[][] board = new char[6][5];
    private static Stack<String> board_stack = new Stack<String>();
    private static HashMap<String,tableEntry> trans = new HashMap<String, tableEntry>();
    
    //START: HELPER FUNCTIONS
	public static void print_board() {
		System.out.print(turn+" "+side+"  ("+eval()+")\n");
		for (int i=0; i<6; i++) {
			for (int j=0; j<5; j++) {
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
	}
    //END: HELPER FUNCTIONS

    public static int piece_value(int i, int j) {
        switch (board[i][j]) {
            //Pawn
            case 'p':
                //encourage pawn promotion
                if (i == 5) {
                    return 6;
                }
                return 1;
            case 'P':
                //encourage pawn promotion
                if (i == 0) {
                    return 6;
                }
                return 1;                
            //King
            case 'k':
            case 'K':
                return 100000;         
            //Queen
            case 'q':
            case 'Q':
                return 10;
            //Bishop    
            case 'b':
            case 'B':
                return 3;
            //Rook    
            case 'r':
            case 'R':
                return 4;
            //Knight    
            case 'n':
            case 'N':
                return 3;
            default:
                return 0;
        }
    }

	public static void reset() {
		// reset the state of the game / your internal variables - note that this function is highly dependent on your implementation
        board_stack = new Stack<String>();
		turn = 1;
		side = 'W';
		board[0][0] = 'k';
		board[0][1] = 'q';
		board[0][2] = 'b';
		board[0][3] = 'n';
		board[0][4] = 'r';

		board[1][0] = 'p';
		board[1][1] = 'p';
		board[1][2] = 'p';
		board[1][3] = 'p';
		board[1][4] = 'p';

		board[2][0] = '.';
		board[2][1] = '.';
		board[2][2] = '.';
		board[2][3] = '.';
		board[2][4] = '.';

		board[3][0] = '.';
		board[3][1] = '.';
		board[3][2] = '.';
		board[3][3] = '.';
		board[3][4] = '.';

		board[4][0] = 'P';
		board[4][1] = 'P';
		board[4][2] = 'P';
		board[4][3] = 'P';
		board[4][4] = 'P';

		board[5][0] = 'R';
		board[5][1] = 'N';
		board[5][2] = 'B';
		board[5][3] = 'Q';
		board[5][4] = 'K';
	}
	
	public static String boardGet() {
		// return the state of the game - one example is given below - note that the state has exactly 40 or 41 characters

		String strOut = "";
		strOut += turn+" "+side+"\n";
		for (int i=0; i<6; i++) {
			for (int j=0; j<5; j++) {
				strOut += board[i][j];
			}
			strOut += "\n";
		}

		return strOut;
	}
	
	public static void boardSet(String strIn) {
		// read the state of the game from the provided argument and set your internal variables accordingly - note that the state has exactly 40 or 41 characters
        //System.out.println("boardSet Called: "+strIn);
        
		String[] split_str = strIn.split("\n");
		//WARNING: turn is a char make sure two digit turns parse correctly
		turn = Character.getNumericValue(split_str[0].charAt(0));
		int turn_add = Character.getNumericValue(split_str[0].charAt(1));
		if (turn_add != -1) {
			turn *= 10;
			turn += turn_add;
            side = split_str[0].charAt(3);
		} else {
            side = split_str[0].charAt(2);
        }
		board[0] = split_str[1].toCharArray();
		board[1] = split_str[2].toCharArray();
		board[2] = split_str[3].toCharArray();
		board[3] = split_str[4].toCharArray();
		board[4] = split_str[5].toCharArray();
		board[5] = split_str[6].toCharArray();
        
        //print_board();
	}
	
	public static char winner() {
		// determine the winner of the current state of the game and return '?' or '=' or 'W' or 'B' - note that we are returning a character and not a string
		//print_board();



        boolean white = false;
		boolean black = false;
		for (int i=0; i<6; i++) {
			for (int j=0; j<5; j++) {
				if (board[i][j]=='k')
					black = true;
				if (board[i][j]=='K')
					white = true;
			}
		}

        //check for turn limit
        if (turn > 40) {
            //check special case of black won on last move
            if (turn==41 && black && !white) {
                return 'B';
            }
            return '=';
        }

		if (white && black) {
            //System.out.println("?");
            return '?';
        }
		if (white) {
            //System.out.println("W");
            return 'W';
        }
		if (black) {
            //System.out.println("B");
            return 'B';
        }
		else {
            //System.out.println("=");
            return '=';
        }
	}

    
	public static boolean isValid(int intX, int intY) {
		if (intX < 0) {
			return false;
			
		} else if (intX > 4) {
			return false;
			
		}
		
		if (intY < 0) {
			return false;
			
		} else if (intY > 5) {
			return false;
			
		}
		
		return true;
	}
	
    //CHANGED: isValid now is with respect to the board array
	public static boolean isValidArrayMove(int intY, int intX) {
		if (intX < 0) {
			return false;
			
		} else if (intX > 4) {
			return false;
			
		}
		
		if (intY < 0) {
			return false;
			
		} else if (intY > 5) {
			return false;
			
		}
		
		return true;
	}
    
	public static boolean isEnemy(char charPiece) {
		// with reference to the state of the game, return whether the provided argument is a piece from the side not on move - note that we could but should not use the other is() functions in here but probably
		if (charPiece == '.') {
            return false;
        }
        if (side == 'W') {
			return !Character.isUpperCase(charPiece);
		}
		else if (side == 'B') {
			return Character.isUpperCase(charPiece);
		}
		return false; //just for java
	}

	
	public static boolean isOwn(char charPiece) {
		// with reference to the state of the game, return whether the provided argument is a piece from the side on move - note that we could but should not use the other is() functions in here but probably
		if (charPiece == '.') {
            return false;
        }
        if (side == 'W') {
			return Character.isUpperCase(charPiece);
		}
		else if (side == 'B') {
			return !Character.isUpperCase(charPiece);
		}
		return false;
	}
	
	public static boolean isNothing(char charPiece) {
		// return whether the provided argument is not a piece / is an empty field - note that we could but should not use the other is() functions in here but probably
		return charPiece == '.';
	}
	
	public static int eval() {
		// with reference to the state of the game, return the the evaluation score of the side on move - note that positive means an advantage while negative means a disadvantage
		int w_score = 0;
        int b_score = 0;
        for (int i=0; i<6; i++) {
            for (int j=0; j<5; j++) {
                if (board[i][j] != '.') {
                    //is it a white piece?
                    if (Character.isUpperCase(board[i][j])) {
                        w_score += piece_value(i,j);
                    }
                    //else its a black piece
                    else {
                        b_score += piece_value(i,j);
                    }
                }
            }
        }
        
        //return a score with respect to white
        if (side == 'W') {
            return w_score-b_score;
        } 
        //else return a score with respect to black
        else {
            return b_score-w_score;
        }       
	}

    //  converts array positions to move strings
    //  (4,0,3,0) -> "a2-a3"
    //    array columns translate to letter at the start of the position eg 'a'
    //    array rows translate to an oppositely enumerated list of integers that
    //      occupy the second position of the move eg '2'
    public static String array_to_board(int si, int sj, int fi, int fj) {
        String strOut = "";
        switch (sj) {
            case 0:
                strOut += 'a';
                break;
            case 1:
                strOut += 'b';
                break;
            case 2:
                strOut += 'c';
                break;
            case 3:
                strOut += 'd';
                break;
            case 4:
                strOut += 'e';
                break;
            case 5:
                strOut += 'f';
                break;
        }
        switch (si) {
            case 0:
                strOut += '6';
                break;
            case 1:
                strOut += '5';
                break;
            case 2:
                strOut += '4';
                break;
            case 3:
                strOut += '3';
                break;
            case 4:
                strOut += '2';
                break;
            case 5:
                strOut += '1';
                break;
        }
        strOut += "-";
        switch (fj) {
            case 0:
                strOut += 'a';
                break;
            case 1:
                strOut += 'b';
                break;
            case 2:
                strOut += 'c';
                break;
            case 3:
                strOut += 'd';
                break;
            case 4:
                strOut += 'e';
                break;
            case 5:
                strOut += 'f';
                break;
        }
        switch (fi) {
            case 0:
                strOut += '6';
                break;
            case 1:
                strOut += '5';
                break;
            case 2:
                strOut += '4';
                break;
            case 3:
                strOut += '3';
                break;
            case 4:
                strOut += '2';
                break;
            case 5:
                strOut += '1';
                break;
        }
        strOut += '\n';
        return strOut;
    }

    public static Vector<String> piece_moves(int i, int j) {
        Vector<String> strOut = new Vector<String>();
        //System.out.println("piece_moves called : "+board[i][j]);        
        //utility defs
        boolean flag_ul = true;
        boolean flag_u = true;
        boolean flag_ur = true;
        boolean flag_r = true;
        boolean flag_dr = true;
        boolean flag_d = true;
        boolean flag_dl = true;
        boolean flag_l = true;
        int step = 1;
        switch (board[i][j]) {                        
            //White Pawn
            case 'P':
                //System.out.println("piece_moves called - pawn");
                //can pawn move up?
                if (isValidArrayMove(i-1,j) && isNothing(board[i-1][j])) {
                    strOut.add(array_to_board(i,j,i-1,j));
                }
                //can pawn attach up-left?
                if (isValidArrayMove(i-1, j-1)) {
                    if (isEnemy(board[i-1][j-1])) {
                        strOut.add(array_to_board(i,j,i-1,j-1));
                    }
                }
                //can pawn attach up-right?
                if (isValidArrayMove(i-1, j+1)) {
                    if (isEnemy(board[i-1][j+1])) {
                        strOut.add(array_to_board(i,j,i-1,j+1));
                    }
                }
                break;

            //Black Pawn
            case 'p':
                //System.out.println("piece_moves called - pawn");
                //can pawn move down?
                if (isValidArrayMove(i+1,j) && isNothing(board[i+1][j])) {
                    strOut.add(array_to_board(i,j,i+1,j));
                }
                //can pawn attach down-left?
                if (isValidArrayMove(i+1, j-1)) {
                    if (isEnemy(board[i+1][j-1])) {
                        strOut.add(array_to_board(i,j,i+1,j-1));
                    }
                }
                //can pawn attach down-right?
                if (isValidArrayMove(i+1, j+1)) {
                    if (isEnemy(board[i+1][j+1])) {
                        strOut.add(array_to_board(i,j,i+1,j+1));
                    }
                }
                break;

            //King
            case 'k':
            case 'K':
                //System.out.println("piece_moves called - king");
                //can king attack/move up-left
                if (isValidArrayMove(i-1,j-1)) {
                    if (!isOwn(board[i-1][j-1])) {
                        strOut.add(array_to_board(i,j,i-1,j-1));
                    }
                }
                //can king attack/move up
                if (isValidArrayMove(i-1,j)) {
                    if (!isOwn(board[i-1][j])) {
                        strOut.add(array_to_board(i,j,i-1,j));
                    }
                }
                //can king attack/move up-right
                if (isValidArrayMove(i-1,j+1)) {
                    if (!isOwn(board[i-1][j+1])) {
                        strOut.add(array_to_board(i,j,i-1,j+1));
                    }
                }
                //can king attack/move right
                if (isValidArrayMove(i,j+1)) {
                    if (!isOwn(board[i][j+1])) {
                        strOut.add(array_to_board(i,j,i,j+1));
                    }
                }
                //can king attack/move down-right
                if (isValidArrayMove(i+1,j+1)) {
                    if (!isOwn(board[i+1][j+1])) {
                        strOut.add(array_to_board(i,j,i+1,j+1));
                    }
                }
                //can king attack/move down
                if (isValidArrayMove(i+1,j)) {
                    if (!isOwn(board[i+1][j])) {
                        strOut.add(array_to_board(i,j,i+1,j));
                    }
                }
                //can king attack/move down-left
                if (isValidArrayMove(i+1,j-1)) {
                    if (!isOwn(board[i+1][j-1])) {
                        strOut.add(array_to_board(i,j,i+1,j-1));
                    }
                }
                //can king attack/move left
                if (isValidArrayMove(i,j-1)) {
                    if (!isOwn(board[i][j-1])) {
                        strOut.add(array_to_board(i,j,i,j-1));
                    }
                }
                break;
            //Queen
            case 'q':
            case 'Q':
                //System.out.println("piece_moves called - queen");
                //look around - mark flags false when  wall or enemy is hit
                flag_ul = true;
                flag_u = true;
                flag_ur = true;
                flag_r = true;
                flag_dr = true;
                flag_d = true;
                flag_dl = true;
                flag_l = true;
                step = 1;
                while (flag_ul || flag_u || flag_ur || flag_r || flag_dr || flag_d || flag_dl || flag_dl || flag_l) {
                    //ul attack/move
                    if (flag_ul && isValidArrayMove(i-step,j-step) && !isOwn(board[i-step][j-step])) {
                        strOut.add(array_to_board(i,j,i-step,j-step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i-step][j-step])) {
                            flag_ul = false;
                        }
                    } else {
                        flag_ul = false;
                    }
                    //u attack/move
                    if (flag_u && isValidArrayMove(i-step,j) && !isOwn(board[i-step][j])) {
                        strOut.add(array_to_board(i,j,i-step,j));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i-step][j])) {
                            flag_u = false;
                        }
                    } else {
                        flag_u = false;
                    }
                    //ur attack/move
                    if (flag_ur && isValidArrayMove(i-step,j+step) && !isOwn(board[i-step][j+step])) {
                        strOut.add(array_to_board(i,j,i-step,j+step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i-step][j+step])) {
                            flag_ur = false;
                        }
                    } else {
                        flag_ur = false;
                    }
                    //r attack/move
                    if (flag_r && isValidArrayMove(i,j+step) && !isOwn(board[i][j+step])) {
                        strOut.add(array_to_board(i,j,i,j+step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i][j+step])) {
                            flag_r = false;
                        }
                    } else {
                        flag_r = false;
                    }
                    //dr attack/move
                    if (flag_dr && isValidArrayMove(i+step,j+step) && !isOwn(board[i+step][j+step])) {
                        strOut.add(array_to_board(i,j,i+step,j+step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i+step][j+step])) {
                            flag_dr = false;
                        }
                    } else {
                        flag_dr = false;
                    }
                    //d attack/move
                    if (flag_d && isValidArrayMove(i+step,j) && !isOwn(board[i+step][j])) {
                        strOut.add(array_to_board(i,j,i+step,j));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i+step][j])) {
                            flag_d = false;
                        }
                    } else {
                        flag_d = false;
                    }
                    //dl attack/move
                    if (flag_dl && isValidArrayMove(i+step,j-step) && !isOwn(board[i+step][j-step])) {
                        strOut.add(array_to_board(i,j,i+step,j-step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i+step][j-step])) {
                            flag_dl = false;
                        }
                    } else {
                        flag_dl = false;
                    }
                    //l attack/move
                    if (flag_l && isValidArrayMove(i,j-step) && !isOwn(board[i][j-step])) {
                        strOut.add(array_to_board(i,j,i,j-step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i][j-step])) {
                            flag_l = false;
                        }
                    } else {
                        flag_l = false;
                    }
                    step++;
                }
                break;
            //Bishop
            case 'b':
            case 'B':
                //System.out.println("piece_moves called - bishop");
                flag_ul = true;
                flag_u = true;
                flag_ur = true;
                flag_r = true;
                flag_dr = true;
                flag_d = true;
                flag_dl = true;
                flag_l = true;
                step = 1;
                while (flag_ul || flag_u || flag_ur || flag_r || flag_dr || flag_d || flag_dl || flag_dl || flag_l) {
                    //System.out.println("Flags: "+flag_ul+""+flag_u+""+flag_ur+""+flag_r+""+flag_dr+""+flag_d+""+flag_dl+""+flag_l);
                    
                    //ul attack/move
                    if (flag_ul && isValidArrayMove(i-step,j-step) && !isOwn(board[i-step][j-step])) {
                        strOut.add(array_to_board(i,j,i-step,j-step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i-step][j-step])) {
                            flag_ul = false;
                        }
                    } else {
                        flag_ul = false;
                    }
                    //u move
                    if (flag_u && isValidArrayMove(i-step,j) && isNothing(board[i-step][j])) {
                        strOut.add(array_to_board(i,j,i-step,j));                        
                        flag_u = false;                        
                    } else { flag_u = false; }
                    //ur attack/move
                    if (flag_ur && isValidArrayMove(i-step,j+step) && !isOwn(board[i-step][j+step])) {
                        strOut.add(array_to_board(i,j,i-step,j+step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i-step][j+step])) {
                            flag_ur = false;
                        }
                    } else {
                        flag_ur = false;
                    }
                    //r move
                    if (flag_r && isValidArrayMove(i,j+step) && isNothing(board[i][j+step])) {
                        strOut.add(array_to_board(i,j,i,j+step));                        
                        flag_r = false;
                    } else { flag_r = false; }
                    //dr attack/move
                    if (flag_dr && isValidArrayMove(i+step,j+step) && !isOwn(board[i+step][j+step])) {
                        strOut.add(array_to_board(i,j,i+step,j+step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i+step][j+step])) {
                            flag_dr = false;
                        }
                    } else {
                        flag_dr = false;
                    }
                    //d move
                    if (flag_d && isValidArrayMove(i+step,j) && isNothing(board[i+step][j])) {
                        strOut.add(array_to_board(i,j,i+step,j));                        
                        flag_d = false;
                    } else { flag_d = false; }
                    //dl attack/move
                    if (flag_dl && isValidArrayMove(i+step,j-step) && !isOwn(board[i+step][j-step])) {
                        strOut.add(array_to_board(i,j,i+step,j-step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i+step][j-step])) {
                            flag_dl = false;
                        }
                    } else {
                        flag_dl = false;
                    }
                    //l move
                    if (flag_l && isValidArrayMove(i,j-step) && isNothing(board[i][j-step])) {
                        strOut.add(array_to_board(i,j,i,j-step));                        
                        flag_l = false;
                    } else { flag_l = false; }                                    
                    step++;
                }
                break;
            //Rook
            case 'r':
            case 'R':
                //System.out.println("piece_moves called - rook");
                flag_u = true;
                flag_r = true;
                flag_d = true;
                flag_l = true;
                step = 1;
                while (flag_u || flag_r || flag_d || flag_l) {                    
                    //u attack/move
                    if (flag_u && isValidArrayMove(i-step,j) && !isOwn(board[i-step][j])) {
                        strOut.add(array_to_board(i,j,i-step,j));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i-step][j])) {
                            flag_u = false;
                        }
                    } else {
                        flag_u = false;
                    }                    
                    //r attack/move
                    if (flag_r && isValidArrayMove(i,j+step) && !isOwn(board[i][j+step])) {
                        strOut.add(array_to_board(i,j,i,j+step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i][j+step])) {
                            flag_r = false;
                        }
                    } else {
                        flag_r = false;
                    }                    
                    //d attack/move
                    if (flag_d && isValidArrayMove(i+step,j) && !isOwn(board[i+step][j])) {
                        strOut.add(array_to_board(i,j,i+step,j));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i+step][j])) {
                            flag_d = false;
                        }
                    } else {
                        flag_d = false;
                    }                    
                    //l attack/move
                    if (flag_l && isValidArrayMove(i,j-step) && !isOwn(board[i][j-step])) {
                        strOut.add(array_to_board(i,j,i,j-step));
                        //if we hit an enemy we wont look any further
                        if (isEnemy(board[i][j-step])) {
                            flag_l = false;
                        }
                    } else {
                        flag_l = false;
                    } 
                    step++;
                }
                break;
            //knight
            case 'n':
            case 'N':
                //uul
                if (isValidArrayMove(i-2,j-1) && !isOwn(board[i-2][j-1])) {
                    strOut.add(array_to_board(i,j,i-2,j-1));
                }
                //uur
                if (isValidArrayMove(i-2,j+1) && !isOwn(board[i-2][j+1])) {
                    strOut.add(array_to_board(i,j,i-2,j+1));
                }
                //ull
                if (isValidArrayMove(i-1,j-2) && !isOwn(board[i-1][j-2])) {
                    strOut.add(array_to_board(i,j,i-1,j-2));
                }
                //urr
                if (isValidArrayMove(i-1,j+2) && !isOwn(board[i-1][j+2])) {
                    strOut.add(array_to_board(i,j,i-1,j+2));
                }
                //ddl
                if (isValidArrayMove(i+2,j-1) && !isOwn(board[i+2][j-1])) {
                    strOut.add(array_to_board(i,j,i+2,j-1));
                }
                //ddr
                if (isValidArrayMove(i+2,j+1) && !isOwn(board[i+2][j+1])) {
                    strOut.add(array_to_board(i,j,i+2,j+1));
                }
                //dll
                if (isValidArrayMove(i+1,j-2) && !isOwn(board[i+1][j-2])) {
                    strOut.add(array_to_board(i,j,i+1,j-2));
                }
                //drr
                if (isValidArrayMove(i+1,j+2) && !isOwn(board[i+1][j+2])) {
                    strOut.add(array_to_board(i,j,i+1,j+2));
                }
        }       

        return strOut;
    }

	public static Vector<String> moves() {
		// with reference to the state of the game and return the possible moves - one example is given below - note that a move has exactly 6 characters
        Vector<String> strOut = new Vector<String>();
        for (int i=0; i<6; i++) {
            for (int j=0; j<5; j++) {
                if (isOwn(board[i][j]))
                    strOut.addAll(piece_moves(i, j));
            }
        }    
		
		return strOut;
	}
	
	public static Vector<String> movesShuffled() {
		// with reference to the state of the game, determine the possible moves and shuffle them before returning them - note that you can call the chess.moves() function in here
		Vector<String> strOut = new Vector<String>();
        strOut = moves();
        //System.out.println(strOut);
        Collections.shuffle(strOut);
		return strOut;
	}
	
	public static Vector<String> movesEvaluated() {
		// with reference to the state of the game, determine the possible moves and sort them in order of an increasing evaluation score before returning them - note that you can call the chess.moves() function in here
		Vector<String> strOut = new Vector<String>();
        strOut = moves();
        Collections.sort(strOut, new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    move(lhs);
                    int lhs_eval = eval();                   
                    undo();
                    move(rhs);
                    int rhs_eval = eval();                    
                    undo();
                    return lhs_eval-rhs_eval;
                }
            });
		return strOut;
	}
	
    public static void board_to_array(String charIn, int[] mov) {
        char cs = charIn.charAt(0);
        char rs = charIn.charAt(1);
        char cf = charIn.charAt(3);
        char rf = charIn.charAt(4);
        //0=si, 1=sj, 2=fi, 3=fj
        switch (cs) {
            case 'a':
                mov[1]=0;
                break;
            case 'b':
                mov[1]=1;
                break;
            case 'c':
                mov[1]=2;
                break;
            case 'd':
                mov[1]=3;
                break;
            case 'e':
                mov[1]=4;
                break;            
        }
        switch (rs) {
            case '1':
                mov[0]=5;
                break;
            case '2':
                mov[0]=4;
                break;
            case '3':
                mov[0]=3;
                break;
            case '4':
                mov[0]=2;
                break;
            case '5':
                mov[0]=1;
                break;            
            case '6':
                mov[0]=0;
                break;
        }
        switch (cf) {
            case 'a':
                mov[3]=0;
                break;
            case 'b':
                mov[3]=1;
                break;
            case 'c':
                mov[3]=2;
                break;
            case 'd':
                mov[3]=3;
                break;
            case 'e':
                mov[3]=4;
                break;            
        }
        switch (rf) {
            case '1':
                mov[2]=5;
                break;
            case '2':
                mov[2]=4;
                break;
            case '3':
                mov[2]=3;
                break;
            case '4':
                mov[2]=2;
                break;
            case '5':
                mov[2]=1;
                break;            
            case '6':
                mov[2]=0;
                break;
        }
    }
    
	public static void move(String charIn) {
		// perform the supplied move (for example "a5-a4\n") and update the state of the game / your internal variables accordingly - note that it advised to do a sanity check of the supplied move
     
        //WARNING: adds lag bugfix for end of game crashes
        //if (charIn == "") {
        //    board_stack.push(boardGet());
        //    return;
        //}  
        
        
        
        //save the current board
        board_stack.push(boardGet());
        
        //holds return values from board_to_array
        //0=si, 1=sj, 2=fi, 3=fj
        int[] move = new int[4];
        board_to_array(charIn, move);
        char temp = board[move[0]][move[1]];
        if (move[2]==5 && temp=='p')
            temp = 'q';
        else if (move[2]==0 && temp=='P')
            temp = 'Q';
        board[move[0]][move[1]] = '.';
        board[move[2]][move[3]] = temp;
        //update side/turn
        if (side == 'W') {
            side = 'B';
        } else {
            side = 'W';
            turn++;
        }        
	}
	
	public static String moveRandom() {
		// perform a random move and return it - one example output is given below - note that you can call the chess.movesShuffled() function as well as the chess.move() function in here
		Vector<String> move_list = new Vector<String>();
        move_list = movesShuffled();
        if (move_list.isEmpty())
            return "";
        String m = move_list.firstElement();
        move(m);
		return m;
	}
	
	public static String moveGreedy() {
		// perform a greedy move and return it - one example output is given below - note that you can call the chess.movesEvaluated() function as well as the chess.move() function in here
		Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        if (move_list.isEmpty())
            return "";
        String m = move_list.firstElement();
        move(m);
		return m;
	}
	
    public static void moveNegamaxWrapper(int intDuration) {
        if (intDuration > 40000)
            moveNegamax(6,0);
        else if (intDuration > 8000)
            moveNegamax(5,0);
        else
            moveNegamax(4,0);
    }      

	public static String moveNegamax(int intDepth, int intDuration) {
		// perform a negamax move and return it - one example output is given below - note that you can call the the other functions in here
        /*
        //wrapper code
        if (intDuration > 40000)
            intDepth=6;
        else if (intDuration > 8000)
            intDepth=5;
        else
            intDepth=4;
        */
        
		String best = "";
        int score = -99999999;
        int temp = 0;
        //intDepth = 3;
        Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        //WARNING: following line adds time factor
        //if (move_list.isEmpty()) {
        //    return best;
        //}
        for (String m: move_list) {            
            move(m);
            temp = -1*negamax(intDepth-1);
            undo();            
            if (temp > score) {
                best = m;
                score = temp;
            }
        }
        //System.out.print(best);
        //System.out.println("moving: "+best);
        //if (best == "") {
        ///    best = move_list.firstElement();
        //}
        move(best);
		return best;
	}
    
    public static int negamax(int depth) {
        if (depth==0 || winner()!='?') {
            return eval();
        }
        
        int score = -99999999;
        
        Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        for (String m: move_list) {
            move(m);
            score = Math.max(score, -1*negamax(depth-1));
            undo();
        }
        return score;
    }
	
    public static int negamax_alphaBeta(int depth, int alpha, int beta) {
        int alphaOrig = alpha;
        //Transposition table lookup
        tableEntry t = trans.get(boardGet());
        if (t!=null && t.depth>=depth) {
            if (t.flag == 'E')
                return t.val;
            else if (t.flag == 'L')
                alpha = Math.max(alpha, t.val);
            else if (t.flag == 'U')
                beta = Math.min(beta, t.val);
            if (alpha >= beta)
                return t.val;
        }

        if (depth == 0 || winner()!='?') {
            return eval();
        }
        
        int score = -99999999;
        
        Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        for (String m: move_list) {
            move(m);
            score = Math.max(score, -negamax_alphaBeta(depth-1, -beta, -alpha));
            undo();
            alpha = Math.max(alpha, score);
            if (alpha >= beta)
                break;
            
        }


        //Transposition table store
        if (t==null)
            t = new tableEntry();
        t.val = score;
        if (score <= alphaOrig)
            t.flag = 'U';
        else if (score >= beta)
            t.flag = 'L';
        else
            t.flag = 'E';
        t.depth = depth;
        trans.put(boardGet(), t);

        return score;
    }
    
	public static String moveAlphabeta(int intDepth, int intDuration) {
		// perform a alphabeta move and return it - one example output is given below - note that you can call the the other functions in here
		String best = "";
        int alpha = -99999999;
        int beta = 99999999;

        trans = new HashMap<String, tableEntry>();

        int temp = 0;
        Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        //System.out.println(move_list);
        for (String m: move_list) {
            move(m);
            int v = -negamax_alphaBeta(intDepth-1, -beta, -alpha);
            undo();
            if (v > alpha) {
                best = m;
                alpha = v;
            }       
            
        }
        move(best);
		return best;
	}
    
    
    
    
    
    
    
    
    
    //code from wiki - negamax
    public static int negamax_alphaBetaTrans(int depth, int alpha, int beta) {
        int alphaOrig = alpha;
        
        //Transposition table lookup
        tableEntry t = trans.get(boardGet());
        if (t!=null && t.depth>=depth) {
            if (t.flag == 'E') 
                return t.val;
            else if (t.flag == 'L')
                alpha = Math.max(alpha, t.val);
            else if (t.flag == 'U')
                beta = Math.min(beta, t.val);
            if (alpha >= beta)
                return t.val;
        }
        
        if (depth == 0) {
            return eval();
        }
           
        int score = -99999999;
        
        Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        for (String m: move_list) {
            move(m);
            int v = -negamax_alphaBetaTrans(depth-1, -beta, -alpha);
            undo();
            score = Math.max(score, v);            
            alpha = Math.max(alpha, v);
            if (alpha >= beta)
                break;
            
        }
        
        //Transposition table store
        if (t==null)
            t = new tableEntry();
        t.val = score;
        if (score <= alphaOrig)
            t.flag = 'U';
        else if (score >= beta)
            t.flag = 'L';
        else
            t.flag = 'E';
        t.depth = depth;
        trans.put(boardGet(), t);
        
        
        return score;
    }

	public static String moveNegamaxAlphabetaTrans(int intDepth, int intDuration) {
		// perform a alphabeta move and return it - one example output is given below - note that you can call the the other functions in here
		
        trans = new HashMap<String, tableEntry>();
        
		String best = "";
        int score = -99999999;
        int alpha = -99999999;
        int beta = 99999999;
        
        int temp = 0;
        Vector<String> move_list = new Vector<String>();
        move_list = movesEvaluated();
        //System.out.println(move_list);
        for (String m: move_list) {
            move(m);
            int v = -negamax_alphaBetaTrans(intDepth-1, alpha, beta);
            undo();
            if (v > score) {
                best = m;
                score = v;
            }       
            
        }
        move(best);
		return best;
	}    
	
	public static void undo() {
		// undo the last move and update the state of the game / your internal variables accordingly - note that you need to maintain an internal variable that keeps track of the previous history for this
        boardSet(board_stack.pop());
	}
}

class tableEntry {
    public int val;
    public char flag;
    public int depth;
}
