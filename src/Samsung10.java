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
    static Boolean[] visited;
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
    }

    void BackTracking(int level) {
        // 유망성 검사
        // 최소값 보다 크면 가지치기

        for(int i = 0; i < chickenHouses.size(); i++) {
            visited[i] = true;
            BackTracking(level+1);
            visited[i] = false;
        }
    }

    void promising() {
        int lenght = 0, min;
        for(int i = 0; i < houses.size(); i++) {
            House house = houses.get(i);
            for(int j = 0; j < chickenHouses.size(); j++) {
                // 임시 치킨거리
                int l = 0;
                if(visited[j]) {
                    House chicken = chickenHouses.get(j);
                    if(house.row - chicken.row < 0) {
                        l += ((house.row - chicken.row) * -1);
                    }
                    if(house.column - house.column < 0) {
                        l += ((house.column - chicken.column) * -1);
                    }
                    if(lenght != 0 && l < lenght) lenght = l;
                }

            }
        }
    }
}
