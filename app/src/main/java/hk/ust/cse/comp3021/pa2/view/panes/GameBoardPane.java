package hk.ust.cse.comp3021.pa2.view.panes;

import hk.ust.cse.comp3021.pa2.model.EntityCell;
import hk.ust.cse.comp3021.pa2.model.GameState;
import hk.ust.cse.comp3021.pa2.model.Player;
import hk.ust.cse.comp3021.pa2.util.NotImplementedException;
import hk.ust.cse.comp3021.pa2.view.GameUIComponent;
import hk.ust.cse.comp3021.pa2.view.controls.GameCell;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * A {@link javafx.scene.layout.Pane} for displaying the status of {@link hk.ust.cse.comp3021.pa2.model.GameBoard}.
 */
public class GameBoardPane extends GridPane implements GameUIComponent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeComponents() {
        this.setAlignment(Pos.CENTER);
        this.setCenterShape(true);
    }

    /**
     * Updates the game board display with latest {@link GameState}.
     *
     * @param gameState The latest {@link GameState}.
     */
    public void showGameState(GameState gameState) {
        // TODO: Update the content based on the state of the game board.
        for (int i=0; i<gameState.getGameBoard().getNumRows(); i++) {
            for (int j=0; j<gameState.getGameBoard().getNumCols(); j++) {
                //create the image view for each cell
                var iv = new GameCell(gameState.getGameBoard().getCell(i,j));
                this.add(iv, j, i);
            }
        }

        //throw new NotImplementedException();
    }

}
