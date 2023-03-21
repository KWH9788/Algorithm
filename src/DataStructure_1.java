import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DataStructure_1 {
    public static class Heap {
        // 트리 배열
        int[] item;
        int size, capacity;
        
        // 트리 사이즈
        Heap setCapacity(int capacity){
            this.capacity = capacity;
            item = new int[capacity+1];
            size = 0;
            return this;
        }

        // 왼쪽 자식노드 
        int getLeftChild(int index) {
            if(index * 2 <= this.size) return this.item[index * 2];
            return -1;
        }
        // 오른쪽 자식노드
        int getRightChild(int index) {
            if((index * 2) + 1 <= size) return this.item[(index * 2) + 1];
            return -1;
        }
        // 부모 노드
        int getParent(int index) {
            if(index / 2 > 0) return this.item[index / 2];
            return -1;
        }

        // 노드 추가
        void add(int item) {
            if(size+1 <= capacity) {
                this.item[size+1] = item;
                this.size += 1;
            }
        }
        // 부모 노드와 교체
        void swap(int index) {
            if(index / 2 > 0) {
                int parent = getParent(index);
                this.item[index / 2] = this.item[index];
                this.item[index] = parent;
            }
        }
    }
    public static class MinHeap extends Heap {
        MinHeap setCapacity(int capacity){
            this.capacity = capacity;
            item = new int[capacity+1];
            size = 0;
            return this;
        }

        // 노드 추가
        void insert(int item) {
            // 노드를 맨 뒤에 추가
            this.add(item);
            int cur = size;

            // 현재 노드가 부모노드보다 작고 트리 노드가 아닐 때
            while(cur > 1 && this.getParent(cur) > this.item[cur]) {
                // 부모노드와 교체
                this.swap(cur);
                // 커서 위로 올리기
                cur = cur / 2;
            }
        }
        int pop() {
            int pop;
            // 자식 노드가 없을 경우
            if(size == 1) {
                pop = this.item[1];
                this.size -= 1;
                return pop;
            }
            // 자식 노드가 있을 경우
            else if(size > 1) {
                pop = this.item[1];
                // 맨 뒤에 있는 노드를 맨 앞으로 옮기기
                this.item[1] = this.item[size];
                this.size -= 1;

                int cur = 1;
                while(cur * 2 <= size) {
                    int min = this.getLeftChild(cur);
                    int minIndex = cur * 2;
                    // 왼쪽 자식 보다 오른쪽 자식이 작을 때
                    if((cur * 2) + 1 <= size && min > this.getRightChild(cur)) {
                        min = this.getRightChild(cur);
                        minIndex = (cur * 2) + 1;
                    }
                    // 자식 노드가 부모 노드보다 클때 반복문 중지
                    if(this.item[cur] < min) break;

                    // 자식 노드와 교체
                    swap(minIndex);
                    cur = minIndex;
                }
                return pop;
            }
            return 0;
        }
    }
    public static void main(String[] args) throws Exception{
        // 연산 개수, 입력값
        int N, X;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        // 최소힙 선언
        MinHeap minHeap = new MinHeap().setCapacity(N+1);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < N; i++){
            X = Integer.parseInt(br.readLine());
            // 출력
            if(X == 0) {
                sb.append(minHeap.pop() + "\n");
            // 입력
            } else {
                minHeap.insert(X);
            }
        }
        System.out.println(sb.toString());
    }
}
