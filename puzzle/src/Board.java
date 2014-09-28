public class Board {

    private int N;
    
    private int[][] tiles;
    // twin board - swapped 2 adj tiles in a row
    private int[][] tiles2;
    private int[][] goal;

    private MinPQ<Board> queue = new MinPQ<Board>();
    private MinPQ<Board> queue2 = new MinPQ<Board>();
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks[0].length;
        tiles = new int[N][N];
        tiles2 = new int[N][N];
        goal = new int[N][N];
        
        int g = 1;
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                tiles[i][j] = blocks[i][j];
                //todo: twin
                goal[i][j] = g++;
            }
        }
        goal[N-1][N-1] = 0;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int h = 0;
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (tiles[i][j] != goal[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    // sum of Manhattan distances between blocks and goal
    // sum of the vertical and horizontal distance) from the blocks to their goal positions
    public int manhattan() {
        int m = 0;
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                // goal
                int x = goal[i][j];
                if (x == 0)
                    continue;
                // find matching
                for (int r=0; r<N; r++) {
                    for (int c=0; c<N; c++) {
                        int y = tiles[i][j];
                        if (x != y)
                            continue;
                        // match - find number of rows/cols deltas
                        int dr = i - r;
                        //if (dr < 0)
                        //    dr = -dr;
                        int dc = j - c;
                        //if (dc < 0)
                        //    dc = -dc;
                        m += dr + dc;
                        break;
                    }
                }
            }
        }
        return m;
    }
    
    /*
      8  1  3        1  2  3     1  2  3  4  5  6  7  8    1  2  3  4  5  6  7  8
      4  0  2        4  5  6     ----------------------    ----------------------
      7  6  5        7  8  0     1  1  0  0  1  1  0  1    1  2  0  0  2  2  0  3

      initial        goal         Hamming = 5 + 0          Manhattan = 10 + 0

     */

    // is this board the goal board?
    public boolean isGoal() {
        return tiles.equals(goal);
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        return new Board(tiles2);
    }

    // does this board equal y?
    public boolean equals(Object x) {
        if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        Board that = (Board) x;
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return queue;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();       
    }
}
