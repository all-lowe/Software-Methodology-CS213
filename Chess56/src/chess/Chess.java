/* 
 * Chess.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The main Chess class. This will Run the Chess Program.  This includes accepting user input and using that input to move each player's chess pieces.
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class Chess {
	
	/**
	 * Board variable where the game will be played on.
	 */
	public Board board; //chess board
	/**
	 * Player variable that represents Player white.
	 */
	Player p_white;		//player 1 (white pieces) [1st turn]
	/**
	 * Player variable that represents Player black.
	 */
	Player p_black; 	//player 2 (black pieces)
	/**
	 * Boolean variable that alerts if the game is still being played.
	 */
	boolean inProgress; //check if current game is in progress (default = true)
	
	// Chess Constructor -> create a game of Chess in main
	/**
	 * Constructor for the Chess class that will initialize the board, both Players in the game, and initialize the boolean variable that will start the game
	 */
	public Chess() {
		
		// Create chess board the players are playing on
		Board board = new Board();
		this.board = board;
		
		// Create and initialize 2 players (black and white)
		p_white = new Player(board, 'w');
		p_black = new Player(board, 'b');
		
		p_white.opponent = p_black;
		p_black.opponent = p_white;
		
		inProgress = true;
		
	}
	
	/**
	 * Checks if the desired piece for the pawn to be promoted to is a valid piece
	 * @param input is the input that the user entered when trying to promote a piece
	 * @return returns true if the user entered Q,R,N, or B, when trying to promote a piece else returns false
	 */
	public static boolean checkValidity(String input) {
		
		if(input.length() == 7) { //promotion
			char newchar = input.charAt(6);
			if(newchar == 'Q' || newchar == 'R' || newchar == 'N' || newchar == 'B') {
				return true;
			}	
		}
		
		return false;
		
	}
	
	/**
	 * The method of this application.  Will Run the game and check if a check or checkmate has occurred.  Will also check if the user wants to resign or offer a draw.  
	 * @param args array String of arguments
	 */
	public static void main(String[] args) {
		
		Chess chess_game = new Chess();
		
		try {
		
			boolean white_turn = true;
			
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			
			// Current game is IN PROGRESS
			while(chess_game.inProgress == true) {
				
				/*
				System.out.println("White's pieces: " + chess_game.p_white.pieces);
				System.out.println("Black's pieces: " + chess_game.p_black.pieces);
				System.out.println();
				*/
				
				/*
				
				ArrayList<Piece> white_pieces = chess_game.p_white.pieces;
				ArrayList<Piece> black_pieces = chess_game.p_black.pieces;
				ArrayList<String> allValidMoves1;
				ArrayList<String> allValidMoves2;
				
				
				
				int i = 0;
				
				//prints out all of the white and black moves available to make
				while (i < white_pieces.size()){
					allValidMoves1 = white_pieces.get(i).generateMoves();
					System.out.println("- Move (" + white_pieces.get(i).name + " " + white_pieces.get(i).color + "): "  + allValidMoves1);
					i++;
				}
				
				System.out.println();
				
				i = 0;
				
				while (i < black_pieces.size()){
					allValidMoves2 = black_pieces.get(i).generateMoves();
					System.out.println("- Move (" + black_pieces.get(i).name + " " + black_pieces.get(i).color + "): "  + allValidMoves2);
					i++;
				}
				
				System.out.println();
				*/
				
				if(white_turn == true) { //white turn
					if(chess_game.p_white.validMove == true) {
						System.out.println(chess_game.board);
					}
				} else { //black turn
					if(chess_game.p_black.validMove == true) {
						System.out.println(chess_game.board);
					}
				}
				
				if(white_turn == true) { // White Player's Turn
					
					chess_game.p_white.draw = false;
					
					for(Piece p : chess_game.p_white.pieces) {
						if(p != null && p instanceof Pawn) {
							Piece temp = p;
							((Pawn)(temp)).hasMovedTwo = false;
						}
					}
					
					System.out.print(chess_game.p_white + "'s move: ");
					
					white_turn = false; //end white player's turn
					
				} else { // Black Player's Turn
					
					chess_game.p_black.draw = false;
					
					for(Piece p : chess_game.p_black.pieces) {
						if(p != null && p instanceof Pawn) {
							Piece temp = p;
							((Pawn)(temp)).hasMovedTwo = false;
						}
					}
					
					System.out.print(chess_game.p_black + "'s move: ");
					
					white_turn = true; //end black player's turn
				}
				
				String input = br.readLine();
				System.out.println("");
				
				if(input.equals("resign")) { //resign / forfeit -- [fully working]
					
					if(white_turn == false) { //white player resigns
						chess_game.inProgress = false;
						System.out.println(chess_game.p_black + " wins"); //black wins
						break;
					} else { //black player resigns
						chess_game.inProgress = false;
						System.out.println(chess_game.p_white + " wins"); //white wins
						break;
					}
					
				} else if(input.contains("draw") && input.length() == 4) { //player is ACCEPTING a draw -- [fully working]
					
					if(white_turn == false) { //white player accepts the draw
						chess_game.p_white.draw = true;
					} else { //black player accepts the draw
						chess_game.p_black.draw = true;
					}
					
					if(chess_game.p_white.draw == true && chess_game.p_black.draw == true) { //ends game in draw
						chess_game.inProgress = false;
						break;
					}
					
				} else if(input.length() == 5 || input.contains("draw?")) { //regular piece movement (e.g: "e2 e4") OR asking for draw (e.g.: "e2 e4 draw?") -- [fully working]
					
					boolean result;
					
					if(white_turn == false) { //white's turn
						result = chess_game.board.movePiece(input, chess_game.p_white, 'Q');
						
						// promotes if pawn is at the other end
						//chess_game.board.promote(white_turn, 'Q', chess_game.p_white, chess_game.p_black);
						
						//if white did not properly make a move, make white turn go again
						if(result == false) { //invalid move
							white_turn = true;
							chess_game.p_white.validMove = false; //white's current move is INVALID
							System.out.println("Illegal move, try again");
							System.out.println();
						} else {
							chess_game.p_white.validMove = true; //white's current move is VALID
						}
						
					} else { //black's turn
						result = chess_game.board.movePiece(input, chess_game.p_black, 'Q');
						
						// promotes if pawn is at the other end
						//chess_game.board.promote(white_turn, 'Q', chess_game.p_white, chess_game.p_black);
						
						
						
						
						
						//if black did not properly make a move, make black turn go again
						if(result == false) { //invalid move
							white_turn = false;
							chess_game.p_black.validMove = false; //black's current move is INVALID
							System.out.println("Illegal move, try again");
							System.out.println();
						} else {
							chess_game.p_black.validMove = true; //black's current move is VALID
						}
					}
					
					if(input.contains("draw?") && input.length() == 11 && result == true) { // player asks for a draw -- [fully working]
						if(white_turn == false) { //white player asks for draw
							chess_game.p_white.draw = true;
						} else { //black player asks for draw
							chess_game.p_black.draw = true;
						}
					}
					
				} else if(input.length() == 7) { //pawn promotion (e.g: "g7 g8 N")

					char newtype = input.charAt(6);
					
					if(white_turn == false) {
						
						if(checkValidity(input) == false) {
							white_turn = true;
							chess_game.p_white.validMove = false; //white's current move is INVALID
							System.out.println("Illegal move, try again");
							System.out.println();
							continue;
						}
						
						boolean result = chess_game.board.movePiece(input, chess_game.p_white, newtype);
						
						//promotes if pawn is at the other end
						//chess_game.board.promote(white_turn, newtype, chess_game.p_white, chess_game.p_black);
						
						//if white did not properly make a move, make white turn go again
						if(result == false) { //invalid move
							white_turn = true;
							chess_game.p_white.validMove = false; //white's current move is INVALID
							System.out.println("Illegal move, try again");
							System.out.println();
						} else {
							chess_game.p_white.validMove = true; //white's current move is VALID
						}
						
					} else {
						
						if(checkValidity(input) == false) {
							white_turn = false;
							chess_game.p_black.validMove = false; //black's current move is INVALID
							System.out.println("Illegal move, try again");
							System.out.println();
							continue;
						}
						
						boolean result = chess_game.board.movePiece(input, chess_game.p_black, newtype);
						
						//promotes if pawn is at the other end
						//chess_game.board.promote(white_turn, newtype, chess_game.p_white, chess_game.p_black);
						
						//if black did not properly make a move, make black turn go again
						if(result == false) { //invalid move
							white_turn = false;
							chess_game.p_black.validMove = false; //black's current move is INVALID
							System.out.println("Illegal move, try again");
							System.out.println();
						} else {
							chess_game.p_black.validMove = true; //black's current move is VALID
						}
					}
					
				
				} else {
					// check for invalid input
					
					if(white_turn == false) {
						white_turn = true;
						chess_game.p_white.validMove = false; //white's current move is INVALID
						System.out.println("Illegal move, try again");
						System.out.println();
					} else {
						white_turn = false;
						chess_game.p_black.validMove = false; //black's current move is INVALID
						System.out.println("Illegal move, try again");
						System.out.println();
					}

				}
				
				Player checkedPlayer = null;
				
				// Check
				if(chess_game.p_white.check || chess_game.p_black.check) {
					
					if(chess_game.p_white.check && chess_game.p_white.validMove == true) { //white is in check
						checkedPlayer = chess_game.p_white;
						System.out.println("Check");
						System.out.println("");
					} else if(chess_game.p_black.check && chess_game.p_black.validMove == true) { //black is in check
						//System.out.println(chess_game.p_white.validMove);
						checkedPlayer = chess_game.p_black;
						System.out.println("Check");
						System.out.println("");
					} else {
						// do nothing
					}
					
				}
				
				// Checkmate
				int a = 0;
				if(checkedPlayer != null) {
					checkedPlayer.checked = true;
					
					while(a < checkedPlayer.pieces.size() && checkedPlayer.checked == true) {
						
						ArrayList<String> moves = checkedPlayer.pieces.get(a).generateMoves();
						
						for(String s : moves) {
							
							// setup for reverting if we need to
							String fr_1 = s.substring(0,2);
							String fr_2 = s.substring(3,5);
							
							Piece srcPiece = null;
							srcPiece = chess_game.board.getPiece(fr_1);
							int xpos_src = chess_game.board.getRow(fr_1);
							int ypos_src = chess_game.board.getColumn(fr_1);
							
							Piece killedPiece = null;
							killedPiece = chess_game.board.getPiece(fr_2);
							int xpos_kill = chess_game.board.getRow(fr_2);
							int ypos_kill = chess_game.board.getColumn(fr_2);
							
							boolean pieceCheck = chess_game.board.movePiece(s, checkedPlayer, 'Q');
							
							// revert moving pieces changes
							if(pieceCheck == true) { //valid move -> NOT checkmate
								
								checkedPlayer.checked = false;
								checkedPlayer.check = true;
								
								// x1, y2 -> src of piece before move
								// x2, y2 -> dest of piece after move
								chess_game.board.space[xpos_src][ypos_src] = srcPiece;
								chess_game.board.space[xpos_kill][ypos_kill] = null;
								srcPiece.setX(xpos_src);
								srcPiece.setY(ypos_src);
								
								if(killedPiece != null) { //revert back killed piece to original location
									chess_game.board.space[xpos_kill][ypos_kill] = killedPiece;
									killedPiece.setX(xpos_kill);
									killedPiece.setY(ypos_kill);
									checkedPlayer.opponent.pieces.add(killedPiece); //added to player's arraylist
								}
								
								int x_diff = xpos_src - xpos_kill; //should be 1 if pawn enpassant
								int y_diff = Math.abs(ypos_src - ypos_kill); //should be 1 if pawn enpassant
								
								// srcPiece is a pawn
								if(srcPiece instanceof Pawn && srcPiece != null && killedPiece == null) {
									if(srcPiece.color == 'w' && x_diff == 1 && y_diff == 1) { //recovering black pawn

										chess_game.p_black.board.space[xpos_kill + 1][ypos_kill] = new Pawn(chess_game.p_black, chess_game.board, 'b', xpos_kill + 1, ypos_kill);
										chess_game.p_black.pieces.add(chess_game.p_black.board.space[xpos_kill + 1][ypos_kill]);
										
									} else if(srcPiece.color == 'b' && x_diff == -1 && y_diff == 1) { //recovering white pawn
										
										chess_game.p_white.board.space[xpos_kill - 1][ypos_kill] = new Pawn(chess_game.p_white, chess_game.board, 'w', xpos_kill - 1, ypos_kill);
										chess_game.p_white.pieces.add(chess_game.p_white.board.space[xpos_kill - 1][ypos_kill]);
										
									} else {
										// skip
									}
								}
								
								break;
							}
							
						}
						
						a++;
					}
					
					if(checkedPlayer.checked == true) { //checkmate
						checkedPlayer.checkmate = true;
					}
					
					if(checkedPlayer.checkmate == true) { //checkmate -> game over
						chess_game.inProgress = false; //current game is FINISHED -> ends game/exits loop
						//System.out.println(chess_game.board);
						System.out.println("Checkmate");
						System.out.println("");
						System.out.println(checkedPlayer.opponent + " wins");
						System.out.println("");
					} else { //not checkmate
						
					}
				}
				
				//chess_game.inProgress = false; //current game is FINISHED -> ends game/exits loop
				
			} //end of while loop
			
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		
	}
}
