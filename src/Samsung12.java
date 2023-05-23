import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
    배열 : map(양분), A(추가될 양분의 양)
    리스트 : tree(위치 정보, 나이, 생사여부)
    K : 목표 년수

    봄 : 나이 만큼 양분 감소 
        and 어린 나무부터 양분 흡수 
        and 양분을 못 먹은 나무는 죽는다
    여름 : 죽은 나무가 있는 위치에 나이 / 2 만큼 양분 추가
    가을 : 나이가 5의 배수인 나무와 인접한 칸에 번식 (나이가 1인 나무) 
        and 주어진 땅을 벗어나는 칸에는 번식X
    겨울 : 리스트 A에 입력된 양만큼 땅에 추가
*/

public class Samsung12 {
    static class Tree {
        int x, y, age;
        Tree(int x, int y, int age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }
    }
    static Deque<Tree> trees = new LinkedList<>();
    static Queue<Tree> deadTrees = new LinkedList<>();
    static int[][] A, map;
    static int N, M, K;
    static int plusX[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
    static int plusY[] = { 1, 1, 0, -1, -1, -1, 0, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        A = new int[N][N];
        map = new int[N][N];
        
        for(int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for(int c = 0; c < N; c++) {
                A[r][c] = Integer.parseInt(st.nextToken());
                map[r][c] = 5;
            }
        }
        
        // 나무 정보
        for(int i = 0; i < M; i++) {
            int x, y, age;
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            age = Integer.parseInt(st.nextToken());
            
            trees.add(new Tree(y-1, x-1, age));
        }

        // Tree tree;
        // int n = trees.size();
        // for(int i = 0 ; i < n; i++) {
        //     tree = trees.poll();
        //     System.out.println("index" + i + " (" + tree.x + ", " + tree.y + "), " + "age : " + tree.age);
        //     trees.add(tree);
        // }

        for(int k = 0; k < K; k++) {
            spring();
            summer();
            fall();
            winter();
            
            int n = trees.size();
            if(n == 0) break;
            // for(int i = 0 ; i < n; i++) {
            //     tree = trees.poll();
            //     System.out.println("index" + i + " (" + tree.x + ", " + tree.y + "), " + "age : " + tree.age);
            //     trees.add(tree);
            // }
        }
        System.out.println(trees.size());
    }

    static void spring() {
        Tree tree;
        // 죽은 나무 리스트 초기화
        deadTrees = new LinkedList<>();
        // 양분 사용
        int n = trees.size();
        for(int i = 0; i < n; i++) {
            tree = trees.pollLast();
            // 양분이 충분할 경우
            if(map[tree.y][tree.x]  >= tree.age) {
                map[tree.y][tree.x] -= tree.age;
                tree.age ++;
                trees.addFirst(new Tree(tree.x, tree.y, tree.age));
            } else {
                deadTrees.add(new Tree(tree.x, tree.y, tree.age));
            }
        }
    }

    static void summer() {
        Tree tree;
        // 죽은 나무 위치에 양분 추가
        if(deadTrees.isEmpty()) return;
        int n = deadTrees.size();
        for(int i = 0; i < n; i++) {
            tree = deadTrees.poll();
            map[tree.y][tree.x] += (tree.age / 2);
        }
    }

    static void fall() {
        Tree tree;
        Queue<Tree> temp = new LinkedList<>();
        // 5배수 나무 인접 위치에 번식
        int n = trees.size();
        for(int i = 0; i < n; i++) {
            tree = trees.poll();
            // 5의 배수인지 체크
            if(tree.age % 5 == 0) {
                for(int j = 0; j < 8; j++) {
                    // 인덱스 범위 초과 방지
                    if(tree.x + plusX[j] >= 0 && tree.x + plusX[j] < N && tree.y + plusY[j] >= 0 && tree.y + plusY[j] < N) {
                        temp.add(new Tree(tree.x + plusX[j], tree.y + plusY[j], 1));
                    }
                }
            }
            trees.add(new Tree(tree.x, tree.y, tree.age));
        }
        trees.addAll(temp);
    }

    static void winter() {
        // 양분 추가
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] += A[i][j];
            }
        }
    }
}
