package pieces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Tile;

public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King( final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
        
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        
        final List<Move> legalMoves = new ArrayList<>();
        
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
           final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

           if(isFirstColumExclusion(this.piecePosition, currentCandidateOffset) ||
                isEighthColumnExlusion(this.piecePosition, currentCandidateOffset)){
            continue;
           }

           if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
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

        return Collections.unmodifiableList(legalMoves); // èidpsidp'iu
    }

    @Override
    public String toString(){
        return Piece.PieceType.KING.toString();
    }

    private static boolean isFirstColumExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1
        || candidateOffset == 7);
    }

    private static boolean isEighthColumnExlusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 
        || candidateOffset == 9);
    }
    
}
