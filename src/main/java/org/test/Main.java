package org.test;

import java.util.*;

public class Main {
    static final int[] dx = {1, -1, 0, 0};
    static final int[] dy = {0, 0, 1, -1};
    static final Random rand = new Random();

    static boolean isValid(int x, int y, int n, int m, char[][] maze) {
        return (x > 0 && y > 0 && x < n - 1 && y < m - 1 && maze[x][y] == '#');
    }

    static void dfs(int x, int y, char[][] maze, int n, int m, int[] end) {
        maze[x][y] = '.';
        end[0] = x;
        end[1] = y;

        List<Integer> order = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(order, rand);

        for (int i : order) {
            int nx = x + dx[i] * 2;
            int ny = y + dy[i] * 2;

            if (isValid(nx, ny, n, m, maze)) {
                maze[x + dx[i]][y + dy[i]] = '.';
                dfs(nx, ny, maze, n, m, end);
            }
        }
    }

    static int[] getRandomCell(int n, int m) {
        int x = rand.nextInt(n / 2) * 2 + 1;
        int y = rand.nextInt(m / 2) * 2 + 1;
        return new int[]{x, y};
    }

    static void createIndependentPaths(char[][] maze, int n, int m, int extraPaths) {
        for (int i = 0; i < extraPaths; i++) {
            int x = rand.nextInt(n - 2) + 1;
            int y = rand.nextInt(m - 2) + 1;

            if (maze[x][y] == '#' && maze[x - 1][y] == '#' && maze[x + 1][y] == '#' &&
                    maze[x][y - 1] == '#' && maze[x][y + 1] == '#') {
                maze[x][y] = '.';
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.close();

        if (n % 2 == 0) n++;
        if (m % 2 == 0) m++;

        char[][] maze = new char[n][m];
        for (char[] row : maze) Arrays.fill(row, '#');

        int[] start = getRandomCell(n, m);
        int[] end = {start[0], start[1]};

        dfs(start[0], start[1], maze, n, m, end);

        maze[start[0]][start[1]] = 'S';
        maze[end[0]][end[1]] = 'E';

        createIndependentPaths(maze, n, m, n * m / 10);

        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}