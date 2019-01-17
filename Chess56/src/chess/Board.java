/* 
 * Board.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * This is the board class which stores all of the chess Pieces and handles the Pieces based on user input.
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class Board {
	
	//Board is a 2x2 array of Piece objects, board also has two players
	/**
	 * This is the board where the game will be played on
	 */
	Piece[][] space;
	
	/**
	 * Constructor for the board class that will initialize all of the board spaces to be a Piece type.
	 */
	public Board() {
		space = new Piece[8][8]; //2d array of pieces, allocated space but not initialized yet
	}
	
	// Converts 2D Array values into FileRank values
	/**
	 * This method will take an x and y index in a 2d array and convert it to its chess filerank. (Eg. row=0, column=0 returns a8)
	 * @param row an int variable that specifies the x location of the piece on the chess board.
	 * @param column an int variable that specifies the y location of the piece on the chess board.
	 * @return returns a String variable that is the resulting filerank.
	 */
	public static String convertToFileRank(int row, int column) {
		
		String result = "";
		
		char file = (char)('a' + column);
		int rank = 8 - row;
		
		result = file + "" + rank;
		
		return result;
		
	}
	
	// Converts FileRank values into 2D Array Values
	/**
	 * This method will take a filerank and convert it to its index x and index y values in the form of an array.
	 * @param filerank String variable that is the filerank of a spot on the board.
	 * @return int array variable that is length 2.  index 0 of the array contains row and index 1 contains column.
	 */
	public static int[] convertToArray(String filerank) {
		
		int[] result = new int[2];
		
		char file = filerank.charAt(0);
		char rankc = filerank.charAt(1);
		int rank = Character.getNumericValue(rankc);
		
		if(file < 'a' || file > 'h') { //invalid file
			return null;
		}
		
		if(rank < 1 || rank > 8) { //invalid rank
			return null;
		}
		
		result[0] = (int)file - 'a'; //column in 2d array
		result[1] = 8 - rank; //row in 2d array
		
		return result;
		
	}
	
	/*
	* Obtains piece from 2D Array using the FileRank
	* -> "FileRank FileRank" -> "e2 e4"
	* -> takes first FileRank ("e2") and converts values to find the object in 2D Array 
	* and returns the piece
	*/
	/**
	 * This method will take in a String filerank and return the Piece that is located at that filerank, if piece is empty, return null
	 * @param filerank is the String variable that corresponds to a location on the board.
	 * @return the Piece object that is located on the filerank
	 */
	public Piece getPiece(String filerank) {
		
		char file = filerank.charAt(0);
		char rankc = filerank.charAt(1);
		int rank = Character.getNumericValue(rankc);
		
		if(file < 'a' || file > 'h') { //invalid file
			return null;
		}
		
		if(rank < 1 || rank > 8) { //invalid rank
			return null;
		}
		
		int column = (int)file - 'a'; //column in 2d array
		int row = 8 - rank; //row in 2d array
		
		if(space[row][column] != null) {
			return space[row][column];
		} else {
			return null;
		}

	} //end of 'getPiece' method

	
	// Remove a piece from the board given its location/filerank (e.g. "e2")
	/**
	 * method that removes a piece on the chess board.
	 * @param filerank where the piece to be removed is located.
	 * @return returns false if the method failed to remove the piece, else returns true if successfully removed the piece from the board.
	 */
	public boolean removePiece(String filerank) {
		
		char file = filerank.charAt(0);
		char rankc = filerank.charAt(1);
		int rank = Character.getNumericValue(rankc);
		
		if(file < 'a' || file > 'h') { //invalid file
			return false;
		}
		
		if(rank < 1 || rank > 8) { //invalid rank
			return false;
		}
		
		int row = 8 - rank; //row in 2d array
		int col = ((int)file - 'a'); //column in 2d array
		
		if(space[row][col] != null) {
			space[row][col] = null; //remove piece from board
			//removePieceFromList(p, row, col); //remove piece from arraylist
		}
		
		return true;
	}
	
	// Remove a piece from the board given its location/filerank (e.g. "e2")
	/**
	 * method that puts a piece on the chess_board.
	 * @param filerank is the location on the chess baord where we are putting a new piece.
	 * @param piece is the Piece object that we are putting onto the chess_board given a location on the chess_board.
	 * @return returns true if the action was successful, false otherwise.
	 */
	public boolean setPiece(String filerank, Piece piece) {
		
		char file = filerank.charAt(0);
		char rankc = filerank.charAt(1);
		int rank = Character.getNumericValue(rankc);
		
		if(file < 'a' || file > 'h') { //invalid file
			return false;
		}
		
		if(rank < 1 || rank > 8) { //invalid rank
			return false;
		}
		
		int row = 8 - rank; //row in 2d array
		int col = ((int)file - 'a'); //column in 2d array

		space[row][col] = piece; // set piece on board
		
		return true;
	}
	
	//if a piece is killed or promoted, this will remove the piece from the Player's arrayList
	/**
	 * method that removes a player's piece from his/her arrayList
	 * @param p is the player who is getting a chess piece removed from their arraylist
	 * @param xposition is the x index of the piece player p is losing from their arraylist
	 * @param yposition is the y index of the piece player p is losing from their arraylist
	 */
	public void removePieceFromList(Player p, int xposition, int yposition) {
		
		ArrayList<Piece> list = new ArrayList<Piece>();
		
		for(Piece piece: p.pieces) {
			if((piece.getX() == xposition) && (piece.getY()==yposition)) {
				list.add(piece);
			}
		}
		
		p.pieces.removeAll(list);
		
	}
	
	//given e2, returns 6 because 2 is at the 6th column
	/**
	 * method that returns the row or x index given a String filerank(Eg. "e2")
	 * @param filerank is the String variable that contains the location of the spot on the chess board.
	 * @return returns the row or x index at the filerank's location.
	 */
	public int getRow(String filerank) {
		
		char rankc = filerank.charAt(1);
		int rank = Character.getNumericValue(rankc);
		int row = 8 - rank;
		

		return row;
		
	} //end of "getRow" function
	
	/**
	 * Returns the column or x index given at a String filerank(Eg. "e2")
	 * @param filerank is the String variable that contains the location of the spot on the chess board.
	 * @return returns the row or x index at the filerank's location.
	 */
	public int getColumn(String filerank) {
		char file = filerank.charAt(0);
		int column = (int)file - 'a';
		return column;
	}

