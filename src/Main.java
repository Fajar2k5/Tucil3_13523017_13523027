
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Papan papan = new Papan();
        papan.readFromFile("tes.txt");
        Map<Character, Piece> pieces = papan.getMapPiece();
        pieces.put('P', papan.getPrimaryPiece());
        System.err.println("Exit: " + papan.getKeluarX() + " " + papan.getKeluarY());
        State initialState = new State(pieces, papan, 0, null, "State Awal");
        // UCS.uniformCostSearch(initialState);
        // AStarSearch.solve(initialState);
        IDAStar.solve(initialState);
    }
}
