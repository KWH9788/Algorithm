import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Samsung2 {
    static int N;
    static int[] tester;
    static int supervisor;
    static int boss;
    static long answer = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        tester = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for(int i = 0; i < N; i++) {
            tester[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine(), " ");
        boss = Integer.parseInt(st.nextToken());
        supervisor = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++){
            int temp = tester[i] - boss;
            answer++;
            if(temp > 0) {
                if(temp % supervisor > 0) answer ++;
                answer += temp / supervisor;
            }
        }
        System.out.println(answer);
    }
}
