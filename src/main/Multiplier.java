public class Multiplier {
    private static class Solver {
        int[][] A, B, result;
        int n, m, k;
        int countThreads;

        private final int CountThreadsDefault = 4;

        public Solver(int n, int m, int k, int[][] A, int[][] B) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.A = A;
            this.B = B;
            result = new int[n][k];
            countThreads = CountThreadsDefault;
        }

        private class ParallelSolver implements Runnable {
            int l, r;
            public ParallelSolver(int l, int r) {
                this.l = l;
                this.r = r;
            }
            public void run() {
                for (int i = l; i < r; i++) {
                    for (int j = 0; j < k; j++) {
                        for (int w = 0; w < m; w++) {
                            result[i][j] += A[i][w] * B[w][j];
                        }
                    }
                }
            }
        }

        public void solve() throws Exception {
            int step = n / countThreads;
            int cnt = n % countThreads;
            int l = 0;
            Thread[] threads = new Thread[countThreads];
            for (int i = 0; i < countThreads; i++) {
                int r = l + step + (i < cnt ? 1 : 0);
                threads[i] = new Thread(new ParallelSolver(l, r));
                threads[i].start();
                l = r;
            }
            for (int i = 0; i < countThreads; i++) {
                threads[i].join();
            }
        }
    }
    static int[][] multiply(int n, int m, int k, int[][] A, int [][] B) throws Exception {
        Solver solver = new Solver(n, m, k, A, B);
        solver.solve();
        return solver.result;
    }
    static int[][] multiply(int n, int m, int k, int[][] A, int [][] B, int countThreads) throws Exception {
        Solver solver = new Solver(n, m, k, A, B);
        solver.countThreads = countThreads;
        solver.solve();
        return solver.result;
    }
}
