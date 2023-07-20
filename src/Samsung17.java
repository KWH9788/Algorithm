import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Samsung17 {
    static class Virus {
        int x, y;
        Virus(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int N, M, answer = -1, m;
    static int[][] lab;
    static boolean[][] visited;
    static boolean[] v;
    static Queue<Virus> bfs = new LinkedList<>(), actbfs = new LinkedList<>();
    static Virus[] viruses;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        lab = new int[N][N];
        viruses = new Virus[N * N];
        m = 0;
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                lab[i][j] = Integer.parseInt(st.nextToken());
                if(lab[i][j] == 2) {
                    viruses[m] = new Virus(j, i);
                    m++;
                }
            }
        }

        v = new boolean[m];
        backtracking(0, new Virus[M], 0);
        System.out.println(answer);
    }

    static void backtracking(int idx, Virus[] actVirus, int curidx) {
        if(idx == M) {
            for(int i = 0; i < M; i++) {
                bfs.add(new Virus(actVirus[i].x, actVirus[i].y));
            }
            int temp = BFS(duplicate(lab));
            
            if(answer == -1 && temp != -1) answer = temp;
            else if(answer > temp && temp != -1) answer = temp;
            return;
        }
        for(int i = curidx; i < m; i++) {
            if(idx == 0 && i + M - 1 > m) break;
            if(v[i] == false) {
                actVirus[idx] = new Virus(viruses[i].x, viruses[i].y);
                v[i] = true;
                backtracking(idx+1, actVirus, i+1);
                v[i] = false;
            }
        }
    }

    static int[] px = {0, 1, 0, -1}, py = {1, 0, -1, 0};
    static int BFS(int[][] map) {
        ///
        ///  시뮬레이션 사전 준비
        ///
        Virus node;
        visited = new boolean[N][N];
        int time = 0;
        int actTime = 0;
        int size;
        boolean t;
        // 활성 바이러스 방문 처리
        for(int i = 0; i < M; i++) {
            node = bfs.poll();
            visited[node.y][node.x] = true;
            bfs.add(new Virus(node.x, node.y));
        }

        ///
        /// 시뮬레이션 시작
        ///
        while(true) {
            t = false;
            // 활성바이러스가 1초동안 퍼지는걸 구현
            size = bfs.size();
            for(int i = 0; i < size; i++) {
                node = bfs.poll();
                for(int j = 0; j < 4; j++) {
                    // index 초과 방지
                    if(node.x + px[j] >= N || node.y + py[j] >= N 
                        || node.x + px[j] < 0 || node.y + py[j] < 0) continue;
                    // 방문 체크
                    if(visited[node.y + py[j]][node.x + px[j]] == false) {
                        // 빈 칸에 바이러스 퍼트리기
                        if(map[node.y + py[j]][node.x + px[j]] == 0 ) {
                            map[node.y + py[j]][node.x + px[j]] = -1;
                            bfs.add(new Virus(node.x + px[j], node.y + py[j]));
                            visited[node.y + py[j]][node.x + px[j]] = true;
                            if(t == false) {
                                time++;
                                t = true;
                                time += actTime;
                                actTime = 0;
                            }
                        }
                        // 비활성화 바이러스를 활성화 (시간 영향 X)
                        else if(map[node.y + py[j]][node.x + px[j]] == 2 ) {
                            bfs.add(new Virus(node.x + px[j], node.y + py[j]));
                            visited[node.y + py[j]][node.x + px[j]] = true;
                        }
                    }
                }
            }
            if(bfs.isEmpty()) break;
            if(!t) actTime++;
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j] == 0) return -1;
            }
        }

        return time;
    }

    static int[][] duplicate(int[][] original) {
        int[][] copy = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }
}
