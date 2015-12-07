import java.util.Random;

import static org.junit.Assert.*;

public class MultiplierTest {
    final int N = 2000;
    final int M = 2000;
    final int K = 2000;
    @org.junit.Test
    public void testMultiply() throws Exception {
        Random rnd = new Random();
        int[][] A = new int[N][M];
        int[][] B = new int[M][K];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                A[i][j] = rnd.nextInt(1000);
            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < K; j++) {
                B[i][j] = rnd.nextInt(1000);
            }
        }
        long startTime = System.nanoTime();
        int[][] resMultiplier = Multiplier.multiply(N, M, K, A, B);
        long endTime = System.nanoTime();
        int[][] resCorrect = new int[N][K];
        long startTime2 = System.nanoTime();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                for (int w = 0; w < M; w++) {
                    resCorrect[i][j] += A[i][w] * B[w][j];
                }
            }
        }
        long endTime2 = System.nanoTime();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                assertTrue(resMultiplier[i][j] == resCorrect[i][j]);
            }
        }
        System.out.println("Time for Multiplier: " + ((endTime - startTime) / 1000000));
        System.out.println("Time for simple multiplication: " + ((endTime2 - startTime2) / 1000000));
    }
}