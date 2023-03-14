import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Samsung7 {
    // N: 세로선, M: 놓여져 있는 가로선, H: 가로선
    static int N, M, H, answer;
    static int[][] info;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        info = new int[H][N];

        answer = 4;
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            info[a-1][b-1] = 1;
        }

        backTracking(0, 0, info, 0);

        // 조건에 맞게 출력
        if(answer > 3) answer = -1;
        System.out.println(answer);
    }

    static void backTracking(int level, int bridge, int[][] info, int idx) {
        // 0이 제일 최솟값이기 때문에 더 이상 탐색할 필요 없음
        if(answer == 0) return;
        // 가로선 3개 까지 사용가능
        if(bridge > 3 || level >= H) return;
        // i번 세로선의 결과가 i번이 나오는지 체크
        if(promising(info)) {
            // 최솟값 구하기
            if(answer > bridge) answer = bridge;
        }
        for(int j = idx; j < N-1; j++){
            // 가로선이 연결되어 있다면
            if(info[level][j] == 1) continue;
            if(j - 1 >= 0) {
                if(info[level][j-1] == 1) continue;
            }
            if(info[level][j+1] == 1) continue;
            
            info[level][j] = 1;
            backTracking(level, bridge+1, info, j+1);
            info[level][j] = 0;
        }
        // 모든 가로선을 탐색하면 한칸 내려가서 탐색시작
        backTracking(level+1, bridge, info, 0);
    }

    static boolean promising(int[][] info){
        for(int i = 0; i < N; i++) {
            // x: 세로선
            int x = i;
            for(int j = 0; j < H; j++){
                // j: 가로선

                // 오른쪽으로 이동
                if(info[j][x] == 1) {
                    x++;
                    continue;
                }
                // 인덱스 초과 방지
                if(x-1 >= 0){
                    // 왼쪽으로 이동
                    if(info[j][x-1] == 1) {
                        x--;
                    }
                }
            }
            // 여러 줄 중에 한 줄이라도 조건에 부합하지 않는다면 false
            if(x != i) return false;
        }
        return true;
    }
}
