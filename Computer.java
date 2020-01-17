import java.util.ArrayList;
import java.util.Collections;

public class Computer
{
        private int depth_stop;

        public Computer(int depth)
        {
                depth_stop = depth;
        }

        public int getDepth()
        {
                return depth_stop;
        }

        public GameState nextBoard(GameState state, char player) {
                boolean maximiser = (player == 'Y') ? true : false;
                int col = nextMove(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth_stop, maximiser);
                GameState tmp = state.getCopy();
                tmp.setColumn(col, player);
                return tmp;
        }

        private GameState[] actions(GameState state, char player)
        {
                GameState temp;
                GameState[] list = new GameState[7];

                for (int c = 0; c < 7; c++) {
                        temp = state.getCopy();
                        if (temp.setColumn(c, player))
                                list[c] = temp;
                        else
                                list[c] = null;
                }

                return list;
        }

        private int nextMove(GameState state, int alpha, int beta, int depth, boolean maximiser)
        {
                char player = state.hasWon();
                if (player == 'Y') return 10000;
                if (player == 'R') return -10000;
                if (state.isDraw()) return 0;
                if (depth <= 0) return utility(state);

                int value = maximiser ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                int index = 0;

                GameState[] actions = maximiser ? actions(state, 'Y') : actions(state, 'R');

                int[] values = new int[7];
                if (depth == depth_stop)
                        for (int i = 0; i < 7; i++)
                                values[i] = Integer.MIN_VALUE;

                if (maximiser)
                        for (int i : randomIndices(7)) {
                                if (actions[i] == null) continue;
                                int temp = nextMove(actions[i], alpha, beta, depth - 1, false);
                                if (temp > value) {value = temp; index = i; if (depth == depth_stop) values[i] = temp;}
                                if (value > alpha) alpha = value;
                                if (alpha >= beta) break;
                        }
                else
                        for (int i : randomIndices(7)) {
                                if (actions[i] == null) continue;
                                int temp = nextMove(actions[i], alpha, beta, depth - 1, true);
                                if (temp < value) {value = temp; index = i;}
                                if (value < beta) beta = value;
                                if (alpha >= beta) break;
                        }

                if (depth == depth_stop) {
                        System.out.printf("[");
                        for (int i : values)
                                System.out.printf("%d, ", i);
                        System.out.printf("]\n");
                        return index;
                }
                return value;
        }

        private int utility(GameState state)
        {
                return evaluation(state, 'Y') - evaluation(state, 'R');
        }

        private int evaluation(GameState state, char player)
        {
                int num = 0;
                num += numInRow(2, state, player);
                num += numInRow(3, state, player);
                return num;
        }

        @SuppressWarnings("unused")
        private void printArray(int[] nums)
        {
                System.out.print("[");
                for (int i = 0; i < nums.length; i++) {
                        if (i + 1 == nums.length) {
                                System.out.printf("%d]\n\n", nums[i]);
                                break;
                        }
                        System.out.printf("%d, ", nums[i]);
                }
        }

        private int[] randomIndices(int size)
        {
                if (size < 1) return null;
                ArrayList<Integer> list = new ArrayList<Integer>(size);
                int[] arr = new int[size];
                for (int i = 0; i < size; i++) list.add(i);
                Collections.shuffle(list);
                for (int i = 0; i < size; i++) arr[i] = list.get(i);
                return arr;
        }

        private int numInRow(int count, GameState state, char player)
        {
                char[][] grid = state.getBoard();
                int num = 0;

                num += horizontal(count, grid, player);
                num += diagonal_positive(count, grid, player);
                num += diagonal_negative(count, grid, player);

                return num;
        }

