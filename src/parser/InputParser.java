package parser;

import model.MainBoard;
import model.Piece;

import java.util.List;

public interface InputParser {
    MainBoard getBoard();

    List<Piece> getPieces();
}
