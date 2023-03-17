import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    가지고 있는 숫자 카드중에서 문제에서 주어진 숫자가 몇개 있는지 찾는 문제
    카운팅 정렬로 푸는 방법
*/

public class Sort_1 {
    public static void main(String[] args) throws Exception {
        int N, M;
        
        int[] arr = new int[20000001];
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            int n = Integer.parseInt(st.nextToken());
            if(n >= 0) arr[n] += 1;
            else arr[20000001+n] += 1;
        }

        StringBuilder ans = new StringBuilder();

        M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            int m = Integer.parseInt(st.nextToken());
            if(m >= 0) {
                ans.append(arr[m]).append(" ");
            }
            else ans.append(arr[20000001+m]).append(" ");
        }
        System.out.println(ans);
    }
}
