package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Tile;

public class Knight extends Piece {
    
    /** Suppose that the night's situate in the d4 Tile. If we start count the tile from a8 (Tile 0), for example\
     * d4 corrispondes to 35. We can find the candididates legal move add or subtracting from the night's current position
     * 17, 15, 10 or 6. 
     */

    private static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17}; 

    public  Knight( final Alliance pieceAlliance, final int piecePosition){
        super( pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){
        
       
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
               
                if(isFirstColumExclusion(this.piecePosition, currentCandidateOffset) || 
                        isSecondColumExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExlusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumExclusion(this.piecePosition, currentCandidateOffset)){
                    continue; //break?
                }
                
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccuped()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // this = the current knight
                } else{ //if it's occupied
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance != pieceAlliance){ // If the tile is occupied by an enemy  
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination) );
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString(){
        return Piece.PieceType.KNIGHT.toString();
    }

    private static boolean isFirstColumExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10
        || candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] &&  (candidateOffset == -6 || candidateOffset == 10);
    }
    
    private static boolean isEighthColumnExlusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition]  && (candidateOffset == 17 || candidateOffset == 10
        || candidateOffset == -6 || candidateOffset == -15);
    }
}
