import java.util.Vector;

public class chess {
	//BEGIN: TESTING FUNCTIONS
	public static void main(String [] args) {
		System.out.println("test");
		reset();
		print_board();
		System.out.println(boardGet());
		String strOut = "";

		strOut += "12W\n";
		strOut += "kqbnr\n";
		strOut += "ppppp\n";
		strOut += ".....\n";
		strOut += "P....\n";
		strOut += ".PPPP\n";
		strOut += "RNBQK\n";
		boardSet(strOut);
		print_board();
	}

	public static void preset() {
		// reset the state of the game / your internal variables - note that this function is highly dependent on your implementation
		turn = 1;
		side = 'W';
		board[0][0] = 'k';
		board[0][1] = 'q';
		board[0][2] = 'b';
		board[0][3] = 'n';
		board[0][4] = 'r';

		board[1][0] = '.';
		board[1][1] = 'p';
		board[1][2] = 'p';
		board[1][3] = 'p';
		board[1][4] = 'p';

		board[2][0] = 'p';
		board[2][1] = '.';
		board[2][2] = '.';
		board[2][3] = '.';
		board[2][4] = '.';

		board[3][0] = '.';
		board[3][1] = '.';
		board[3][2] = '.';
		board[3][3] = '.';
		board[3][4] = 'P';

		board[4][0] = 'P';
		board[4][1] = 'P';
		board[4][2] = 'P';
		board[4][3] = 'P';
		board[4][4] = '.';

		board[5][0] = 'R';
		board[5][1] = 'N';
		board[5][2] = 'B';
		board[5][3] = 'Q';
		board[5][4] = 'K';
	}
	//END: TESTING FUNCTIONS

	private static int turn = 1;
	private static char side = 'W';
	private static char[][] board = new char[6][5];

    //START: HELPER FUNCTIONS
	public static void print_board() {
		System.out.print(turn+" "+side+"\n");
		for (int i=0; i<6; i++) {
			for (int j=0; j<5; j++) {
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
	}


    //END: HELPER FUNCTIONS



	public static void reset() {
		// reset the state of the game / your internal variables - note that this function is highly dependent on your implementation
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
	}
	
	public static char winner() {
		// determine the winner of the current state of the game and return '?' or '=' or 'W' or 'B' - note that we are returning a character and not a string
		//print_board();

        if (turn > 40) {
            return '=';
        }

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
		
		return 0;
	}

    //convert "40-30" input as (4,0,3,0) to "b0-c0"
    public static String array_to_board(int si, int sj, int fi, int fj) {
        String strOut = "";
        strOut += si;
        strOut += sj;
        strOut += "-";
        strOut += fi;
        strOut += fj;

        return strOut;
    }

    public static Vector<String> piece_moves(int i, int j) {
        Vector<String> strOut = new Vector<String>();

        switch (board[i][j]) {
            case 'P':
                //can pawn move up?
                if (isValid(i-1,j)) {
                    strOut.add(array_to_board(i,j,(i-1),j));
                }
            case 'p':
        }

       // strOut.add("a5-a4\n");



        return strOut;
    }

	public static Vector<String> moves() {
		// with reference to the state of the game and return the possible moves - one example is given below - note that a move has exactly 6 characters
        Vector<String> strOut = new Vector<String>();
        for (int i=0; i<6; i++) {
            for (int j=0; j<5; j++) {
               strOut.addAll(piece_moves(i, j));
            }
        }


        for (String s: strOut) {
            System.out.println(s);
        }
		/*strOut.add("a5-a4\n");
		strOut.add("b5-b4\n");
		strOut.add("c5-c4\n");
		strOut.add("d5-d4\n");
		strOut.add("e5-e4\n");
		strOut.add("b6-a4\n");
		strOut.add("b6-c4\n");*/
		
		return strOut;
	}
	
	public static Vector<String> movesShuffled() {
		// with reference to the state of the game, determine the possible moves and shuffle them before returning them - note that you can call the chess.moves() function in here
		
		return new Vector<String>();
	}
	
	public static Vector<String> movesEvaluated() {
		// with reference to the state of the game, determine the possible moves and sort them in order of an increasing evaluation score before returning them - note that you can call the chess.moves() function in here
		
		return new Vector<String>();
	}
	
	public static void move(String charIn) {
		// perform the supplied move (for example "a5-a4\n") and update the state of the game / your internal variables accordingly - note that it advised to do a sanity check of the supplied move
        //string from =
        //string to =
        //char piece_holder =
	}
	
	public static String moveRandom() {
		// perform a random move and return it - one example output is given below - note that you can call the chess.movesShuffled() function as well as the chess.move() function in here
		
		return "a5-a4\n";
	}
	
	public static String moveGreedy() {
		// perform a greedy move and return it - one example output is given below - note that you can call the chess.movesEvaluated() function as well as the chess.move() function in here
		
		return "a5-a4\n";
	}
	
	public static String moveNegamax(int intDepth, int intDuration) {
		// perform a negamax move and return it - one example output is given below - note that you can call the the other functions in here
		
		return "a5-a4\n";
	}
	
	public static String moveAlphabeta(int intDepth, int intDuration) {
		// perform a alphabeta move and return it - one example output is given below - note that you can call the the other functions in here
		
		return "a5-a4\n";
	}
	
	public static void undo() {
		// undo the last move and update the state of the game / your internal variables accordingly - note that you need to maintain an internal variable that keeps track of the previous history for this
	}
}
