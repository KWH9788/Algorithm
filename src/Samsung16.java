import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Samsung16 {

    static class Pair implements Comparable<Pair> {
        int first, second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public void printPair() {
            System.out.println(first + ", " + second);
        }
    
        @Override
        public int compareTo(Pair o) {
            if (second == o.second) return Integer.compare(first, o.first);
            return Integer.compare(second, o.second);
        }
    
    }

    static int r, c, k, maxR = 3, maxC = 3;
    static int[][] A = new int[100][100];
    static int[][] duplicate;
    static PriorityQueue<Pair> sortList = new PriorityQueue<Pair>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken()) - 1;
        c = Integer.parseInt(st.nextToken()) - 1;
        k = Integer.parseInt(st.nextToken());

        for(int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            A[i][0] = Integer.parseInt(st.nextToken());
            A[i][1] = Integer.parseInt(st.nextToken());
            A[i][2] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i <= 100; i++) {
            int tempMaxR = maxR;
            int tempMaxC = maxC;
            // 정렬하기 전 정답 조건 확인
            if(A[r][c] == k) {
                System.out.println(i);
                break;
            };
            // 100초가 지나도 A[r][c] = k가 되지 않으면 || 크기가 100을 넘어가는 경우 -1을 출력
            if(i == 100 || maxC > 100 || maxR > 100) {
                System.out.println(-1);
                break;
            }

            duplicate = duplicate(maxR, maxC);
            A = new int[100][100];
            if(maxR >= maxC) {
                for(int j = 0; j < maxR; j++) {
                    rowSort(j);
                    int s = sortList.size();
                    for(int a = 0; a < s; a++) {
                        // 열 크기
                        if(a * 2 + 2 > tempMaxC) tempMaxC = a * 2 + 2;
                        // 크기가 100을 넘어가는 경우
                        if(tempMaxC > 100) {
                            maxC = tempMaxC;
                            break;
                        }

                        Pair sorted = sortList.poll();
                        A[j][a * 2] = sorted.first;
                        A[j][a * 2 + 1] = sorted.second;
                    }
                }
            } else if(maxR < maxC) {
                for(int j = 0; j < maxC; j++) {
                    colSort(j);
                    int s = sortList.size();
                    for(int a = 0; a < s; a++) {
                        // 행 크기 
                        if(a * 2 + 2 > tempMaxR) tempMaxR = a * 2 + 2;
                        // 크기가 100을 넘어가는 경우
                        if(tempMaxR > 100) {
                            maxR = tempMaxR;
                            break;
                        }

                        Pair sorted = sortList.poll();
                        A[a * 2][j] = sorted.first;
                        A[a * 2 + 1][j] = sorted.second;
                    }
                }
            }

            maxC = tempMaxC;
            maxR = tempMaxR;
        }
    }
    
    static void rowSort(int r) {
        // 개수 정보
        HashMap<Integer, Integer> counting = new HashMap<>();
        // 수 정보
        Queue<Integer> keys = new LinkedList<Integer>();
        int count;

        // 각각의 수가 몇 번 나왔는지 확인
        for(int i = 0; i < maxC; i++) {
            if(counting.containsKey(duplicate[r][i])) {
                count = counting.get(duplicate[r][i]);
                counting.replace(duplicate[r][i], count+1);
            } else {
                if(duplicate[r][i] == 0) continue;
                counting.put(duplicate[r][i], 1);
                keys.add(duplicate[r][i]);
            }
        }

        int s = keys.size();
        for(int i = 0; i < s; i++) {
            int key = keys.poll();
            // 오름차순으로 정렬
            sortList.add(new Pair(key, counting.get(key)));
        }
    }

    static void colSort(int c) {
        // 개수 정보
        HashMap<Integer, Integer> counting = new HashMap<>();
        // 수 정보
        Queue<Integer> keys = new LinkedList<>();
        int count;

        // 각각의 수가 몇 번 나왔는지 확인
        for(int i = 0; i < maxR; i++) {
            if(counting.containsKey(duplicate[i][c])) {
                count = counting.get(duplicate[i][c]);
                counting.replace(duplicate[i][c], count+1);
            } else {
                if(duplicate[i][c] == 0) continue;
                counting.put(duplicate[i][c], 1);
                keys.add(duplicate[i][c]);
            }
        }

        int s = keys.size();
        for(int i = 0; i < s; i++) {
            int key = keys.poll();
            // 오름차순으로 정렬
            sortList.add(new Pair(key, counting.get(key)));
        }
    }

    static int[][] duplicate(int r, int c) {
        int[][] temp = new int[r][c];
        for(int i = 0; i < r; i++) {
            for(int j = 0; j < c; j++) {
                temp[i][j] = A[i][j];
            }
        }
        return temp;
    }
}