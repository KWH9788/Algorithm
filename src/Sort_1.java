import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
    가지고 있는 숫자 카드중에서 문제에서 주어진 숫자가 몇개 있는지 찾는 문제
    해시맵을 이용해서 key: 숫자, value: 중복 개수로 저장해서 간단하게 푸는 방법
*/

public class Sort_1 {
    public static void main(String[] args) throws Exception {
        int N, M;
        
        HashMap<Integer, Integer> countingSort = new HashMap<>();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            int n = Integer.parseInt(st.nextToken());
            int count = 0;
            if(countingSort.get(n) == null)
                countingSort.put(n, count+1);
            else {
                count = countingSort.get(n);
                countingSort.put(n, count+1);
            }
        }

        StringBuilder ans = new StringBuilder();
        M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            int m = Integer.parseInt(st.nextToken());
            if(countingSort.get(m) == null) ans.append(0).append(" ");
            else ans.append(countingSort.get(m)).append(" ");
        }
        System.out.println(ans);
    }
}
