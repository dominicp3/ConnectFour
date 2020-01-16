public class Game {
		private GameState state = new GameState();
	    private Computer computer = new Computer(9);
	    private boolean player = false; // false = player, true = cpu
        private int status = 0;         // 0 = running, 1 = draw, 2 = won

        public boolean playerMove(int column)
        {
        		if (state.isFull(column) || status != 0 || !state.setColumn(column, player ? 'Y' : 'R')) {
        				return false;
        		}
        		
        		player = !player;

        		if (state.hasWon() != 0) {
        				status = 2;
        				return true;
        		}

        		if (state.isDraw()) {
        				status = 1;
        				return true;
        		}

        		return true;
        }

        public void cpuMove()
        {
        		if (status != 0) return;
	    		state = computer.nextBoard(state, 'Y');
	    		player = !player;
    			
    			if (state.hasWon() != 0) {
	    				status = 2;
	    				return;
    			}

	    		if (state.isDraw()) {
	    				status = 1;
	    				return;
	    		}
        }

        public GameState getState()
        {
        		return state.getCopy();
        }

        public int status()
        {
        		return status;
        }

        public boolean player()
        {
        	return player;
        }
        
        public void reset()
        {
        		status = 0;
        		player = false;
        		state = new GameState();
        }
}