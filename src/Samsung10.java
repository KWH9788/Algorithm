import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class House {
    int column, row;
    House(int row, int column) {
        this.column = column;
        this.row = row;
    }
}

public class Samsung10 {
    static int N, M, answer = 99999;
    static int[][] map;
    static boolean[] visited;
    // 치킨집 좌표 배열
    static ArrayList<House> chickenHouses = new ArrayList<>();
    // 집 좌표 배열
    static ArrayList<House> houses = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        int spot;
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                spot = Integer.parseInt(st.nextToken());
                if(spot == 2) {
                    chickenHouses.add(new House(i+1, j+1));
                } else if(spot == 1) {
                    houses.add(new House(i+1, j+1));
                }
                map[i][j] = spot;
            }
        }

        visited = new boolean[chickenHouses.size()];

        // 폐업시키지 않을 치킨집 최대 M개와 치킨집의 개수가 같을 때
        if(chickenHouses.size() == M) {
            for(int i = 0; i < M; i++) {
                visited[i] = true;
                promising();
            }
        }
        else BackTracking(1, 0);
        System.out.println(answer);
    }

    static void BackTracking(int level, int index) {
        if(level > M+1) return;
        else if(level == M+1) {
            // System.out.print("level : " + level + ", ");
            promising();
        }

        for(int i = index; i < chickenHouses.size(); i++) {
            if(!visited[i]){
                visited[i] = true;
                BackTracking(level+1, i);
                visited[i] = false;
            }
        }
    }

    static void promising() {
        int lenght, min = 0, rL, cL;

        for(int i = 0; i < houses.size(); i++) {
            // 최단 거리
            lenght = 0;
            House house = houses.get(i);
            for(int j = 0; j < chickenHouses.size(); j++) {
                // 폐업시키지 않을 치킨집
                if(visited[j]) {
                    House chicken = chickenHouses.get(j);
                    // row 거리
                    if(house.row - chicken.row < 0) {
                        rL = ((house.row - chicken.row) * -1);
                    } else {
                        rL = house.row - chicken.row;
                    }
                    // col 거리
                    if(house.column - chicken.column < 0) {
                        cL = ((house.column - chicken.column) * -1);
                    } else {
                        cL = house.column - chicken.column;
                    }

                    // 최단 거리
                    if(lenght == 0) lenght = rL + cL;
                    else if((rL + cL) < lenght) lenght = rL + cL;
                }
            }   // 치킨집 탐색

            min += lenght;
        }   // 집 순회

        if(answer > min) {
            answer = min;
            // System.out.print("min : " + answer + "\n");
            return;
        }
        // System.out.print("value : " + min + "\n");
    }
}
