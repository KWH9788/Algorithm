import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 
    이분탐색에서 lower/upper bound를 이용해서 중복개수를 구하는 문제
    lower bound: 찾는 값 "이상"의 값이 처음 나오는 인덱스 (하한선)
    upper bound: 찾는 값을 "초과"한 값이 처음 나오는 인덱스 (상한선)
    상한선 - 하한선 = 중복 개수
*/

public class BinarySearch_3 {
    public static void main(String[] args) throws Exception {
        int N, M, low, high;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int arr[] = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        M = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < M; i++) {
            int m = Integer.parseInt(st.nextToken());
            low = lowerBound(arr, m);
            high = upperBound(arr, m);
            ans.append(high - low).append(" ");
        }
        System.out.println(ans);

    }
    // 하한선
    static int lowerBound(int[] arr, int target) {
        int l = 0, h = arr.length-1, mid;
        while(l < h) {
            mid = l + ((h - l) / 2);
            // 찾는 값 보다 작으면 오른쪽으로 좁히기
            if(arr[mid] < target) l = mid+1;
            // 같거나 크면 왼쪽으로 좁히기
            else h = mid;
        }
        return l;
    }
    // 상한선
    static int upperBound(int arr[], int target) {
        int l = 0, h = arr.length-1, mid;
        while(l < h) {
            mid = l + ((h - l) / 2);
            // 찾는 값 보다 크면 왼쪽으로 좁히기
            if(arr[mid] > target) h = mid;
            // 같거나 작으면 오른쪽으로 좁히기
            else l = mid + 1;
        }
        // 마지막 인덱스인데 찾는 값이랑 같을 경우
        if(arr[h] == target) h++;
        return h;
    }
}
