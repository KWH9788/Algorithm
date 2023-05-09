import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Samsung11 {
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int[] px = { 0, 1, 0, -1 };
    static int[] py = { 1, 0, -1, 0 };
    static boolean[][] visited;

    static int N, L, R;
    static int[][] map;
    static int answer = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        visited = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        while(true) {
            if(bfs()) answer++;
            else break;
            visited = new boolean[N][N];
        }
        
        System.out.println(answer);
    }

    // 국경개방 조건에 맞는 경우 방문체크
    // 더이상 인접한 곳에 조건이 충족되는 노드가 없을 경우 인구 이동 
    // -> 연합 해체 (방문체크 안한 노드부터 재탐색)
    // 더이상 인구 이동이 불가능할 경우 방문체크 초기화 + 일수 추가
    static boolean bfs() {
        Point node;
        Point nextNode = new Point(0, 0);
        int A, total = 0;
        Queue<Point> bfs = new LinkedList<>();
        ArrayList<Point> plag = new ArrayList<>();
        boolean moved = false;

        for(int y = 0; y < N; y++){   
            for(int x = 0; x < N; x++){
                if(visited[y][x]) continue;

                total = 0;
                plag.clear();
                bfs.clear();
                bfs.add(new Point(x, y));

                while(!bfs.isEmpty()) {
                    node = bfs.poll();
                    // System.out.println(node.x + ", " + node.y);
                    
                    for(int i = 0; i < 4; i++) {
                        nextNode.x = node.x + px[i];
                        nextNode.y = node.y + py[i];
                        
                        // index 초과
                        if(nextNode.x < 0 || nextNode.y < 0 ||
                            nextNode.x >= N || nextNode.y >= N) continue;
                        // 방문체크
                        if(visited[nextNode.y][nextNode.x]) continue;
                        
                        // 인구이동 조건
                        A = map[node.y][node.x] - map[nextNode.y][nextNode.x];
                        if(A < 0) A *= -1;
                        // System.out.print("인접 노드 [ " + nextNode.x + ", " + nextNode.y + " ] ");
                        // System.out.print("A = " + A + " ");
                        if(L <= A && A <= R) {
                            if(!visited[node.y][node.x]) {
                                visited[node.y][node.x] = true;
                                plag.add(new Point(node.x, node.y));
                                total += map[node.y][node.x];
                            }
                            visited[nextNode.y][nextNode.x] = true;
                            bfs.add(new Point(nextNode.x, nextNode.y));
                            plag.add(new Point(nextNode.x, nextNode.y));
                            total += map[nextNode.y][nextNode.x];
                        }
                    }
                    // System.out.println();
                }

                if(!plag.isEmpty()) {
                    total /= plag.size();
                    moved = true;
                    for(int i = 0; i < plag.size(); i++) {
                        node = plag.get(i);
                        map[node.y][node.x] = total;
                    }
                    
                    // for(int i = 0; i < N; i++) {
                    //     System.out.println();
                    //     for(int j = 0; j < N; j++) {
                    //         System.out.print(map[i][j] + " ");
                    //     }
                    // }
                    // System.out.println("\n");
                }
                
            }
        }
        return moved;
    }
}
