/* 
  * Knight.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the Knight class that contains the knight constructor and moveset for knight pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Knight extends Piece {

	/**
	 * Constructor for the Knight object
	 * @param player the player to which the Knight object belongs
	 * @param board the board that the Knight is assigned to
	 * @param color the knight object's color
	 * @param xpos the x position of the Knight object
	 * @param ypos the y position of the Knight object
	 */
	public Knight(Player player, Board board, char color, int xpos, int ypos) {
		super(player, board, color, xpos, ypos);
		this.name = 'N';
	}
	
	public ArrayList<String> generateMoves() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		int row = this.getX();
		int column = this.getY();
		String filerank1 = Board.convertToFileRank(row, column);
		String filerank2 = "";
		
		
		// Top-Left (Up-Up-Left)
		if(row >= 2 && column >= 1) {
			filerank2 = Board.convertToFileRank(row - 2, column - 1); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Top-Right (Up-Up-Right)
		if(row >= 2 && column <= 6) {
			filerank2 = Board.convertToFileRank(row - 2, column + 1); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Top Mid-Left (Up-Left-Left)
		if(row >= 1 && column >= 2) {
			filerank2 = Board.convertToFileRank(row - 1, column - 2); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Top Mid-Right (Up-Right-Right)
		if(row >= 1 && column <= 5) {
			filerank2 = Board.convertToFileRank(row - 1, column + 2); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Bottom Mid-Left (Down-Left-Left)
		if(row <= 6 && column >= 2) {
			filerank2 = Board.convertToFileRank(row + 1, column - 2); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Bottom Mid-Right (Down-Right-Right)
		if(row <= 6 && column <= 5) {
			filerank2 = Board.convertToFileRank(row + 1, column + 2); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Bottom-Left (Down-Down-Left)
		if(row <= 5 && column >= 1) {
			filerank2 = Board.convertToFileRank(row + 2, column - 1); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}
		
		// Bottom-Right (Down-Down-Right)
		if(row <= 5 && column <= 6) {
			filerank2 = Board.convertToFileRank(row + 2, column + 1); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
			}
		}

		return result;
		
	}

}
