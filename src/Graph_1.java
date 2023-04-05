import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Graph_1 {
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
            
        }
    }
    static int R, C;
    static Queue<Location> queue;
    static ArrayList<Location> clusterList;

    public static void main(String[] args) throws Exception {
        int N;
        Character[][] cave;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        cave = new Character[R][C];
        for(int i = 0; i < R; i++) {
            String str = br.readLine();
            for(int j = 0; j < C; j++) {
                cave[i][j] = str.charAt(j);
            }
        }
        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            
        }
        cluster(7, 1, cave);
    }
    static void cluster(int r, int c, Character[][] cave) {
        queue = new LinkedList<>();
        clusterList = new ArrayList<Location>();

        boolean[][] visited = new boolean[R][C];

        int maxRow;
        int[] plusRow = {-1, 0, +1, 0};
        int[] plusCol = {0, +1, 0, -1};

        Location now = new Location(r, c);

        queue.add(now);
        maxRow = now.row;
        
        while(!queue.isEmpty()) {
            now = queue.poll();
            visited[now.row][now.col] = true;
            
            // 네방향 탐색
            for(int i = 0; i < 4; i++){
                int row = now.row + plusRow[i];
                int col = now.col + plusCol[i];
                // index 범위 초과 방지
                if(row > R - 1 || row < 0 || col > C - 1 || col < 0) continue;
                if(cave[row][col] == 'x' || !visited[row][col]) queue.add(new Location(row, col));
                if(maxRow < row) maxRow = row;
            }
            // 클러스터 위치 정보 추가
            clusterList.add(now);
        }
        // 클러스터가 공중에 떠 있는 경우
        if(maxRow != R - 1) {
            System.out.println("공중에 떠 있음");
            return;
        }
        System.out.println("바닥에 있음");
    }
}