/* ----------------- */
	
	//given String "e2 e4", it moves piece at e2 to e4
	/**
	 
	 * @return returns a boolean variable which notifies if the move successfully completed.  returns true if successful and false if unsuccessful.
	 */
	
	
	/**
	 * method that moves a chess piece from one location to another if valid.  returns a boolean variable notifying if the move succeeded.
	 * Will handle enpassant,promotion, and castling moves as well as invalid moves in the case of a check.
	 * @param filerank is the String variable that contains the full move from the user.
	 * @param p is the player who is making the move.
	 * @param newpiece is the desired piece to promote a pawn to
	 * @return returns true if successfully moved piece, returns false otherwise
	 */
	public boolean movePiece(String filerank, Player p, char newpiece) {
		
		// if filerank is invalid
		if(filerank == null || filerank.length() == 0 || filerank.isEmpty()) { //error checking
			//System.out.println("Illegal move, try again");
			//System.out.println();
			return false;
		} else { //more error checking
			char file = filerank.charAt(0);
			char rankc = filerank.charAt(1);
			int rank = Character.getNumericValue(rankc);
			
			if(file < 'a' || file > 'h' || rank < 1 || rank > 8) { //invalid file or rank
				//System.out.println("Illegal move, try again");
				//System.out.println();
				return false;
			}
		}
		
		// Separate SOURCE and DESTINATION fileranks (e.g: "e2 e4" -> s1 = "e2" & s2 = "e4")
		String s1 = filerank.substring(0, 2); //src
		String s2 = filerank.substring(3,5); //dest
		String full_filerank = filerank.substring(0,5);
		
		// Obtain the piece that is being moved (from SOURCE)
		Piece temp = getPiece(s1);
		
		// Error checking piece
		if(temp == null) { //no piece at source
			//System.out.println("Illegal move, try again");
			//System.out.println();
			return false;
		}
		if(temp.color != p.color) { //player trying to move wrong colored piece
			//System.out.println("Illegal move, try again");
			//System.out.println();
			return false;
		}
		
		if(filerank.length() == 7) {
			if(temp.color == 'w') { //white piece being promoted
				if(temp.getX() != 1) {
					return false;
				}
			} else { //black piece being promoted
				if(temp.getX() != 6) {
					return false;
				}
			}	
		}
		
		// CREATE ARRAYLIST TO CHECK IF FILERANK IS A VALID MOVE
		if(temp.generateMoves().contains(full_filerank)) {
		
			// Piece is a King -> check for castling
			//System.out.println(p.color + " isChecked: " + p.check);
			boolean isCastled = false;
			if((temp instanceof King) && (temp.isMoved == false) && p.check == false) {
				
				//checking if the space between the king and desired castle location is under attack
				if(filerank.equals("e1 g1")) {
					if( (space[7][5]==null) && (space[7][6]==null) && space[7][7].isMoved==false ) {
						if(spotUnderAttack(p, 7, 5)) { //if the space between king and location to castle to is under attack by a piece, then disallow castle
							//System.out.println("space in between castle is under attack!");
							return false;
						}
					}
				}else if(filerank.equals("e1 c1")) { //black side castle right
					if( (space[7][1]==null) && (space[7][2]==null) && (space[7][3]==null) && space[7][0].isMoved==false ) {
						if(spotUnderAttack(p, 7, 3)) { //if the space between king and location to castle to is under attack by a piece, then disallow castle
							//System.out.println("space in between castle is under attack!");
							return false;
						}
					}
				}else if(filerank.equals("e8 g8")){ //black side castle right	
					//checks if all spaces between rook and king are null, ALSO checks if Rook is unmoved.
					if( (space[0][5]==null) && (space[0][6]==null) && space[0][7].isMoved==false ) {
						if(spotUnderAttack(p, 0, 5)) { //if the space between king and location to castle to is under attack by a piece, then disallow castle
							//System.out.println("space in between castle is under attack!");
							return false;
						}
					}
				}else if(filerank.equals("e8 c8")) { //black side castle left
					//checks if all spaces between rook and king are null, ALSO checks if Rook is unmoved.
					if( (space[0][1]==null) && (space[0][2]==null) && (space[0][3]==null) && space[0][0].isMoved==false ) {
						if(spotUnderAttack(p, 0, 3)) { //if the space between king and location to castle to is under attack by a piece, then disallow castle
							//System.out.println("space in between castle is under attack!");
							return false;
						}
					}
				}
				
				isCastled = castle(full_filerank);
				if(isCastled == true) {
					//return true; //end turn b/c of castling
				}
			}
			
			Piece goal = getPiece(s2); //obtain current piece at destination space
			Piece killedPiece = null;
			
			//getting the indexes for the pieces in the array
			int x1 = getRow(s1);
			int y1 = getColumn(s1);
			int x2 = getRow(s2);
			int y2 = getColumn(s2);
			
			// Empty Destination -> move piece from source to destination
			if(goal == null && isCastled == false) {
				space[x2][y2] = temp;
				space[x1][y1] = null;
				temp.setX(x2); //changes the x pos
				temp.setY(y2); //changes the y pos
				//temp.isMoved = true;  //if pawn hits this, it wont ever have its hasMovedTwo to true, moved this to end
			}
			
			// Piece at Destination -> remove piece at destination + move source piece to destination
			if(goal != null && temp.color != goal.color) {
				p.opponent.pieces.remove(goal);
				killedPiece = this.getPiece(s2);
				boolean removed = this.removePiece(full_filerank);
				if(removed == false) {
					return false;
				}
				space[x2][y2] = temp;
				space[x1][y1] = null;
				temp.setX(x2); //changes the x pos
				temp.setY(y2); //changes the y pos
			}
			
			boolean hasEnpas = false;
			Piece enpasTemp = null;

			//piece is a pawn -> check if and where enpassant is possible
			if(temp instanceof Pawn && temp != null) {
				
				if(temp.color == 'w') { //white
					int x = temp.getX() + 1;
					int y = temp.getY();
					String filerank_xy = Board.convertToFileRank(x, y); //piece to be deleted
					
					if(this.getPiece(filerank_xy) instanceof Pawn && this.getPiece(filerank_xy).color != temp.color && ((Pawn)(this.getPiece(filerank_xy))).hasMovedTwo == true && x == 3) {
						p.opponent.pieces.remove(this.getPiece(filerank_xy));
						enpasTemp = this.getPiece(filerank_xy);
						this.removePiece(filerank_xy);
						hasEnpas = true;
					}
				} else { //black
					int x = temp.getX() - 1;
					int y = temp.getY();
					String filerank_xy = Board.convertToFileRank(x, y);
					
					if(this.getPiece(filerank_xy) instanceof Pawn && this.getPiece(filerank_xy).color != temp.color && ((Pawn)(this.getPiece(filerank_xy))).hasMovedTwo == true && x == 4) {
						p.opponent.pieces.remove(this.getPiece(filerank_xy));
						enpasTemp = this.getPiece(filerank_xy);
						this.removePiece(filerank_xy);
						hasEnpas = true;
					}
				}
				
			}
			
			//checks for pawn
			if((temp instanceof Pawn) && (temp.isMoved == false)) {
				temp.isMoved = true; //mark as pawn moved
				
				if(temp.color == 'w') { //white
					if(temp.getX() == 4) {
						((Pawn)(temp)).hasMovedTwo = true;
					}
				} else { //black
					if(temp.getX() == 3) {
						((Pawn)(temp)).hasMovedTwo = true;
					}
				}
			}
			
			// Prevent pieces from moving if causes check
			ArrayList<String> opponentMoves;
			String kingLocation = "";
			
			for(Piece piece : p.pieces) {
				if(piece instanceof King && piece.name == 'K' && piece.color == p.color) {
					kingLocation = Board.convertToFileRank(piece.getX(), piece.getY());
				}
			}
			
			int i = 0;
			
			while(i < p.opponent.pieces.size()) { //each piece
				
				opponentMoves = p.opponent.pieces.get(i).generateMoves();
				
				for(String s : opponentMoves) { //filerank in opponent's arraylist for each piece
					String dest = s.substring(3, 5);
					
					if(dest.equals(kingLocation)) { //found check	
						
						// Revert enpassant
						if(hasEnpas == true && temp instanceof Pawn && temp != null) {
							
							if(temp.color == 'w') { //pawn that enpassanted is white
								int enpas_x = temp.getX() + 1;
								int enpas_y = temp.getY();
								//String enpas_xy = Board.convertToFileRank(enpas_x, enpas_y); //piece to be recovered
								
								if(enpasTemp != null && enpasTemp instanceof Pawn && enpasTemp.color != temp.color) {
									p.opponent.pieces.add(enpasTemp);
									space[enpas_x][enpas_y] = enpasTemp;
								}
								
							} else { //pawn that enpassanted is black
								int enpas_x = temp.getX() - 1;
								int enpas_y = temp.getY();
								//String enpas_xy = Board.convertToFileRank(enpas_x, enpas_y); //piece to be recovered
								
								if(enpasTemp != null && enpasTemp instanceof Pawn && enpasTemp.color != temp.color) {
									p.opponent.pieces.add(enpasTemp);
									space[enpas_x][enpas_y] = enpasTemp;
								}
								
							}
							
						}
						
						//revert the rook in the castling action, King gets reverted with the code right after
						if(isCastled == true) {
							revertCastle(filerank);
						}
						
						// x1, y2 -> src of piece before move
						// x2, y2 -> dest of piece after move
						space[x1][y1] = temp;
						space[x2][y2] = null;
						temp.setX(x1);
						temp.setY(y1);
						
						if(killedPiece != null) { //revert back killed piece to original location
							space[x2][y2] = killedPiece;
							killedPiece.setX(x2);
							killedPiece.setY(y2);
							p.opponent.pieces.add(killedPiece); //added to player's arraylist
						}
						
						//if we detect that the piece moved was enpassant, then revert the enpassant back
						
						return false;
					}
					
				}
				
				i++;
				
			}
			
			temp.isMoved = true;
			
			//checking for promotion
			this.promote(p.color, newpiece, p);
			
			// Check
			ArrayList<String> ourMoves;
			String theirKingLocation = "";
			
			for(Piece piece : p.opponent.pieces) {
				if(piece instanceof King && piece.name == 'K' && piece.color != p.color) {
					theirKingLocation = Board.convertToFileRank(piece.getX(), piece.getY());
				}
			}
			
			i = 0;
			while(i < p.pieces.size()) {
				
				ourMoves = p.pieces.get(i).generateMoves(); 
				
				for(String s : ourMoves) { //filerank in our arraylist for each piece 
					String dest = s.substring(3, 5);
					
					if(dest.equals(theirKingLocation)) { //moving piece causes check against opponent
						p.opponent.check = true;
						return true;
					}
					
				}
				
				i++;
			}
			
			p.check = false;
			
			
			
		} else {
			//System.out.println("Illegal move, try again");
			//System.out.println();
			return false; //invalid move
		}
		
		return true;
		
	} // end of 'movePiece' method
	
