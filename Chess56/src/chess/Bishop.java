/* 
 * Bishop.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the Bishop class that contains the bishop constructor and moveset for bishop pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Bishop extends Piece {
	
	/**
	 * Constructor for Bishop
	 * @param player the player who owns the Bishop piece
	 * @param board the board where the Bishop is located on
	 * @param color the color of the Bishop
	 * @param xpos the x location of the Bishop
	 * @param ypos the y location of the Bishop
	 */
	public Bishop(Player player, Board board, char color, int xpos, int ypos) {
		super(player, board, color, xpos, ypos);
		this.name = 'B'; //Bishop name/symbol
	}
	
	public ArrayList<String> generateMoves() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		int row = this.getX();
		int column = this.getY();
		int diagRow;
		int diagCol;
		String filerank1 = Board.convertToFileRank(row, column);
		String filerank2 = "";
		
		
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
