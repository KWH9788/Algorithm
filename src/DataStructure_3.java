import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class DataStructure_3 {
    public static void main(String[] args) throws Exception {
        int N, X;
        PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>(Collections.reverseOrder());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= N; i++) {
            X = Integer.parseInt(br.readLine());
            
            // 짝수 번째
            if(i % 2 == 0) maxQueue.add(X);
            // 홀수 번째
            else minQueue.add(X);

            if(!maxQueue.isEmpty()) {
                // 최소힙의 top이 최대힙의 top보다 커야 함
                if(maxQueue.peek() > minQueue.peek()) {
                    minQueue.add(maxQueue.poll());
                    maxQueue.add(minQueue.poll());
                }
            }

            // 짝수 번째 + 최대, 최소 힙의 크기가 같을 경우 둘 중 가장 작은 수 출력
            if(maxQueue.size() == minQueue.size() && i % 2 ==0) {
                if(minQueue.peek() < maxQueue.peek()) sb.append(minQueue.peek()).append("\n");
                else sb.append(maxQueue.peek()).append("\n");
            }
            // 가운데 출력
            else sb.append(minQueue.peek()).append("\n");
        }
        System.out.println(sb.toString());
    }
}