        private int horizontal(int count, char[][] grid, char player)
        {
                int numRow = 6;
                int numCol = 7;

                int num = 0;

                for (int r = 0; r < numRow; r++) {
                        for (int c = 0; c < numCol; c++) {
                                char t = grid[r][c];
                                if (t != player) continue;
                                for (int i = 0; i < count; i++) {

                                        if (c + i < numCol && grid[r][c + i] != t)
                                                break;

                                        if (c + i >= numCol)
                                                break;

                                        if (c - 1 >= 0 && grid[r][c - 1] == t)
                                                break;

                                        if (i + 1 == count) {

                                                if (c + count < numCol && grid[r][c + count] == t)
                                                        break;

                                                if (c > 0 && grid[r][c - 1] == 0) {
                                                        if (player == 'R' && r % 2 == 0)
                                                                num += count == 2 ? 8 : 40;
                                                        else if (player == 'Y' && r % 2 == 1)
                                                                num += count == 2 ? 8 : 40;
                                                        else
                                                                num += count == 2 ? 3 : 15;
                                                }

                                                if (c + count < numCol && grid[r][c + count] == 0) {
                                                        if (player == 'R' && r % 2 == 0)
                                                                num += count == 2 ? 8 : 40;
                                                        else if (player == 'Y' && r % 2 == 1)
                                                                num += count == 2 ? 8 : 40;
                                                        else
                                                                num += count == 2 ? 3 : 15;
                                                }

                                        }

                                }
                        }
                }

                return num;
        }

        private int diagonal_positive(int count, char[][] grid, char player)
        {
                int numRow = 6;
                int numCol = 7;

                int num = 0;

                for (int r = 0; r < numRow; r++) {
                        for (int c = 0; c < numCol; c++) {
                                char t = grid[r][c];
                                if (t != player) continue;
                                for (int i = 0; i < count; i++) {

                                        if (r + i < numRow && c + i < numCol && grid[r + i][c + i] != t)
                                                break;

                                        if (r + i >= numRow || c + i >= numCol)
                                                break;

                                        if (r - 1 >= 0 && c - 1 >= 0 && grid[r - 1][c - 1] == t)
                                                break;

                                        if (i + 1 == count) {
                                                if (r + count < numRow && c + count < numCol && grid[r + count][c + count] == t )
                                                        break;

                                                if (r > 0 && c > 0 && grid[r - 1][c - 1] == 0) {
                                                        if (player == 'R' && r - 1 % 2 == 0)
                                                                num += count == 2 ? 8 : 40;
                                                        else if (player == 'Y' && r - 1 % 2 == 1)
                                                                num += count == 2 ? 8 : 40;
                                                        else
                                                                num += count == 2 ? 3 : 15;
                                                }

                                                if (r + count  < numRow && c + count < numCol && grid[r + count][c + count] == 0) {
                                                        if (player == 'R' && r + count % 2 == 0)
                                                                num += count == 2 ? 8 : 40;
                                                        else if (player == 'Y' && r + count % 2 == 1)
                                                                num += count == 2 ? 8 : 40;
                                                        else
                                                                num += count == 2 ? 3 : 15;
                                                }

                                        }

                                }
                        }
                }

                return num;
        }

        private int diagonal_negative(int count, char[][] grid, char player)
        {
                int numRow = 6;
                int numCol = 7;

                int num = 0;

                for (int r = 0; r < numRow; r++) {
                        for (int c = 0; c < numCol; c++) {
                                char t = grid[r][c];
                                if (t != player) continue;
                                for (int i = 0; i < count; i++) {

                                        if (r + i < numRow && c - i >= 0 && grid[r + i][c - i] != t)
                                                break;

                                        if (r + i >= numRow || c - i < 0)
                                                break;

                                        if (r - 1 >= 0 && c + 1 < numCol && grid[r - 1][c + 1] == t)
                                                break;

                                        if (i + 1 == count) {
                                                if (r + count < numRow && c - count >= 0 && grid[r + count][c - count] == t)
                                                        break;

                                                if (r > 0 && c + 1 < numCol && grid[r - 1][c + 1] == 0) {
                                                        if (player == 'R' && r - 1 % 2 == 0)
                                                                num += count == 2 ? 8 : 40;
                                                        else if (player == 'Y' && r - 1 % 2 == 1)
                                                                num += count == 2 ? 8 : 40;
                                                        else
                                                                num += count == 2 ? 3 : 15;
                                                }

                                                if (r + count  < numRow && c - count >= 0 && grid[r + count][c - count] == 0) {
                                                        if (player == 'R' && r + count % 2 == 0)
                                                                num += count == 2 ? 8 : 40;
                                                        else if (player == 'Y' && r + count % 2 == 1)
                                                                num += count == 2 ? 8 : 40;
                                                        else
                                                                num += count == 2 ? 3 : 15;
                                                }

                                        }

                                }
                        }
                }

                return num;
        }
}