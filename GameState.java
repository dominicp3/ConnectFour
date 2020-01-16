public class GameState
{
        private char[][] grid;
        private static int numRow = 6;
        private static int numCol = 7;

        public GameState()
        {
                grid = new char[numRow][numCol];                
                for (int i = 0; i < numRow; i++) {
                        for (int j = 0; j < numCol; j++)
                                grid[i][j] = 0;
                }
        }

        private GameState(char[][] board)
        {
                grid = board;
        }

        public char[] getColumn(int column)
        {
                char[] temp;
                
                if (column < 0 || column > numCol) return null;
                
                temp = new char[numRow];
                
                for (int i = 0; i < numRow; i++)
                        temp[i] = grid[i][column];
                
                return temp;
        }

        public boolean setColumn(int column, char player)
        {
                for (int r = 0; r < numRow; r++) {
                        if (grid[r][column] == 0) {
                                grid[r][column] = player;
                                return true;
                        }
                }
                return false;
        }

        public boolean setBoard(char[][] board)
        {
                for (int r = 0; r < numRow; r++) {
                        for (int c = 0; c < numCol; c++)
                                this.grid[r][c] = board[r][c];
                }
                
                return true;
        }

        public boolean isFull(int column)
        {
                for (int r = 0; r < numRow; r++)     
                        if (grid[r][column] == 0) return false;     
                
                return true;
        }

        public char[][] getBoard()
        {
                char[][] temp = new char[numRow][numCol];
                for (int r = 0; r < numRow; r++) {
                        for (int c = 0; c < numCol; c++)
                                temp[r][c] = grid[r][c];
                }
                return temp;
        }

        public boolean isDraw()
        {
                for (int c = 0; c < numCol; c++)
                        if (!isFull(c)) return false;
                return true;
        }

        public char hasWon()
        {
//              Horizontal
                for (int r = 0; r < numRow; r++) {
                        for (int c = 0; c < numCol-3; c++) {
                                if (grid[r][c] == 0) continue;
                                if (grid[r][c] == grid[r][c+1] && grid[r][c+1] == grid[r][c+2] && grid[r][c+2] == grid[r][c+3])              
                                        return grid[r][c];
                        }
                }
                
//              Vertical
                for (int r = 0; r < numRow-3; r++) {
                        for (int c = 0; c < numCol; c++) {
                                if (grid[r][c] == 0) continue;
                                if (grid[r][c] == grid[r+1][c] && grid[r+1][c] == grid[r+2][c] && grid[r+2][c] == grid[r+3][c])
                                        return grid[r][c];
                        }
                }
                
//              Diagonal (positive slope)
                for (int r = 0; r < numRow-3; r++) {
                        for (int c = 0; c < numCol-3; c++) {
                                if (grid[r][c] == 0) continue;
                                if (grid[r][c] == grid[r + 1][c + 1] && grid[r + 1][c + 1] == grid[r + 2][c + 2] && grid[r + 2][c + 2] == grid[r + 3][c + 3])
                                        return grid[r][c];
                        }
                }
                
//              Diagonal (negative slope)
                for (int r = 0; r < numRow-3; r++) {
                        for (int c = 3; c < numCol; c++) {
                                if (grid[r][c] == 0) continue;
                                if (grid[r][c] == grid[r + 1][c - 1] && grid[r+1][c-1] == grid[r+2][c-2] && grid[r + 2][c - 2] == grid[r + 3][c - 3])
                                        return grid[r][c];
                        }
                }
                
                return 0;
        }

        public GameState getCopy()
        {
                return new GameState(getBoard()); 
        }

        public void printBoard()
        {
                for (int r = grid.length - 1; r >= 0; r--) {
                        for (int c = 0; c < grid[0].length; c++) {
                                
                                if (c + 1 == grid[0].length) {
                                        System.out.printf("%c|\n", grid[r][c]);
                                        break;
                                }
                                
                                if (c == 0)
                                        System.out.printf("|%c, ", grid[r][c]);
                                else
                                        System.out.printf("%c, ", grid[r][c]);
                                
                        }
                }
        }
}