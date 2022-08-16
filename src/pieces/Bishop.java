package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Tile;

public class Bishop extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};


    public Bishop( final Alliance pieceAlliance, final int piecePosition){
        super( pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){
        
        final List<Move> legalMoves = new ArrayList<>();
        
        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){            
            int candidateDestinationCoordinate = this.piecePosition;
            
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                
                if(isFirstColumExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExlusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break; //CONTROLLA QUI.
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                        final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccuped()){
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // this = the current bishop
                    }else{ //if it's occupied
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(this.pieceAlliance != pieceAlliance){ // If the tile is occupied by an enemy  
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination) );
                        }
                        break; /**This break is used for indicate that after any piece(enemy or not) the Bishop cannot move */
                    }
                }
                
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString(){
        return Piece.PieceType.BISHOP.toString();
    }


    private static boolean isFirstColumExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExlusion (final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7);
    }
}
