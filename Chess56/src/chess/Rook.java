/* 
 * Rook.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the Rook class that contains the rook constructor and moveset for rook pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Rook extends Piece {
	
	/**
	 * Constructor for the Rook Object
	 * @param player the player to which the Rook object belongs
	 * @param board the board that the Rook is assigned to
	 * @param color the Rook object's color
	 * @param xpos the x position of the Rook object
	 * @param ypos the y position of the Rook object
	 */
	public Rook(Player player, Board board, char color, int xpos, int ypos) {
		super(player, board, color, xpos, ypos);
		this.name = 'R';
	}
	
	public ArrayList<String> generateMoves() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		int row = this.getX();
		int column = this.getY();
		String filerank1 = Board.convertToFileRank(row, column);
		String filerank2 = "";
		
		
		// Straight Upwards (changing rows, same column)
		for(int i = row - 1; i >= 0 ;i--) {
			filerank2 = Board.convertToFileRank(i, column); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}
			
		}
		
		// Straight Downwards (changing rows, same column)
		for(int i = row + 1; i <= 7; i++) {
			filerank2 = Board.convertToFileRank(i, column); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}			
		}

		// Straight Leftwards (same row, changing column)
		for(int i = column - 1; i >= 0; i--) {
			filerank2 = Board.convertToFileRank(row, i); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}			
		}
		
		// Straight Rightwards (same row, changing column)
		for(int i = column + 1; i <= 7; i++) {
			filerank2 = Board.convertToFileRank(row, i); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}			
		}
				
		return result;
	}
	
}
