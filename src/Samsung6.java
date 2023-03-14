import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Samsung6 {
    // n:세로 m:가로
    static int n, m;
    // CCTV 좌표, 개수, 방향
    static int[][] cctv = new int[8][2];
    static int cctv_count = 0;
    static int[] arrows = new int[8];
    // 사각지대 개수
    static int answer = 64;
    public static void main(String[] args) throws Exception {
        // 지도
        int[][] map;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] < 6 && map[i][j] > 0) {
                    cctv[cctv_count][0] = i;
                    cctv[cctv_count][1] = j;
                    cctv_count++;
                }
            }
        }

        backTracking(map, 1);
        System.out.println(answer);
    }

    static void backTracking(int[][] map, int level){
        // 모든 CCTV 방향을 설정했을 경우
        if(level == cctv_count+1){
            // 감시영역 구현
            int[][] temp_map = duplicateMap(map, n, m);
            for(int i = 0; i < cctv_count; i++){
                int x = cctv[i][1];
                int y = cctv[i][0];
                int type = temp_map[y][x];
                temp_map = cctvCheck(temp_map, arrows[i], type, y, x);
            }
            // 사각지대 탐색
            int temp = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(temp_map[i][j] == 0) {
                        temp++;
                    }
                }
            }
            // 최소값 구하기
            if(answer > temp) answer = temp;
            return;
        }

        //DFS
        for(int i = 1; i < 5; i++){
            arrows[level-1] = i;
            backTracking(map, level+1);
        }
    }

    static int[][] cctvCheck(int[][] map, int arrow, int type, int y, int x){
        // 맵 범위를 초과하거나, 벽을 만났을 경우 반복문 중단
        // 경로에 cctv가 있어도 진행

        // 벽을 만났을 때 false로 변경
        boolean up = true, down = true, left = true, right = true;

        // 1번 CCTV
        if(type == 1){
            // 방향: 위
            if(arrow == 1){
                while(y+1 < n){
                    if(map[y+1][x] == 0){
                        map[y+1][x] = -1;
                    } else if(map[y+1][x] == 6) {
                        return map;
                    }
                    y++;
                }
                return map;
            }
            // 방향: 오른쪽
            else if(arrow == 2){
                while(x+1 < m){
                    if(map[y][x+1] == 0){
                        map[y][x+1] = -1;
                    } else if(map[y][x+1] == 6) {
                        return map;
                    }
                    x++;
                }
                return map;
            }
            // 방향: 아래
            else if(arrow == 3){
                while(y-1 >= 0){
                    if(map[y-1][x] == 0){
                        map[y-1][x] = -1;
                    } else if(map[y-1][x] == 6) {
                        return map;
                    }
                    y--;
                }
                return map;
            }
            // 방향: 왼쪽
            else if(arrow == 4){
                while(x-1 >= 0){
                    if(map[y][x-1] == 0){
                        map[y][x-1] = -1;
                    } else if(map[y][x-1] == 6) {
                        return map;
                    }
                    x--;
                }
                return map;
            }
        }
        
        // 2번 CCTV
        else if(type == 2){
            int move = 1;
            // 방향: 위, 아래
            if(arrow == 1 || arrow == 3){
                while(y+move < n || y-move >= 0){
                    if(y+move < n && up){
                        if(map[y+move][x] == 0){
                        map[y+move][x] = -1;
                        }
                        else if(map[y+move][x] == 6) up = false;
                    }
                    if(y-move >= 0 && down){
                        if(map[y-move][x] == 0){
                            map[y-move][x] = -1;
                        }
                        else if(map[y-move][x] == 6) down = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 오른쪽, 왼쪽
            else if(arrow == 2 || arrow == 4){
                while(x+move < m || x-move >= 0){
                    if(x+move < m && right){
                        if(map[y][x+move] == 0){
                            map[y][x+move] = -1;
                        }
                        else if(map[y][x+move] == 6) right = false;
                    }
                    if(x-move >= 0 && left){
                        if(map[y][x-move] == 0){
                            map[y][x-move] = -1;
                        }
                        else if(map[y][x-move] == 6) left = false;
                    }
                    move++;
                }
                return map;
            }
        }

        // 3번 CCTV
        else if(type == 3){
            int move = 1;
            // 방향: 위, 오른쪽
            if(arrow == 1){
                while(y+move < n || x+move < m){
                    if(y+move < n && up){
                        if(map[y+move][x] == 0){
                            map[y+move][x] = -1;
                        }
                        else if(map[y+move][x] == 6) up = false;
                    }
                    if(x+move < m && right){
                        if(map[y][x+move] == 0){
                            map[y][x+move] = -1;
                        }
                        else if(map[y][x+move] == 6) right = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 오른쪽, 아래
            else if(arrow == 2){
                while(x+move < m || y-move >= 0){
                    if(x+move < m && right){
                        if(map[y][x+move] == 0){
                            map[y][x+move] = -1;
                        }
                        else if(map[y][x+move] == 6) right = false;
                    }
                    if(y-move >= 0 && down){
                        if(map[y-move][x] == 0){
                            map[y-move][x] = -1;
                        }
                        else if(map[y-move][x] == 6) down = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 아래, 왼쪽
            else if(arrow == 3){
                while(y-move >= 0 || x-move >= 0){
                    if(x-move >= 0 && left){
                        if(map[y][x-move] == 0){
                            map[y][x-move] = -1;
                        }
                        else if(map[y][x-move] == 6) left = false;
                    }
                    if(y-move >= 0 && down){
                        if(map[y-move][x] == 0){
                            map[y-move][x] = -1;
                        }
                        else if(map[y-move][x] == 6) down = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 왼쪽, 위
            else if(arrow == 4){
                while(y+move < n || x-move >= 0){
                    if(x-move >= 0 && left){
                        if(map[y][x-move] == 0){
                            map[y][x-move] = -1;
                        }
                        else if(map[y][x-move] == 6) left = false;
                    }
                    if(y+move < n && up){
                        if(map[y+move][x] == 0){
                            map[y+move][x] = -1;
                        }
                        else if(map[y+move][x] == 6) up = false;
                    }
                    move++;
                }
                return map;
            }
        }

        // 4번 CCTV
        else if(type == 4){
            int move = 1;
            // 방향: 왼쪽, 위, 오른쪽
            if(arrow == 1){
                while(y+move < n || x+move < m || x-move >= 0){
                    if(y+move < n && up){
                        if(map[y+move][x] == 0){
                            map[y+move][x] = -1;
                        }
                        else if(map[y+move][x] == 6) up = false;
                    }
                    if(x+move < m && right){
                        if(map[y][x+move] == 0){
                            map[y][x+move] = -1;
                        }
                        else if(map[y][x+move] == 6) right = false;
                    }
                    if(x-move >= 0 && left){
                        if(map[y][x-move] == 0){
                            map[y][x-move] = -1;
                        }
                        else if(map[y][x-move] == 6) left = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 위, 오른쪽, 아래
            else if(arrow == 2){
                while(x+move < m || y-move >= 0 || y+move < n){
                    if(x+move < m && right){
                        if(map[y][x+move] == 0){
                            map[y][x+move] = -1;
                        }
                        else if(map[y][x+move] == 6) right = false;
                    }
                    if(y-move >= 0 && down){
                        if(map[y-move][x] == 0){
                            map[y-move][x] = -1;
                        }
                        else if(map[y-move][x] == 6) down = false;
                    }
                    if(y+move < n && up){
                        if(map[y+move][x] == 0){
                            map[y+move][x] = -1;
                        }
                        else if(map[y+move][x] == 6) up = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 왼쪽, 아래, 오른쪽
            else if(arrow == 3){
                while(y-move >= 0 || x-move >= 0 || x+move < m){
                    if(x-move >= 0 && left){
                        if(map[y][x-move] == 0){
                            map[y][x-move] = -1;
                        }
                        else if(map[y][x-move] == 6) left = false;
                    }
                    if(y-move >= 0 && down){
                        if(map[y-move][x] == 0){
                            map[y-move][x] = -1;
                        }
                        else if(map[y-move][x] == 6) down = false;
                    }
                    if(x+move < m && right){
                        if(map[y][x+move] == 0){
                            map[y][x+move] = -1;
                        }
                        else if(map[y][x+move] == 6) right = false;
                    }
                    move++;
                }
                return map;
            }
            // 방향: 위, 왼쪽, 아래
            else if(arrow == 4){
                while(y+move < n || x-move >= 0 || y-move >= 0){
                    if(x-move >= 0 && left){
                        if(map[y][x-move] == 0){
                            map[y][x-move] = -1;
                        }
                        else if(map[y][x-move] == 6) left = false;
                    }
                    if(y+move < n && up){
                        if(map[y+move][x] == 0){
                            map[y+move][x] = -1;
                        }
                        else if(map[y+move][x] == 6) up = false;
                    }
                    if(y-move >= 0 && down){
                        if(map[y-move][x] == 0){
                            map[y-move][x] = -1;
                        }
                        else if(map[y-move][x] == 6) down = false;
                    }
                    move++;
                }
                return map;
            }
        }
        else if(type == 5){
            int move = 1;
            // 방향: 네방향 전체
            while(y+move < n || x+move < m || x-move >= 0 || y-move >= 0){
                if(y+move < n && up){
                    if(map[y+move][x] == 0){
                        map[y+move][x] = -1;
                    }
                    else if(map[y+move][x] == 6) up = false;
                }
                if(y-move >= 0 && down){
                    if(map[y-move][x] == 0){
                        map[y-move][x] = -1;
                    }
                    else if(map[y-move][x] == 6) down = false;
                }
                if(x+move < m && right){
                    if(map[y][x+move] == 0){
                        map[y][x+move] = -1;
                    }
                    else if(map[y][x+move] == 6) right = false;

                }
                if(x-move >= 0 && left){
                    if(map[y][x-move] == 0){
                        map[y][x-move] = -1;
                    }
                    else if(map[y][x-move] == 6) left = false;
                }
                move++;
            }
            return map;
        }
        return map;
    }

    // 지도 복사
    static int[][] duplicateMap(int[][] map, int y, int x){
        int[][] duplicate = new int[y][x];
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                duplicate[i][j] = map[i][j];
            }
        }
        return duplicate;
    }
}
