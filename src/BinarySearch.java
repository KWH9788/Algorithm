import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 
    N : 정수의 개수
    A[1~N] 정수
    M : 찾을 정수의 개수
    left, right : 탐색 범위
    mid : 기준

    풀이방법
    1. 배열을 정렬한다
    2. 가운데를 기준으로 찾을 정수의 크기가
        더 크면 left = mid + 1 작으면 right = mid - 1
        맞다면 return mid
    3. 2를 while(left <= right) 반복
    4. 3을 M번 반복
*/

public class BinarySearch {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) 
            arr[i] = Integer.parseInt(st.nextToken());

        int M = Integer.parseInt(br.readLine());
        int targets[] = new int[M];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            targets[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        for(int i = 0; i < M; i++)
            System.out.println(binarySearch(arr, targets[i]));
    }

    static int binarySearch(int[] arr, int m) {
        int left, right, mid;
        left = 0;
        right = arr.length-1;

        while(left <= right) {
            mid = (right + left) / 2;
            if(arr[mid] == m) return 1;
            else if(arr[mid] > m) right = mid - 1;
            else left = mid + 1;
        }
        return 0;
    }
}
