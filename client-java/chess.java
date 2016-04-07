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
		
		return 0;
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

        return strOut;
    }

    public static Vector<String> piece_moves(int i, int j) {
        Vector<String> strOut = new Vector<String>();
        System.out.println("piece_moves called : "+board[i][j]);        
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
                if (isValidArrayMove(i-1,j)) {
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
                if (isValidArrayMove(i+1,j)) {
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

        print_board();
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
