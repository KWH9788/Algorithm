import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    N : 자를 수 있는 나무 개수
    M : 필요한 나무 길이
    H[0~N] : 나무 높이 배열

    1. 설정한 절단기 높이를 매개변수로한 
        자른 나무 길이를 구하는 함수 구현
    2. 절단기 높이는 이진 탐색으로 설정
*/

public class BinarySearch2 {
    public static void main(String[] args) throws Exception {
        int answer = 0, left = 0, mid, right = 0, N, M;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                if(arr[i] > right) right = arr[i];
            }
        
        while(left <= right) {
            // 중간값
            mid = (left+right) / 2;

            // 잘린 나무 길이가 조건에 충족될 때
            // 최소값 Up, 높이의 최대값 구하기
            if(cutter(arr, mid, M)) {
                left = mid + 1;
                if(answer < mid) answer = mid;
            }
            // 충족되지 않았을 때
            // 최대값 Down
            else right = mid - 1;
        }

        System.out.println(answer);
    }

    static boolean cutter(int[] arr, int height, int M) {
        // 자른 나무를 다 더했을 때 int 범위를 초과할 수 있으니까 long 타입 사용
        long totalLength = 0;
        for(int i = 0; i < arr.length; i++) {
            // 절단기 높이가 나무보다 높을 경우 스킵
            if(height >= arr[i]) continue;
            else {
                totalLength += arr[i] - height;
            }
            // 필요한 만큼의 나무를 잘랐다면 바로 중단
            if(totalLength >= M) return true;
        }
        return false;
    }
}
