package pieces;

//import java.util.List;
import java.util.Collection;

import board.Board;
import board.Move;


public abstract class Piece{

    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;

    Piece( final Alliance pieceAlliance, final int piecePosition){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.isFirstMove = false;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }
    public int getPiecePosition(){
        return this.piecePosition;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);
    
    public boolean isFirstMove(){
        return this.isFirstMove;
    }


    public enum PieceType{

        PAWN("P"),
        ROOK("R"),
        BISHOP("B"),
        QUEEN("Q"),
        KING("K"),
        KNIGHT("N");

        
        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }
    }
}