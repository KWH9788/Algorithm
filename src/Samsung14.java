import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Samsung14 {
    static class Offset {
        int x, y;
        Offset(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int R, C, T;
    static int[][] map;
    static Offset air_up, air_down;

    public static void main(String[] args) throws Exception {
        // 입출력 정리
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        map = new int[R][C];

        Boolean airUp = false;
        for(int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                // 공기 청정기 위치 정보
                if(airUp == false && map[i][j] == -1) {
                    air_up = new Offset(j, i);
                    airUp = true;
                } else if(airUp == true && map[i][j] == -1) {
                    air_down = new Offset(j, i);
                }
            }
        }

        for(int i = 0; i < T; i++) {
            dustSimulation();
            airCleanerSimulation();
        }

        // 3. T초 후 남아있는 미세먼지의 양 출력
        int answer = 0;
        for(int i = 0; i < R; i++) {
            for(int j = 0; j < C; j++) {
                if(map[i][j] != -1) {
                    answer += map[i][j];
                }
            }
        }
        System.out.println(answer);
    }

    static int[] aroundX = {0, 1, 0, -1}, aroundY = {-1, 0, 1, 0};

    // 1. 미세먼지 확산 시뮬레이션
    // A[r][c] / 5 만큼 인접 4방향에 확산
    // A[r][c] -= A[r][c] / 5 * 확산된 방향의 개수
    // 확산 되기 전 미세먼지 정보가 필요
    static void dustSimulation() {
        // 확산 전 배열 복사
        int[][] dustInfo = duplicate(map);
        // 미세먼지 양
        int dust;
        // 확산된 방향의 개수
        int sprey;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                // 청정기 위치 제외
                if(dustInfo[i][j] != -1) {
                    // 먼지가 없는 위치 제외
                    if(dustInfo[i][j] != 0) {
                        dust = dustInfo[i][j] / 5;
                        sprey = 0;

                        for(int k = 0; k < 4; k++) {
                            // 인접한 방향에 청정기, 칸이 없는지 확인
                            if(i + aroundY[k] >= 0 && i + aroundY[k] < R && j + aroundX[k] >= 0 && j + aroundX[k] < C && map[i + aroundY[k]][j + aroundX[k]] != -1) {
                                map[i + aroundY[k]][j + aroundX[k]] += dust;
                                sprey++;
                            }
                        }
                        // 남은 미세먼지의 양 반영
                        map[i][j] -= dust * sprey;
                    }
                }
            }
        }
    }

    static int[] 
        air_up_x = {0, 1, 0, -1}, 
        air_up_y = {-1, 0, 1, 0},
        air_down_x = {0, 1, 0, -1}, 
        air_down_y = {1, 0, -1, 0};

    // 2. 공기 청정기 순환 시뮬레이션
    // 청정기 위쪽은 반시계방향으로 순환 => 반시계방향으로 모든 미세먼지 1칸씩 이동
    // 아래쪽은 시계방향으로 순환 => 시계방향으로 모두 1칸씩 이동
    // 청정기 안쪽으로 들어간 미세먼지는 정화
    static void airCleanerSimulation() {
        // 청정기 위쪽 순환
        Offset cur = new Offset(air_up.x, air_up.y);
        int idx = 0;
        while(true) {
            if(cur.x + air_up_x[idx] >= 0 && cur.x + air_up_x[idx] < C && cur.y + air_up_y[idx] >= 0 && cur.y + air_up_y[idx] <= air_up.y){
                if(map[cur.y][cur.x] == -1) {
                    // 미세먼지 정화
                    map[cur.y + air_up_y[idx]][cur.x + air_up_x[idx]] = 0;

                } else if(map[cur.y + air_up_y[idx]][cur.x + air_up_x[idx]] == -1) {
                    map[cur.y][cur.x] = 0;
                    break;

                } else {
                    map[cur.y][cur.x] = map[cur.y + air_up_y[idx]][cur.x + air_up_x[idx]];
                }
                cur.x += air_up_x[idx];
                cur.y += air_up_y[idx];

                // 벽을 만나면 방향 전환
            } else {
                idx++;
            }
        }
        
        cur = new Offset(air_down.x, air_down.y);
        idx = 0;
        while(true) {
            if(cur.x + air_down_x[idx] >= 0 && cur.x + air_down_x[idx] < C && cur.y + air_down_y[idx] >= air_down.y && cur.y + air_down_y[idx] < R){
                if(map[cur.y][cur.x] == -1) {
                    // 미세먼지 정화
                    map[cur.y + air_down_y[idx]][cur.x + air_down_x[idx]] = 0;

                } else if(map[cur.y + air_down_y[idx]][cur.x + air_down_x[idx]] == -1) {
                    map[cur.y][cur.x] = 0;
                    break;
                    
                } else {
                    map[cur.y][cur.x] = map[cur.y + air_down_y[idx]][cur.x + air_down_x[idx]];
                }
                cur.x += air_down_x[idx];
                cur.y += air_down_y[idx];

                // 범위를 벗어나면 방향 전환
            } else {
                idx++;
            }
        }
    }

    // 배열 복사
    static int[][] duplicate(int[][] arr) {
        int[][] temp = new int[arr.length][arr[0].length];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[0].length; j++) {
                temp[i][j] = arr[i][j];
            }
        }
        return temp;
    }
}
