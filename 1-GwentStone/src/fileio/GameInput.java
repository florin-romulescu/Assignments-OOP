package fileio;

import gameplay_elements.Player;

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

        public void setTotalGames(int totalGames) {
                this.totalGames = totalGames;
        }

        public boolean isGameEnded() {
                return gameEnded;
        }

        public void setGameEnded(boolean gameEnded) {
                this.gameEnded = gameEnded;
        }

        public ArrayList<ActionsInput> getActions() {
                return actions;
        }

        public void setActions(final ArrayList<ActionsInput> actions) {
                this.actions = actions;
        }

        public Player getCurrentPlayer() {
                if (currentPlayer == 1) {
                        return this.getStartGame().player1;
                }
                return this.getStartGame().player2;
        }

        public Player getPlayer(int index) {
                if (index == 1)
                        return getStartGame().player1;
                return getStartGame().player2;
        }

        public void setCurrentPlayer(int currentPlayer) {
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
