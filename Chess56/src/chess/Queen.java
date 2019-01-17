/* 
 * Queen.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the Queen class that contains the queen constructor and moveset for queen pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Queen extends Piece {
	
	/**
	 * Constructor for the Queen Object
	 * @param player the player to which the Queen object belongs
	 * @param board the board that the Queen is assigned to
	 * @param color the Queen object's color
	 * @param xpos the x position of the Queen object
	 * @param ypos the y position of the Queen object
	 */
	public Queen(Player player, Board board, char color, int xpos, int ypos) {
		super(player, board, color, xpos, ypos);
		this.name = 'Q';
	}
	
	public ArrayList<String> generateMoves() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		int row = this.getX();
		int column = this.getY();
		int diagRow;
		int diagCol;
		String filerank1 = Board.convertToFileRank(row, column);
		String filerank2 = "";
		
		// Straight Upwards (changing rows, same column)
		for(int i = row - 1; i >= 0; i--) {
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
		
		
		// Upper-Right Diagonal (row and column changing)
		diagRow = row - 1;
		diagCol = column + 1;
		
		while(diagRow >= 0 && diagCol <= 7) {
			filerank2 = Board.convertToFileRank(diagRow, diagCol); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}
			diagRow--;
			diagCol++;
		}
		
		// Upper-Left Diagonal (row and column changing)
		diagRow = row - 1;
		diagCol = column - 1;
		
		while(diagRow >= 0 && diagCol >= 0) {
			filerank2 = Board.convertToFileRank(diagRow, diagCol); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}
			diagRow--;
			diagCol--;
		}
		
		// Lower-Right Diagonal (row and column changing)
		diagRow = row + 1;
		diagCol = column + 1;
		
		while(diagRow <= 7 && diagCol <= 7) {
			filerank2 = Board.convertToFileRank(diagRow, diagCol); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}
			diagRow++;
			diagCol++;
		}
		
		// Lower-Left Diagonal (row and column changing)
		diagRow = row + 1;
		diagCol = column - 1;
		
		while(diagRow <= 7 && diagCol >= 0) {
			filerank2 = Board.convertToFileRank(diagRow, diagCol); //filerank format
			if(board.getPiece(filerank2) == null) {
				result.add(filerank1 + " " + filerank2);
			} else {
				if(board.getPiece(filerank2).color != this.color) { //encountered enemy piece
					result.add(filerank1 + " " + filerank2);
				}
				break;
			}
			diagRow++;
			diagCol--;
		}
		
		return result;
	}

}
