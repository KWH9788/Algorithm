import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Samsung1 {
    static int N;
    static int[][] stateTable;
    static boolean[] team;
    static int answer = -1;
    public static void BackTracking(int level, int player) {
        if(level == N/2) answer = Promising(answer);
        
        for(int i = player; i < N; i++){
            if(team[i] == false){
                team[i] = true;
                BackTracking(level+1, i);
                team[i] = false;
            }
        }
    }
    public static int Promising(int compare) {
        int start_temp = 0;
        int link_temp = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(team[i] == true && team[j] == true) start_temp += stateTable[i][j];
                else if(team[i] == false && team[j] == false) link_temp += stateTable[i][j];
            }
        }

        int temp = start_temp - link_temp;
        if(0 > temp) temp *= -1;
        if(answer == -1) return temp;
        if(compare > temp) return temp;
        return compare;
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        stateTable = new int[N][N];
        team = new boolean[N];

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for(int j = 0; j < N; j++){
                stateTable[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < N; i++){
            team[i] = true;
            BackTracking(1, i);
            team[i] = false;
        }
        System.out.println(answer);
    }
}