/* ----------------- */
	
		//piece must be a king instance
		/**
		 * method used for castling which includes switching the king and rook locations on the chess board.
		 * @param filerank the String input that would specify which castling move the player wishes to make. 
		 * @return returns a boolean notifying whether or not the castling operation was successful.
		 * 
		 */
		public boolean castle(String filerank) {
			if(filerank.equals("e1 g1")) {
				
				//checks if all spaces between rook and king are null, ALSO checks if Rook is unmoved.
				if( (space[7][5]==null) && (space[7][6]==null) && space[7][7].isMoved==false ) {
					
					//they are now moved
					//space[7][4].isMoved = true;
					space[7][7].isMoved = true;		//ONLY ROOK has its isMoved set to true since the king will automatically have its isMoved to true in movePiece()
					
					//changing the object's y coordinates within their class
					space[7][4].setY(6);
					space[7][7].setY(5);
					
					//switching the pieces on the board
					space[7][5] = space[7][7];
					space[7][6] = space[7][4];
					space[7][4] = null;
					space[7][7] = null;
					
					return true;
				}
			}else if(filerank.equals("e1 c1")) { //black side castle right
				
				//checks if all spaces between rook and king are null, ALSO checks if Rook is unmoved.
				if( (space[7][1]==null) && (space[7][2]==null) && (space[7][3]==null) && space[7][0].isMoved==false ) {
					
					//they are now moved
					//space[7][4].isMoved = true;
					space[7][0].isMoved = true;		//ONLY ROOK has its isMoved set to true since the king will automatically have its isMoved to true in movePiece()
					
					//changing the object's y coordinates within their class
					space[7][4].setY(2);
					space[7][0].setY(3);
					
					//switching the pieces on the board
					space[7][3] = space[7][0];
					space[7][2] = space[7][4];
					space[7][4] = null;
					space[7][0] = null;
					
					return true;
				}
				
			}else if(filerank.equals("e8 g8")){ //black side castle right
				
				//checks if all spaces between rook and king are null, ALSO checks if Rook is unmoved.
				if( (space[0][5]==null) && (space[0][6]==null) && space[0][7].isMoved==false ) {
					
					//they are now moved
					//space[0][4].isMoved = true;
					space[0][7].isMoved = true;		//ONLY ROOK has its isMoved set to true since the king will automatically have its isMoved to true in movePiece()
					
					//changing the object's y coordinates within their class
					space[0][4].setY(6);
					space[0][7].setY(5);
					
					//switching the pieces on the board
					space[0][5] = space[0][7];
					space[0][6] = space[0][4];
					space[0][4] = null;
					space[0][7] = null;
						
					return true;
				}
					
			}else if(filerank.equals("e8 c8")) { //black side castle left
				//checks if all spaces between rook and king are null, ALSO checks if Rook is unmoved.
				if( (space[0][1]==null) && (space[0][2]==null) && (space[0][3]==null) && space[0][0].isMoved==false ) {
						
					//they are now moved
					//space[0][4].isMoved = true;
					space[0][0].isMoved = true;  	//ONLY ROOK has its isMoved set to true since the king will automatically have its isMoved to true in movePiece()
					
					//changing the object's y coordinates within their class
					space[0][4].setY(2);
					space[0][0].setY(3);
						
					//switching the pieces on the board
					space[0][3] = space[0][0];
					space[0][2] = space[0][4];
					space[0][4] = null;
					space[0][0] = null;
						
					return true;
				}
			}
				
			return false;
		}
		
		
		//reverts the castling move if this puts the king in check
		/**
		 * Reverts a castling move by moving the Rook back to its original position
		 * @param filerank is the String move entered by the player
		 * 
		 */
		public void revertCastle(String filerank) {
			
			if(filerank.equals("e1 g1")) { //reverts white castle - right
				
					//since we are reverting the move, change the rooks isMoved back to false
					space[7][5].isMoved = false;
					
					//changing the object's y coordinates within their class
					space[7][5].setY(7);
					
					//switching the pieces on the board
					space[7][7] = space[7][5];
					space[7][5] = null;
					
			}else if(filerank.equals("e1 c1")) { //reverts white castle - left
				
					//since we are reverting the move, change the rooks isMoved back to false
					space[7][3].isMoved = false;
					
					//changing the object's y coordinates within their class
					space[7][3].setY(0);
					
					//switching the pieces on the board
					space[7][0] = space[7][3];
					space[7][3] = null;
				
			}else if(filerank.equals("e8 g8")){ //reverts black castle - right
				
					//since we are reverting the move, change the rooks isMoved back to false
					space[0][5].isMoved = false;
					
					//changing the object's y coordinates within their class
					space[0][5].setY(7);
					
					//switching the pieces on the board
					space[0][7] = space[0][5];
					space[0][5] = null;
					
			}else if(filerank.equals("e8 c8")) { //reverts black castle - left
				
					//since we are reverting the move, change the rooks isMoved back to false
					space[0][3].isMoved = false;
					
					//changing the object's y coordinates within their class
					space[0][3].setY(0);
						
					//switching the pieces on the board
					space[0][0] = space[0][3];
					space[0][3] = null;
			}
			
		}	//end of revertCastle()
		
		//**** dont forget to remove the pawn from the player arraylist and add the newpiece *****
		/**
		 * Method that promotes the pawn if it reaches the other side of the board
		 * @param color the char variable representing if its white or black turn
		 * @param newPiece the char variable that specifies which type of piece we are promoting the pawn to, could be a Queen, Rook, Knight, or Bishop
		 * @param p is the player who is getting their piece promoted
		 * 
		 */
		public void promote(char color, char newPiece, Player p) {
			
			//white piece promoted
			if(color == 'w') { 
				for(int i=0; i<8; i++) {
					if(space[0][i] instanceof Pawn) {
						//System.out.println("white pawn Promoted!!");
						space[0][i] = null;
						//String filerank = Board.convertToFileRank(0, i);
						//Piece temp = getPiece(filerank);
						//p_white.pieces.remove(temp);
						removePieceFromList(p, 0, i);
						if(newPiece == 'Q') {
							space[0][i] = new Queen(p, this, 'w', 0, i);
							p.pieces.add(space[0][i]);
						}else if(newPiece == 'R') {
							space[0][i] = new Rook(p, this, 'w', 0, i);
							p.pieces.add(space[0][i]);
						}else if(newPiece == 'B') {
							space[0][i] = new Bishop(p, this, 'w', 0, i);
							p.pieces.add(space[0][i]);
						}else if(newPiece == 'N') {
							space[0][i] = new Knight(p, this, 'w', 0, i);
							p.pieces.add(space[0][i]);
						}else {
							//System.out.println("invalid Piece to be promoted to.");
							return;
						}
					}
				}
			//black piece promoted
			}else {
				for(int i=0; i<8; i++) {
					if(space[7][i] instanceof Pawn) {
						//System.out.println("black pawn Promoted!!");
						space[7][i] = null;
						//String filerank = Board.convertToFileRank(7, i);
						//Piece temp = getPiece(filerank);
						//p_black.pieces.remove(temp);
						removePieceFromList(p, 7, i);
						if(newPiece == 'Q') {
							space[7][i] = new Queen(p, this, 'b', 7, i);
							p.pieces.add(space[7][i]);
						}else if(newPiece == 'R') {
							space[7][i] = new Rook(p, this, 'b', 7, i);
							p.pieces.add(space[7][i]);
						}else if(newPiece == 'B') {
							space[7][i] = new Bishop(p, this, 'b', 7, i);
							p.pieces.add(space[7][i]);
						}else if(newPiece == 'N') {
							space[7][i] = new Knight(p, this, 'b', 7, i);
							p.pieces.add(space[7][i]);
						}else {
							//System.out.println("invalid Piece to be promoted to.");
							return;
						}
					}
				}
			}
		}
		
	/**
	 * Checks if a certain spot on the board is under attack(the space must be null)
	 * @param p the player who is checking if a piece on the board is under attack
	 * @param x the x index on the board which is being checked for danger
	 * @param y the y index on the board which is being checked for danger
	 * @return returns true if the spot is in danger, else false if the spot is safe
	 * 
	 */
	public boolean spotUnderAttack(Player p, int x, int y) {
		//tests if the piece at this location is under attack
		Piece testPiece = new King(p, this, p.color, x, y);
		space[x][y] = testPiece;
		
		ArrayList<String> opponentMoves;
		String targetLocation = "";
		
		targetLocation = Board.convertToFileRank(x, y);
		
		int i = 0;
		
		while(i < p.opponent.pieces.size()) { //each piece
			
			opponentMoves = p.opponent.pieces.get(i).generateMoves();
			
			for(String s : opponentMoves) { //filerank in opponent's arraylist for each piece
				String dest = s.substring(3, 5);
				
				if(dest.equals(targetLocation)) { 
					space[x][y] = null;
					return true;
				}
			}
			i++;
		}
		space[x][y] = null;
		return false;
	}
	
	// will call 'printBoard' to print out board
	/**
	 * prints out the whole chess_board and all of the pieces at their respective coordinates.
	 * @return returns a String 
	 */
	public String toString(){
		
		int count = 8;
		
		//print the board out in this method
		for (int i = 0; i < 8; i++){
		    for (int j = 0; j < 8; j++){
		    	if (space[i][j] != null) { 			//if the location has a piece at the index, print the piece
		    		System.out.print(space[i][j]);
		    	} else if ( (i+j) % 2 == 0 ) { 		//if the piece is null, determine if this should be a white space
		            System.out.print("   ");
		        } else { 							//if the piece is null, determine if this should be a white space
		        	System.out.print("## ");
		        }
		    	
	    		if(j == 7 && count > 0) {
	    			System.out.print(count);
	    			count--;
	    		}
		    	
		    }
		    System.out.println();
		}
		
		System.out.println(" a  b  c  d  e  f  g  h");
		
		return "";
	}
	
	
}
