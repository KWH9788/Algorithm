import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Samsung15 {
    static class Shark{
        int r, c, speed, direction, size;
        Shark(int r, int c, int speed, int direction, int size) {
            this.r = r;
            this.c = c;
            this.speed = speed;
            this.direction = direction;
            this.size = size;
        }
    }

    static int R, C, M, answer;
    static Shark[][] map;

    public static void main(String[] args) throws Exception {
        // 입출력 정리
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new Shark[R][C];

        for(int i = 0; i < M; i++) {
            int r, c, s, d, z;
            st = new StringTokenizer(br.readLine());
            r = Integer.parseInt(st.nextToken()) - 1;
            c = Integer.parseInt(st.nextToken()) - 1;
            s = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            z = Integer.parseInt(st.nextToken());
            map[r][c] = new Shark(r, c, s, d, z);
        }

        for(int i = 0; i < C; i++) {
            fishing(i);
            shark();
        }
        System.out.println(answer);
    }
    // 낚시 구현
    // 낚시왕이 있는 열에서 땅과 제일 가까운 상어를 잡는다
    static void fishing(int c) {
        for(int i = 0; i < R; i++) {
            if(map[i][c] != null) {
                answer += map[i][c].size;
                map[i][c] = null;
                return;
            }
        }
    }

    // 상어 구현
    /* direction = {
        1 = 위
        2 = 아래
        3 = 오른쪽
        4 = 왼쪽
    } */
    // 상어가 이동을 마친 후에 한칸에 두 마리 이상이 있을 경우 큰 상어만 남아있는다
    static int[] dX = { 0, 0, 0, 1, -1};
    static int[] dY = {0, -1, 1, 0, 0};

    static void shark() {
        // 상어가 이동한 칸
        boolean[][] nestCheck = new boolean[R][C];
        
        Shark[][] temp = duplicate();
        for(int i = 0; i < R; i++) {
            for(int j = 0; j < C; j++) {
                if(temp[i][j] != null) {
                    // 움직인 상어 정보
                    Shark Info = move(i, j, temp[i][j].direction, temp[i][j].speed, temp[i][j].size);
                    // 이동한 칸에 다른 상어가 있다면
                    if(nestCheck[Info.r][Info.c]) {
                        if(map[Info.r][Info.c].size < Info.size) map[Info.r][Info.c] = new Shark(Info.r, Info.c, Info.speed, Info.direction, Info.size);
                        if(nestCheck[i][j] == false) map[i][j] = null;
                    } else {
                        map[Info.r][Info.c] = new Shark(Info.r, Info.c, Info.speed, Info.direction, Info.size);
                        nestCheck[Info.r][Info.c] = true;
                        if(nestCheck[i][j] == false) map[i][j] = null;
                    }
                }
            }
        }
    }

    static Shark move(int r, int c, int d, int s, int z) {
        if(d <= 2) {
            for(int i = 0; i < s; i++) {
                if(r + dY[d] < 0 || r + dY[d] >= R) { 
                    if(d % 2 == 0) d--;
                    else if(d % 2 != 0) d++;
                }
                r += dY[d];
            }
        }
        else if(d >= 3) {
            for(int i = 0; i < s; i++) {
                if(c + dX[d] < 0 || c + dX[d] >= C) { 
                    if(d % 2 == 0) d--;
                    else if(d % 2 != 0) d++;
                }
                c += dX[d];
            }
        }

        return new Shark(r, c, s, d, z);
    }

    static Shark[][] duplicate() {
        Shark[][] temp = new Shark[R][C];
        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                temp[r][c] = map[r][c];
            }
        }
        return temp;
    }
}
