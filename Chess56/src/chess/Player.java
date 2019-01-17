/* 
 * Player.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * The Player class that has the player object's constructor, and initializes all of the player's pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Player {
	
	/**
	 * The color of the player
	 */
	char color;
	/**
	 * The board to which the player belongs to
	 */
	Board board;
	/**
	 * The variable for the other player
	 */
	Player opponent;
	/**
	 * Boolean variable notifying whether this player is in check
	 */
	boolean check;
	/**
	 * Boolean variable used when determining if a player is in checkmate
	 */
	boolean checked;
	/**
	 * Boolean variable notifying whether this player is in checkmate
	 */
	boolean checkmate;
	/**
	 * Boolean variable notifying whether this player requested a draw
	 */
	boolean draw;
	/**
	 * Boolean variable notifying whether this player had just made a valid move
	 */
	boolean validMove;
	/**
	 * An ArrayList of all of the player's pieces
	 */
	ArrayList<Piece> pieces;
	
	//when constructor initializes player, we create all of the game pieces for the player
	/**
	 * Constructor for the Player object
	 * @param board the board to which to assign the player to
	 * @param color the color that is assigned to the player
	 */
	public Player(Board board, char color) {
		
		this.color = color;
		this.board = board;
		this.opponent = null;
		this.checked = false;
		this.check = false;
		this.checkmate = false;
		this.draw = false;
		this.validMove = true;
		pieces = new ArrayList<Piece>();
		
		initializePieces(pieces, board, color);
	}
	
	//will initialize all of the pieces and their x/y coordinates
	/**
	 * Will Create and initialize all of the Player's pieces at the beginning of the game and add them to the chess board and each player's ArrayList.
	 * @param pieces the ArrayList of the player's pieces
	 * @param board the board to which the player belongs to
	 * @param color the color of the Player who's pieces are getting initialized
	 */
	public void initializePieces(ArrayList<Piece> pieces, Board board, char color) {
		
		if(color == 'w') {
			
			//(white) initializes Backline(Rook, King, Queens, etc.) pieces to the BOARD
			board.space[7][0] = new Rook(this, board, color, 7, 0);
			board.space[7][1] = new Knight(this, board, color, 7, 1);
			board.space[7][2] = new Bishop(this, board, color, 7, 2);
			board.space[7][3] = new Queen(this, board, color, 7, 3);
			board.space[7][4] = new King(this, board, color, 7, 4);
			board.space[7][5] = new Bishop(this, board, color, 7, 5);
			board.space[7][6] = new Knight(this, board, color, 7, 6);
			board.space[7][7] = new Rook(this, board, color, 7, 7);
			
			//(white) initializes Frontline(Pawns) pieces to the BOARD
			board.space[6][0] = new Pawn(this, board, color, 6, 0);
			board.space[6][1] = new Pawn(this, board, color, 6, 1);
			board.space[6][2] = new Pawn(this, board, color, 6, 2);
			board.space[6][3] = new Pawn(this, board, color, 6, 3);
			board.space[6][4] = new Pawn(this, board, color, 6, 4);
			board.space[6][5] = new Pawn(this, board, color, 6, 5);
			board.space[6][6] = new Pawn(this, board, color, 6, 6);
			board.space[6][7] = new Pawn(this, board, color, 6, 7);
			
			//(white) adds Backline pieces to ArrayList (ArrayList in Player class)
			pieces.add(board.space[7][0]);
			pieces.add(board.space[7][1]);
			pieces.add(board.space[7][2]);
			pieces.add(board.space[7][3]);
			pieces.add(board.space[7][4]);
			pieces.add(board.space[7][5]);
			pieces.add(board.space[7][6]);
			pieces.add(board.space[7][7]);
			
			//(white) adds Frontline pieces to ArrayList (ArrayList in Player class)
			pieces.add(board.space[6][0]);
			pieces.add(board.space[6][1]);
			pieces.add(board.space[6][2]);
			pieces.add(board.space[6][3]);
			pieces.add(board.space[6][4]);
			pieces.add(board.space[6][5]);
			pieces.add(board.space[6][6]);
			pieces.add(board.space[6][7]);
			
		} else { //this.color == 'b'
			
			//(black) initializes backline(Rook, King, Queens, etc.) pieces to the BOARD
			board.space[0][0] = new Rook(this, board, color, 0, 0);
			board.space[0][1] = new Knight(this, board, color, 0, 1);
			board.space[0][2] = new Bishop(this, board, color, 0, 2);
			board.space[0][3] = new Queen(this, board, color, 0, 3);
			board.space[0][4] = new King(this, board, color, 0, 4);
			board.space[0][5] = new Bishop(this, board, color, 0, 5);
			board.space[0][6] = new Knight(this, board, color, 0, 6);
			board.space[0][7] = new Rook(this, board, color, 0, 7);
			
			//(black) initializes frontline(Pawns) pieces to the BOARD
			board.space[1][0] = new Pawn(this, board, color, 1, 0);
			board.space[1][1] = new Pawn(this, board, color, 1, 1);
			board.space[1][2] = new Pawn(this, board, color, 1, 2);
			board.space[1][3] = new Pawn(this, board, color, 1, 3);
			board.space[1][4] = new Pawn(this, board, color, 1, 4);
			board.space[1][5] = new Pawn(this, board, color, 1, 5);
			board.space[1][6] = new Pawn(this, board, color, 1, 6);
			board.space[1][7] = new Pawn(this, board, color, 1, 7);
			
			//(black) adds Backline pieces to ArrayList (ArrayList in Player class)
			pieces.add(board.space[0][0]);
			pieces.add(board.space[0][1]);
			pieces.add(board.space[0][2]);
			pieces.add(board.space[0][3]);
			pieces.add(board.space[0][4]);
			pieces.add(board.space[0][5]);
			pieces.add(board.space[0][6]);
			pieces.add(board.space[0][7]);
			
			//(black) adds Frontline pieces to ArrayList (ArrayList in Player class)
			pieces.add(board.space[1][0]);
			pieces.add(board.space[1][1]);
			pieces.add(board.space[1][2]);
			pieces.add(board.space[1][3]);
			pieces.add(board.space[1][4]);
			pieces.add(board.space[1][5]);
			pieces.add(board.space[1][6]);
			pieces.add(board.space[1][7]);
		}
	}
	
	/**
	 * Return the color of the player
	 * @return returns the color of the Player as a String
	 */
	public String toString() {
		
		String temp = "";
		
		if(this.color == 'w') {
			temp = "White";
		} else {
			temp = "Black";
		}
		
		return temp; // either "White" or "Black" player
	}

}
