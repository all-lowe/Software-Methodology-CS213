/* 
 * Pawn.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the Pawn class that contains the pawn constructor and moveset for pawn pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Pawn extends Piece {
	
	/**
	 * This variable notifies whether or not this Pawn instance can enpassant
	 */
	boolean enpassant;
	/**
	 * this variable notifies whether or not this Pawn instance has moved two spaces on its first turn
	 */
	boolean hasMovedTwo;

	/**
	 * Constructor for the Pawn class
	 * @param player the player to which the Pawn belongs
	 * @param board the board to which the Pawn belongs
	 * @param color the color of the Pawn piece
	 * @param xpos the x index of the Pawn piece
	 * @param ypos the y index of the Pawn piece
	 */
	public Pawn(Player player, Board board, char color, int xpos, int ypos) {
		super(player, board, color, xpos, ypos);
		this.name = 'p';
		this.enpassant = false;
		hasMovedTwo = false;
	}
	
	public boolean canEnpassantRight(Piece p) {
		
		if( (!(p instanceof Pawn)) || p == null) { //check is piece p is a pawn
			return false;
		}
		
		if(p.color == 'w') { //white pawn
			
			if(p.getX() != 3) {
				return false;
			}
			
			int row = p.getX();
			int column = p.getY();
			String right_space = Board.convertToFileRank(row, column + 1);
			
			// EnPassant 1 (Right-Diagonal) - white
			if(board.getPiece(right_space) != null && board.getPiece(right_space) instanceof Pawn && board.getPiece(right_space).color != this.color) {
				if( ((Pawn)(board.getPiece(right_space))).hasMovedTwo == true ) {
					return true;
				}
			} else {
				return false;
			}
			
			
		} else { //black pawn
			
			if(p.getX() != 4) {
				return false;
			}
			
			int row = p.getX();
			int column = p.getY();
			String right_space = Board.convertToFileRank(row, column + 1);
			
			// EnPassant 1 (Right-Diagonal) - black
			if(board.getPiece(right_space) != null && board.getPiece(right_space) instanceof Pawn && board.getPiece(right_space).color != this.color) {
				if( ((Pawn)(board.getPiece(right_space))).hasMovedTwo == true ) {
					return true;
				}
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * method that tells if a piece can enpassant diagonally left
	 * @param p the piece that wants to enpassant another pawn
	 * @return returns true if the piece can enpassant, else returns false if the piece cannot enpassant
	 */
	public boolean canEnpassantLeft(Piece p) {
		
		if( (!(p instanceof Pawn)) || p == null) { //check is piece p is a pawn
			return false;
		}
		
		if(p.color == 'w') { //white pawn
			if(p.getX() != 3) {
				return false;
			}
			
			int row = p.getX();
			int column = p.getY();
			String left_space = Board.convertToFileRank(row, column - 1);
			
			// EnPassant 1 (Left-Diagonal) - white
			if(board.getPiece(left_space) != null && board.getPiece(left_space) instanceof Pawn && board.getPiece(left_space).color != this.color) {
				if( ((Pawn)(board.getPiece(left_space))).hasMovedTwo == true ) {
					return true;
				}	
			} else {
				
				return false;
			}
		} else { //black pawn
			
			if(p.getX() != 4) {
				return false;
			}
			
			int row = p.getX();
			int column = p.getY();
			String left_space = Board.convertToFileRank(row, column - 1);
			
			// EnPassant 1 (Left-Diagonal) - black
			if(board.getPiece(left_space) != null && board.getPiece(left_space) instanceof Pawn && board.getPiece(left_space).color != this.color) {
				if( ((Pawn)(board.getPiece(left_space))).hasMovedTwo == true ) {
					return true;
				}
			} else {
				return false;
			}
		}
		
		return false;
	}

	public ArrayList<String> generateMoves() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		int row = this.getX();
		int column = this.getY();
		String filerank1 = Board.convertToFileRank(row, column);
		String filerank2_one = "";
		String filerank2_two = "";
		String rightDiagonal = "";
		String leftDiagonal = "";
		//String right_space = "";
		//String left_space = "";
		
		if(this.color == 'w') { //white pawn
			
			filerank2_one = Board.convertToFileRank(row - 1, column);
			filerank2_two = Board.convertToFileRank(row - 2, column);
			
			// Up 1 - white
			if(board.getPiece(filerank2_one) == null) { //pawn only can move 1 space up
				result.add(filerank1 + " " + filerank2_one);
				
				// Up 2 - white
				if(this.isMoved == false && board.getPiece(filerank2_two) == null) { //pawn's 1st turn -> can move 2 spaces up
					result.add(filerank1 + " " + filerank2_two);
				}
				
			}

			// Right-Diagonal - white
			rightDiagonal = Board.convertToFileRank(row - 1, column + 1);
			if(board.getPiece(rightDiagonal) != null && board.getPiece(rightDiagonal).color != this.color) {
				result.add(filerank1 + " " + rightDiagonal);
			}
			
			// Left-Diagonal - white
			leftDiagonal = Board.convertToFileRank(row - 1, column - 1);
			if(board.getPiece(leftDiagonal) != null && board.getPiece(leftDiagonal).color != this.color) {
				result.add(filerank1 + " " + leftDiagonal);
			}
			
			// EnPassant 1 (Right-Diagonal) - white
			if(board.getPiece(rightDiagonal) == null) {
				if(canEnpassantRight(board.getPiece(filerank1)) == true) { //check if pawn can use EnPassant rule
					result.add(filerank1 + " " + rightDiagonal);
				}
			}
			
			// EnPassant 2 (Left-Diagonal) - white
			if(board.getPiece(leftDiagonal) == null) {
				if(canEnpassantLeft(board.getPiece(filerank1)) == true) { //check if pawn can use EnPassant rule
					result.add(filerank1 + " " + leftDiagonal);
				}
			}
			
			
		} else { //black pawn
			
			filerank2_one = Board.convertToFileRank(row + 1, column);
			filerank2_two = Board.convertToFileRank(row + 2, column);
			
			// Up 1 - black
			if(board.getPiece(filerank2_one) == null) { //pawn only can move 1 space down
				result.add(filerank1 + " " + filerank2_one);
			
				// Up 2 - black
				if(this.isMoved == false && board.getPiece(filerank2_two) == null) { //pawn's 1st turn -> can move 2 spaces up
					result.add(filerank1 + " " + filerank2_two);
				}
			}
			
			// Right-Diagonal - black
			rightDiagonal = Board.convertToFileRank(row + 1, column + 1);
			if(board.getPiece(rightDiagonal) != null && board.getPiece(rightDiagonal).color != this.color) {
				result.add(filerank1 + " " + rightDiagonal);
			}
			
			// Left-Diagonal - black
			leftDiagonal = Board.convertToFileRank(row + 1, column - 1);
			if(board.getPiece(leftDiagonal) != null && board.getPiece(leftDiagonal).color != this.color) {
				result.add(filerank1 + " " + leftDiagonal);
			}
			
			// EnPassant 1 (Right-Diagonal) - black
			if(board.getPiece(rightDiagonal) == null) {
				if(canEnpassantRight(board.getPiece(filerank1)) == true) { //check if pawn can use EnPassant rule
					result.add(filerank1 + " " + rightDiagonal);
				}
			}

			// EnPassant 2 (Left-Diagonal) - black
			if(board.getPiece(leftDiagonal) == null) {
				if(canEnpassantLeft(board.getPiece(filerank1)) == true) { //check if pawn can use EnPassant rule
					result.add(filerank1 + " " + leftDiagonal);
				}
			}
			
		}
		
		return result;
		
	}
	
}
