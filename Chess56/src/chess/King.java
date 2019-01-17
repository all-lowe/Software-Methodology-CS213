/* 
 * King.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the King class that contains the king constructor and moveset for king pieces
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class King extends Piece {

	/**
	 * Constructor for the King Object
	 * @param player the player to which the King object belongs
	 * @param board the board that the King is assigned to
	 * @param color the King object's color
	 * @param xpos the x position of the King object
	 * @param ypos the y position of the King object
	 */
	public King(Player player, Board board, char color, int xpos, int ypos) {
		super(player, board, color, xpos, ypos);
		this.name = 'K';
	}
	
	public ArrayList<String> generateMoves() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		int row = this.getX();
		int column = this.getY();
		String filerank1 = Board.convertToFileRank(row, column);
		String filerank2 = "";
		
		// Up
		if(row >= 1 && row <= 7 && column <= 7 && column >= 0) {
			filerank2 = Board.convertToFileRank(row - 1, column);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Upper Right-Diagonal
		if(row >= 1 && row <= 7 && column <= 6 && column >= 0) {
			filerank2 = Board.convertToFileRank(row - 1, column + 1);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Upper Left-Diagonal
		if(row >= 1 && row <= 7 && column >= 1 && column <= 7) {
			filerank2 = Board.convertToFileRank(row - 1, column - 1);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Right
		if(row >= 0 && row <= 7 && column <= 6 && column >= 0) {
			filerank2 = Board.convertToFileRank(row, column + 1);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Left
		if(row >= 0 && row <= 7 && column >= 1 && column <= 7) {
			filerank2 = Board.convertToFileRank(row, column - 1);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Lower Right-Diagonal
		if(row <= 6 && row >= 0 && column <= 6 && column >= 0) {
			filerank2 = Board.convertToFileRank(row + 1, column + 1);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Lower Left-Diagonal
		if(row <= 6 && row >= 0 && column >= 1 && column <= 7) {
			filerank2 = Board.convertToFileRank(row + 1, column - 1);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Down
		if(row <= 6 && row >= 0 && column <= 7 && column >= 0) {
			filerank2 = Board.convertToFileRank(row + 1, column);
			if(board.getPiece(filerank2) == null || board.getPiece(filerank2).color != this.color) {
				result.add(filerank1 + " " + filerank2);
			}
		}
		
		// Castling
		if(this.isMoved == false && player.check == false) { //king hasn't moved
			
			Piece rook;
			String filerank_king;
			//String filerank_rook;
			
			if(this.color == 'w') { //white
				
				int king_row = this.getX();
				int king_col = this.getY();
				filerank_king = Board.convertToFileRank(king_row, king_col);
				Piece king = board.getPiece(filerank_king);
				
				// Left Castling
				rook = board.getPiece("a1"); //left
				//filerank_rook = Board.convertToFileRank(rook.getX(), rook.getY());
				if(king instanceof King && rook instanceof Rook && king != null && rook != null) {
					if(king.isMoved == false && rook.isMoved == false) {
						if(board.getPiece("d1") == null && board.getPiece("c1") == null && board.getPiece("b1") == null) {
							result.add("e1 c1");
						}
					}
				}
				
				// Right Castling
				rook = board.getPiece("h1"); //right
				//filerank_rook = Board.convertToFileRank(rook.getX(), rook.getY());
				if(king instanceof King && rook instanceof Rook && king != null && rook != null) {
					if(king.isMoved == false && rook.isMoved == false) {
						if(board.getPiece("f1") == null && board.getPiece("g1") == null) {
							result.add("e1 g1");
							//result.add(filerank_king + " " + filerank_rook);
						}
					}
				}
				
			} else { //black
				
				int king_row = this.getX();
				int king_col = this.getY();
				filerank_king = Board.convertToFileRank(king_row, king_col);
				Piece king = board.getPiece(filerank_king);
				
				// Left Castling
				rook = board.getPiece("a8"); //left
				//filerank_rook = Board.convertToFileRank(rook.getX(), rook.getY());
				if(king instanceof King && rook instanceof Rook && king != null && rook != null) {
					if(king.isMoved == false && rook.isMoved == false) {
						if(board.getPiece("d8") == null && board.getPiece("c8") == null && board.getPiece("b8") == null) {
							result.add("e8 c8");
						}
					}
				}
				
				// Right Castling
				rook = board.getPiece("h8"); //right
				//filerank_rook = Board.convertToFileRank(rook.getX(), rook.getY());
				if(king instanceof King && rook instanceof Rook && king != null && rook != null) {
					if(king.isMoved == false && rook.isMoved == false) {
						if(board.getPiece("f8") == null && board.getPiece("g8") == null) {
							result.add("e8 g8");
						}
					}
				}
				
			}
			
		}
		
		return result;
		
	}

}
