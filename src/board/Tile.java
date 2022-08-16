package board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
// import java.util.WeakHashMap;

import pieces.Piece;



public abstract class Tile {
    
    protected final int tileCordinate;
    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
    
    public static Tile createTile(final int tileCoodinate, final Piece piece){
        return piece != null ? new OccupiedTile(piece, tileCoodinate) : EMPTY_TILE_CACHE.get(tileCoodinate);
    }

    private Tile(final int tileCordinate){
        this.tileCordinate = tileCordinate;
    }

    private static Map<Integer, EmptyTile>createAllPossibleEmptyTiles(){
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
       
        return Collections.unmodifiableMap(emptyTileMap);
        //also if you want import Guva's library, you can return ImmutableMap.copyOf(emptyTileMap)
    }

    public abstract boolean isTileOccuped();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        EmptyTile(final int coodinate){
            super(coodinate);
        }

        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isTileOccuped(){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;
        
        OccupiedTile(final Piece pieceOnTile, int tileCoodinate){
            super(tileCoodinate);
            this.pieceOnTile = pieceOnTile;
            
        }
        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : 
                   getPiece().toString();
        }

        @Override
        public boolean isTileOccuped(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return pieceOnTile;
        }
        
    }
}
