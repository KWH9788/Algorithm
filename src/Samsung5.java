import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    톱니바퀴에 총 8개의 톱니
    톱니바퀴는 총 4개
    회전은 시계, 반 시계
    옆에 있는 톱니바퀴에 맞닿은 톱니가 서로 다른 극이면 그 바퀴는 반대방향으로 회전

    N극은 0 S는 1
    시계는 1 반시계는 -1
    포인터 변수로 12시 톱니 확인
    시계로 회전 -> cur - 1
    반시계로 회전 -> cur + 1
    포인터 기준으로 +2번째 -2번째 톱니 확인

    회전시키기 전에 인접하는 바퀴와 다른 극인지 체크후 회전
*/

public class Samsung5 {
    // 톱니 배열
    static int[][] gears = new int[4][8];
    // 12시 포인터
    static int[] cur = new int[4]; 
    // 회전 여부 중복회전 방지
    static boolean[] rotated = new boolean[4];
    public static void main(String[] args) throws Exception {
        // 명령목록
        int[][] command;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < 4; i++){
            cur[i] = 0;
            String[] arr = br.readLine().split("");
            for(int j = 0; j < 8; j++){
                gears[i][j] = Integer.parseInt(arr[j]);
            }
        }
        int k = Integer.parseInt(br.readLine());
        command = new int[k][2];
        for(int i = 0; i < k; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 2; j++){
                command[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < k; i++){
            int target = command[i][0]-1;
            int rotate = command[i][1];
            Rotate(target, rotate);
            rotated = new boolean[4];
        }

        int answer = 0;
        if(gears[0][cur[0]] == 1) answer += 1;
        if(gears[1][cur[1]] == 1) answer += 2;
        if(gears[2][cur[2]] == 1) answer += 4;
        if(gears[3][cur[3]] == 1) answer += 8;
        System.out.println(answer);
    }
    public static void Rotate(int target, int rotate){
        if(target - 1 >= 0 && rotated[target-1] == false) {
            // A = 회전시킬 바퀴 B = 인접한 완쪽 바퀴
            int left;
            if(cur[target]+6 > 7) left = cur[target]+6 - 8;
            else left = cur[target]+6;
            int Aleft = gears[target][left];

            int right;
            if(cur[target-1]+2 > 7) right = cur[target-1]+2 - 8;
            else right = cur[target-1]+2;
            int Bright = gears[target-1][right];

            rotated[target] = true;
            // 서로 다른 극일 경우 B 반대로 회전
            if(Aleft != Bright) Rotate(target-1, rotate*-1);
        }
        if(target + 1 <= 3 && rotated[target+1] == false) {
            // A = 회전시킬 바퀴 B = 인접한 오른쪽 바퀴
            int right;
            if(cur[target]+2 > 7) right = cur[target]+2 - 8;
            else right = cur[target]+2;
            int Aright = gears[target][right];

            int left;
            if(cur[target+1]+6 > 7) left = cur[target+1]+6 - 8;
            else left = cur[target+1]+6;
            int Bleft = gears[target+1][left];
            
            rotated[target] = true;
            // 서로 다른 극일 경우 B 반대로 회전
            if(Aright != Bleft) Rotate(target+1, rotate*-1);
        }

        if(rotate == 1){
            if(cur[target]-1 < 0) cur[target] = 7;
            else cur[target] -= 1;
            rotated[target] = true;
            System.out.println(target+"번 기어 시계회전");
        } else if(rotate == -1){
            if(cur[target]+1 > 7) cur[target] = 0;
            else cur[target] += 1;
            rotated[target] = true;
            System.out.println(target+"번 기어 반시계회전");
        }
    }
}
