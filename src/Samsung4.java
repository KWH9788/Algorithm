import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.text.AsyncBoxView;

/*
    answer = 지나갈 수 있는 길 개수
    N = 행, 열 개수
    L = 경사로 길이

    규칙
    1. 경사로는 낮은 높이의 바닥에 놓음
    2. 경사로는 높이가 1칸 차이일 경우에만 놓을 수 있음
    3. 경사로 길이(L) 만큼 같은 높이의 칸이 연속되어야 함
    4. 경사로를 놓은 곳에 또 놓을 수 없음
    5. 경사로의 길이가 지도 범위를 벗어날 경우 놓을 수 없음
*/

public class Samsung4 {
    static int N;
    static int L;
    static int[][] map;
    static int answer;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        // 경사로 설치 여부
        boolean[] bridge = new boolean[N];

        // 지나갈 수 있는 길의 개수
        answer = 0;

        for(int i = 0; i < N; i++) {
            solution(i);
        }
        System.out.println();
        System.out.println(answer);
    }

    static void solution(int line) {
        // 행
        int row[] = map[line];
        // 경사로 설치 여부
        boolean[] bridge = new boolean[N];
        // 연속된 같은 높이의 칸 개수 L 보다 작으면 설치 불가
        int sameLevels = 0;
        // 설치 진행중
        boolean bridgeinstalling = false;

        // 행 유망성 검사
        System.out.println();
        System.out.println(1+line+"번 행");
        rowPromissing:
        for(int i = 0; i < N; i++){
            sameLevels++;

            // 내려가는 경사로 설치
            if(bridgeinstalling) {
                // System.out.println(sameLevels+"칸 설치");
                bridge[i] = true;
                // 경사로 설치 완료
                if(sameLevels == L) bridgeinstalling = false;
            }

            // 마지막 칸
            if(i == N-1) {
                // 경사로 설치 중일 경우 범위 초과
                if(bridgeinstalling == true) {
                    System.out.println("경사로 길이 문제로 지나갈 수 없다(범위 초과)");
                    break rowPromissing;
                }
                answer++;
                System.out.println("지나갈 수 있다");
                break rowPromissing;
            }

            // 높이 차이가 2 이상이면 경사로 설치 불가
            if(row[i+1] - row[i] > 1 || row[i+1] - row[i] < -1) {
                System.out.println("높이 차이 문제로 지나갈 수 없다");
                break rowPromissing;
            }

            // 올라가는 경사로
            if(row[i+1] - row[i] == 1) {
                // 현재 칸에 경사로가 있는 경우 설치 불가
                if(bridge[i]) {
                    System.out.println("경사로 겹침으로 지나갈 수 없다");
                    break rowPromissing;
                }
                // 경사로 길이가 연속되는 칸보다 길어서 설치 불가
                else if(sameLevels < L) {
                    System.out.println("경사로 길이 문제로 지나갈 수 없다");
                    break rowPromissing;
                }
                else {
                    // 경사로 설치
                    for(int j = 0; j < L; j++) {
                        // 이미 칸에 경사로가 있을 경우
                        if(bridge[i-j] == true) {
                            System.out.println("경사로 문제(겹침)");
                            break rowPromissing;
                        }
                        bridge[i-j] = true;
                    }
                    sameLevels = 0;
                    continue;
                }

            // 내려가는 경사로
            } else if(row[i+1] - row[i] == -1) {
                if(bridgeinstalling) {
                    System.out.println("경사로 길이 문제로 지나갈 수 없다");
                    break rowPromissing;
                }
                // System.out.println("내리막길 설치 시작(남은 칸 = "+L+")");
                bridgeinstalling = true;
                sameLevels = 0;
                continue;
            }
        }
        
        // 열
        int[] col = new int[N];
        for(int i = 0; i < N; i++){
            col[i] = map[i][line];
        }
        // 경사로 설치 여부
        bridge = new boolean[N];
        // 연속된 같은 높이의 칸 개수 L 보다 작으면 설치 불가
        sameLevels = 0;
        bridgeinstalling = false;

        System.out.println(1+line+"번 열");
        colPromissing:
        for(int i = 0; i < N; i++){
            sameLevels++;

            // 내려가는 경사로 설치
            if(bridgeinstalling) {
                // System.out.println(sameLevels+"칸 설치");
                bridge[i] = true;
                // 경사로 설치 완료
                if(sameLevels == L) bridgeinstalling = false;
            }

            // 마지막 칸
            if(i == N-1) {
                // 경사로 설치 중일 경우 범위 초과
                if(bridgeinstalling == true) {
                    System.out.println("경사로 길이 문제로 지나갈 수 없다(범위 초과)");
                    break colPromissing;
                }
                answer++;
                System.out.println("지나갈 수 있다");
                break colPromissing;
            }

            // 높이 차이가 2 이상이면 경사로 설치 불가
            if(col[i+1] - col[i] > 1 || col[i+1] - col[i] < -1) {
                System.out.println("높이 차이 문제로 지나갈 수 없다");
                break colPromissing;
            }

            // 올라가는 경사로
            if(col[i+1] - col[i] == 1) {
                // 현재 칸에 경사로가 있는 경우 설치 불가
                if(bridge[i]) {
                    System.out.println("경사로 겹침으로 지나갈 수 없다");
                    break colPromissing;
                }
                // 경사로 길이가 연속되는 칸보다 길어서 설치 불가
                else if(sameLevels < L) {
                    System.out.println("경사로 길이 문제로 지나갈 수 없다");
                    break colPromissing;
                }
                else {
                    // 경사로 설치
                    for(int j = 0; j < L; j++) {
                        // 이미 칸에 경사로가 있을 경우
                        if(bridge[i-j] == true) {
                            System.out.println("경사로 문제(겹침)");
                            break colPromissing;
                        }
                        bridge[i-j] = true;
                    }
                    sameLevels = 0;
                    continue;
                }

            // 내려가는 경사로
            } else if(col[i+1] - col[i] == -1) {
                if(bridgeinstalling) {
                    System.out.println("경사로 길이 문제로 지나갈 수 없다");
                    break colPromissing;
                }
                // System.out.println("내리막길 설치 시작(남은 칸 = "+L+")");
                bridgeinstalling = true;
                sameLevels = 0;
                continue;
            }
        }
    }
}
