package fileio;

import gameplayelements.Player;

import java.util.ArrayList;

public final class GameInput {
        private StartGameInput startGame;
        private ArrayList<ActionsInput> actions;

        private int currentPlayer;

        private boolean gameEnded = false;

        private int totalGames = 0;

        public GameInput() {
        }

        public StartGameInput getStartGame() {
                return startGame;
        }

        public void setStartGame(final StartGameInput startGame) {
                this.startGame = startGame;
        }

        public int getTotalGames() {
                return totalGames;
        }

        public void setTotalGames(final int totalGames) {
                this.totalGames = totalGames;
        }

        public boolean isGameEnded() {
                return gameEnded;
        }

        public void setGameEnded(final boolean gameEnded) {
                this.gameEnded = gameEnded;
        }

        public ArrayList<ActionsInput> getActions() {
                return actions;
        }

        public void setActions(final ArrayList<ActionsInput> actions) {
                this.actions = actions;
        }

        /**
         * Getter for the current Player.
         * @return player's index
         */
        public Player getCurrentPlayer() {
                if (currentPlayer == 1) {
                        return this.getStartGame().getPlayer1();
                }
                return this.getStartGame().getPlayer2();
        }

        /**
         * Get the corresponding player.
         * @param index the player's index
         * @return the player with the coresponding index
         */
        public Player getPlayer(final int index) {
                if (index == 1) {
                        return getStartGame().getPlayer1();
                }
                return getStartGame().getPlayer2();
        }

        public void setCurrentPlayer(final int currentPlayer) {
                this.currentPlayer = currentPlayer;
        }

        @Override
        public String toString() {
                return "GameInput{"
                        +  "startGame="
                        + startGame
                        + ", actions="
                        + actions
                        + '}';
        }
}
