import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DataStructure_2 {
   static class Heap {
        int size, capacity;
        int[] arr;
        
        int getLeftChild(int index) {
            if(index * 2 <= this.size) return this.arr[index * 2];
            return 0;
        }
        int getRightChild(int index) {
            if((index * 2) + 1 <= this.size) return this.arr[(index * 2) + 1];
            return 0;
        }
        int getParent(int index) {
            if(index / 2 > 0) return this.arr[index / 2];
            return 0;
        }

        void add(int item) {
            if(this.size < this.capacity) {
                this.arr[this.size+1] = item;
                this.size += 1;
            }
        }
        void swap(int index) {
            if(index / 2 > 0) {
                int parent = this.arr[index / 2];
                this.arr[index / 2] = this.arr[index];
                this.arr[index] = parent;
            }
        }
    }
    // 최대힙
    static class MaxHeap extends Heap {
        MaxHeap setCapacity(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.arr = new int[capacity + 1];
            return this;
        }

        void insert(int item) {
            // 트리 맨 뒤에 노드 추가
            this.add(item);
            int cur = this.size;
            // 자식 노드가 부모 노드 보다 크고 루트 노드가 아닐때 반복
            while(this.arr[cur] > this.getParent(cur) && cur > 1) {
                this.swap(cur);
                cur = cur / 2;
            }
        }
        int pop() {
            int pop;
            if(this.size == 1) {
                pop = this.arr[1];
                this.size -= 1;
                return pop;
            }
            else if(this.size > 1) {
                // 노드 삭제
                pop = this.arr[1];
                
                // 맨 뒤에 있는 노드와 교체
                this.arr[1] = this.arr[this.size];
                this.size -= 1;

                int cur = 1;
                // 자식노드 보다 작을 때 반복
                while(cur * 2 <= size) {
                    // 왼쪽 노드
                    int max = this.getLeftChild(cur), maxIndex = cur * 2;

                    // 왼쪽 노드가 오른쪽 노드 보다 작을 때
                    if(max < this.getRightChild(cur) && this.size >= (cur * 2) + 1) {
                        max = this.getRightChild(cur);
                        maxIndex = (cur * 2) + 1;
                    }

                    if(max < this.arr[cur]) return pop;

                    // 제일 큰 노드와 교체
                    swap(maxIndex);
                    cur = maxIndex;
                }
                return pop;
            }
            return 0;
        }
    }   

    public static void main(String[] args) throws Exception {
        int N, X;
        MaxHeap maxHeap;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        maxHeap = new MaxHeap().setCapacity(N+1);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++) {
            X = Integer.parseInt(br.readLine());
            if(X == 0) sb.append(maxHeap.pop()).append("\n");
            else {
                maxHeap.insert(X);
            }
        }
        System.out.println(sb.toString());
    }
}
