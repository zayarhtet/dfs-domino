package parser;

import model.MainBoard;
import model.Piece;

import java.util.List;

public interface InputParser {
    void read();
    MainBoard getBoard();

    List<Piece> getPieces();


}
