import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Samsung13 {
    static class Fish {
        int x, y, size = 2;
        Fish(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static class Node {
        int x, y;
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int N, eated, answer = 0;
    static int[][] map;
    static Fish shark;
    static Queue<Node> bfs = new LinkedList<>();
    static boolean visited[][];
    static int[] plusX = { 0, 1, 0, -1 };
    static int[] plusY = { 1, 0, -1, 0 };
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        visited = new boolean[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 9) {
                    shark = new Fish(j, i);
                    bfs.add(new Node(j, i));
                    visited[i][j] = true;
                    map[i][j] = 0;
                }
            }
        }
        bfs();
    }

    static void bfs() {
        int level = 0;
        Node now;
        Node eating = new Node(-1, -1);
        boolean found = false;
        eated = 0;
        while(true) {
            int n = bfs.size();
            for(int i = 0; i < n; i++) {
                now = bfs.poll();
                // 물고기가 있고 상어보다 크기가 작을 경우
                if(map[now.y][now.x] < shark.size && map[now.y][now.x] != 0) {
                    // 처음 발견한 물고기
                    if(!found) {
                        eating = new Node(now.x, now.y);
                        found = true;
                    }
                    // 같은 거리의 물고기
                    else {
                        // 우선순위 조건
                        if(eating.y > now.y) {
                            eating = new Node(now.x, now.y);
                        }
                        else if(eating.x > now.x && eating.y == now.y) {
                            eating = new Node(now.x, now.y);
                        }
                    }
                }
                for(int j = 0; j < 4; j++) {
                    // 인덱스 초과 방지 && 방문체크
                    if(now.y + plusY[j] >= 0 && now.y + plusY[j] < N && now.x + plusX[j] >= 0 && now.x + plusX[j] < N && !visited[now.y + plusY[j]][now.x + plusX[j]]){
                        // 상어 보다 작거나 같은 물고기
                        if(shark.size >= map[now.y + plusY[j]][now.x + plusX[j]]) {
                            bfs.add(new Node(now.x + plusX[j], now.y + plusY[j]));
                            visited[now.y + plusY[j]][now.x + plusX[j]] = true;
                        }
                    }
                }
            }
            if(found) {
                // 이동한 거리(시간) 더하기
                answer += level;

                shark.x = eating.x;
                shark.y = eating.y;
                eated++;
                map[eating.y][eating.x] = 0;
                if(eated == shark.size) {
                    shark.size++;
                    eated = 0;
                }

                // bfs 탐색 초기화
                found = false;
                visited = new boolean[N][N];
                level = 0;
                bfs.clear();
                bfs.add(new Node(shark.x, shark.y));
                visited[shark.y][shark.x] = true;
            }
            else {
                level++;
            }

            // 먹을 수 있는 물고기가 없을 경우
            if(bfs.isEmpty()) {
                System.out.println(answer);
                break;
            }
        }
    }
}
