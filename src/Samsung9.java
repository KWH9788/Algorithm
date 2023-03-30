import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    청소기가 바라보는 방향 = d 
    d = 0 : 북, 1 : 동, 2 : 남, 3 : 서
    배열 상태 = 0 : 빈칸, 1 : 벽

    1. 현재 칸이 청소되지 않은 경우 현재 칸 청소
    2. 주변 상하좌우에 청소되지 않은 빈 칸이 없는 경우
        -> 방향을 유지한 채로 한 칸 후진 후 1번 부터 다시
        -> 후진할 수 없다면 작동 중지
    3. 주변 상하좌우에 청소되지 않은 빈 칸이 있는 경우
        -> 반시계 방향으로 90도 회전
        -> 바라보는 방향의 앞 칸에 청소되지 않은 빈 칸이 있다면 한 칸 정지
        -> 1번 부터 다시
*/
public class Samsung9 {
    public static void main(String[] args) throws Exception {
        int N, M, d, rX, rY, answer = 0;
        int[][] room;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        room = new int[N][M];

        st = new StringTokenizer(br.readLine());
        rY = Integer.parseInt(st.nextToken());
        rX = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                room[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while(true) {
            // 현재 칸이 청소되지 않은 경우
            if(room[rY][rX] == 0) {
                room[rY][rX] = -1;
                answer += 1;
            }
            // 주변에 청소되지 않은 빈 칸이 있는 경우
            if(scan(room, rX, rY)) {
                // 회전
                d = rotate(d);

                if(d == 0) {
                    if(room[rY-1][rX] == 0) {
                        rY -= 1;
                    }
                }
                else if(d == 1) {
                    if(room[rY][rX+1] == 0) {
                        rX += 1;
                    }
                }
                else if(d == 2) {
                    if(room[rY+1][rX] == 0) {
                        rY += 1;
                    }
                }
                else if(d == 3) {
                    if(room[rY][rX-1] == 0) {
                        rX -= 1;
                    }
                }
            }
            // 주변에 청소되지 않은 빈 칸이 없는 경우
            else {
                if(d == 0) {
                    // 후진이 가능할 경우 
                    if(room[rY+1][rX] != 1) {
                        rY += 1;
                    }
                    else break;
                }
                else if(d == 1) {
                    // 후진이 가능할 경우 
                    if(room[rY][rX-1] != 1) {
                        rX -= 1;
                    }
                    else break;
                }
                else if(d == 2) {
                    // 후진이 가능할 경우 
                    if(room[rY-1][rX] != 1) {
                        rY -= 1;
                    }
                    else break;
                }
                else if(d == 3) {
                    // 후진이 가능할 경우 
                    if(room[rY][rX+1] != 1) {
                        rX += 1;
                    }
                    else break;
                }
            }
        }
        System.out.println(answer);
    }

    static int rotate(int d) {
        if(d == 0) return 3;
        else if(d == 1) return 0;
        else if(d == 2) return 1;
        else if(d == 3) return 2;
        return -1;
    }

    static boolean scan(int[][] room, int x, int y) {
        if(room[y][x+1] == 0 || room[y][x-1] == 0 || room[y+1][x] == 0 || room[y-1][x] == 0) return true;
        return false;
    }
}
