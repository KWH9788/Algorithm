import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Samsung3 {
    static class ListNode {
        private String data;
        public ListNode front;
        public ListNode back;

        public ListNode() {
            this.data = null;
            this.front = null;
            this.back = null;
        }

        public ListNode(String data) {
            this.data = data;
            this.front = null;
            this.back = null;
        }

        public ListNode(String data, String type, ListNode link){
            this.data = data;
            this.back = link;
            this.front = null;
        }

        public String getData(){
            return this.data;
        }
    }
    static class LinkedList {
        private ListNode head;
        private ListNode rear;
        public int size;

        public LinkedList() {
            head = null;
            rear = null;
            size = 0;
        }

        public void add(String data) {
            ListNode temp = new ListNode(data);
            if(size == 0) {
                this.head = temp;
                this.rear = temp;
                this.size++;
            } else {
                this.rear.back = temp;
                temp.front = rear;
                this.rear = temp;
                size++;
            }
        }
        public String pop() {
            if(size > 0){
                ListNode temp = this.head;
                this.head = temp.back;
                size--;
                if(this.size == 0) this.rear = null;
                return temp.getData();
            }
            return null;
        }
        public void clear() {
            this.head = null;
            this.rear = null;
        }
    }
    static int N, M, board[][];
    static boolean bfs_visited[][];
    static int safe, count_safe;
    static String virus_map[];
    static LinkedList queue = new LinkedList();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        
        int virus = 0;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                int lab = Integer.parseInt(st.nextToken());
                if(lab == 2) {
                    queue.add(Integer.toString(i)+", "+Integer.toString(j));
                    virus++;
                }
                board[i][j] = lab;
            }
        }
        virus_map = new String[virus];
        for(int i = 0; i < virus; i++) {
            virus_map[i] = queue.pop();
        }
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(board[i][j] == 0){
                    board[i][j] = 1;
                    DFS(1, i, j);
                    board[i][j] = 0;
                }
            }
        }
        System.out.println(safe);
    }
    public static void DFS(int Level, int nodey, int nodex){
        // 벽 3개 세우고 바이러스 탐색
        if(Level == 3) {
            BFS();
            // 안전영역 최대값 구하기
            if(safe < count_safe) safe = count_safe;
            return;
        }
        for(int i = nodey; i < N; i++){
            // 중복방지
            if(i == nodey && nodey+1 < M){
                for(int j = nodex+1; j < M; j++){
                    if(board[i][j] == 0) {
                        board[i][j] = 1;
                        DFS(Level+1, i, j);
                        board[i][j] = 0;
                    }
                }
                continue;
            }
            for(int j = 0; j < M; j++){
                if(board[i][j] == 0) {
                    board[i][j] = 1;
                    DFS(Level+1, i, j);
                    board[i][j] = 0;
                }
            }
        }
    }
    public static void BFS(){
        // 방문체크, 연구소 지도 복사
        bfs_visited = new boolean[N][M];
        int[][] test_board = new int[N][M];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                test_board[i][j] = board[i][j];
            }
        }
        // 바이러스 위치 추가
        for(int i = 0; i < virus_map.length; i++){
            queue.add(virus_map[i]);
        }
        StringTokenizer st = new StringTokenizer(queue.pop(), ", ");
        int y = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        bfs_visited[y][x] = true;

        while(true){
            // 경로 추가
            if(x+1 < M && test_board[y][x+1] == 0 && bfs_visited[y][x+1] == false) {
                bfs_visited[y][x+1] = true;
                test_board[y][x+1] = 2;
                queue.add(Integer.toString(y)+", "+Integer.toString(x+1));
            }
            if(y+1 < N && test_board[y+1][x] == 0 && bfs_visited[y+1][x] == false) {
                bfs_visited[y+1][x] = true;
                test_board[y+1][x] = 2;
                queue.add(Integer.toString(y+1)+", "+Integer.toString(x));
            }
            if(x-1 >= 0 && test_board[y][x-1] == 0 && bfs_visited[y][x-1] == false) {
                bfs_visited[y][x-1] = true;
                test_board[y][x-1] = 2;
                queue.add(Integer.toString(y)+", "+Integer.toString(x-1));
            }
            if(y-1 >= 0 && test_board[y-1][x] == 0 && bfs_visited[y-1][x] == false) {
                bfs_visited[y-1][x] = true;
                test_board[y-1][x] = 2;
                queue.add(Integer.toString(y-1)+", "+Integer.toString(x));
            }

            // 탐색할 노드가 더이상 없음
            if(queue.size == 0) break;

            // 탐색할 노드
            st = new StringTokenizer(queue.pop(), ", ");
            y = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            if(bfs_visited[y][x] == false) {
                bfs_visited[y][x] = true;
            }
        }
        // 안전영역 개수
        count_safe = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(test_board[i][j] == 0) count_safe++;
            }
        }
    }
}
