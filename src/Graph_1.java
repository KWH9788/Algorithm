import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
    static Character[][] cave;
    static Queue<Location> queue;
    static ArrayList<Location> clusterList;
    static int[][] map;

    public static void main(String[] args) throws Exception {
        int N;
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
        int direction = 1;
        for(int i = 0; i < N; i++) {
            stick(Integer.parseInt(st.nextToken()), direction);
            if(direction == 1) direction = 2;
            else direction = 1;

            findCluster();
        }
        print();
    }

    static void print() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++) {
                sb.append(cave[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    static void findCluster() {
        map = new int[R][C];

        for(int i = 0; i < R; i++) {
            for(int j = 0; j < C; j++) {
                if(map[i][j] == 0 && cave[i][j] == 'x') {
                    if(cluster(i, j)) return;
                }
            }
        }
    }

    // 공중에 떠 있는 클러스터 탐색
    static boolean cluster(int r, int c) {
        queue = new LinkedList<>();
        clusterList = new ArrayList<Location>();

        int maxRow;
        int[] plusRow = {-1, 0, +1, 0};
        int[] plusCol = {0, +1, 0, -1};

        Location now = new Location(r, c);

        queue.add(now);
        map[now.row][now.col] = 1;
        maxRow = now.row;
        
        while(!queue.isEmpty()) {
            now = queue.poll();
            
            // 네방향 탐색
            for(int i = 0; i < 4; i++){
                int row = now.row + plusRow[i];
                int col = now.col + plusCol[i];
                // index 범위 초과 방지
                if(row > R - 1 || row < 0 || col > C - 1 || col < 0) continue;
                if(cave[row][col] == 'x' && map[row][col] == 0) {
                    queue.add(new Location(row, col));
                    map[row][col] = 1;
                }
                if(maxRow < row) maxRow = row;
            }
            // 클러스터 위치 정보 추가
            clusterList.add(now);
        }
        // 클러스터가 공중에 떠 있는 경우
        if(maxRow != R - 1) {
            downCluster();
            return true;
        }
        return false;
    }

    static void downCluster() {
        Location mineral;
        int plusIndex = 0;
        // 바닥 충돌 체크

        // 공중에 떠 있는 클러스터 . 으로 변경
        for(int i = 0; i < clusterList.size(); i++) {
            mineral = clusterList.get(i);
            cave[mineral.row][mineral.col] = '.';
        }

        // 클러스터가 다른 클러스터위에 떨어지거나 바닥에 떨어질때 까지 반복
        down:
        while(true) {
            for(int i = 0; i < clusterList.size(); i++) {
                mineral = clusterList.get(i);
                // 바닥을 넘기거나
                if(mineral.row + plusIndex + 1 == R) break down;
                // 다른 클러스터와 겹칠 경우
                else if(cave[mineral.row + plusIndex + 1][mineral.col] == 'x') break down;
            }
            plusIndex ++;
        }

        for(int i = 0; i < clusterList.size(); i++) {
            mineral = clusterList.get(i);
            cave[mineral.row + plusIndex][mineral.col] = 'x';
        }
    }

    static void stick(int r, int direction) {
        // r = 높이 -> R - r
        // direction = 1 : 왼쪽에서 오른쪽, 2 : 오른쪽에서 왼쪽
        int row = R - r;
        
        if(direction == 1) {
            for(int i = 0; i < C; i++) {
                if(cave[row][i] == 'x') {
                    cave[row][i] = '.';
                    return;
                }
            }
        }
        else if(direction == 2) {
            for(int i = C - 1; i >= 0; i--) {
                if(cave[row][i] == 'x') {
                    cave[row][i] = '.';
                    return;
                }
            }
        }
    }
}
