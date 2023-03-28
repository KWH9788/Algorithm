/*
    https://jellyinghead.tistory.com/53 [참고한 블로그]
    코드는 보지 않고 풀이 방법만 참고했습니다
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Samsung8 {
    static int N, answer = 0;
    public static void main(String[] args) throws Exception {
        int[][] board;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        BackTracking(board, 0);
        System.out.println(answer);
    }

    static void BackTracking(int[][] board, int level) {
        int[][] tempBoard;
        if(level == 5) {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(answer < board[i][j]) answer = board[i][j];
                }
            }
            return;
        }
        for(int i = 0; i < 4; i ++) {
            tempBoard = play(board, i);
            BackTracking(tempBoard, level+1);
        }
    }

    static int[][] play(int[][] board, int direction) {
        // index = 입력할 위치, current = 최근 본 수
        int index = 0, current = 0;
        int[][] playingBoard = new int[N][N];
        // 방향: 상
        if(direction == 0) {
            // i = 좌우 j = 상하
            for(int i = 0; i < N; i++) {
                index = 0; current = 0;
                for(int j = 0; j < N; j++) {
                    // 최근에 본 수가 있다면
                    if(current != 0) {
                        // 최근 본 수와 일치한 경우 합치기
                        if(current == board[j][i]) {
                            playingBoard[index][i] = current * 2;
                            current = 0;
                            index += 1;
                        // 최근 본 수와 다르다면 위치만 옮기기
                        } else if(board[j][i] != 0) {
                            index += 1;
                            current = board[j][i];
                            playingBoard[index][i] = current;
                        }
                    // 최근에 본 수가 없을 경우
                    } else if(current == 0 && board[j][i] != 0) {
                        current = board[j][i];
                        playingBoard[index][i] = current;
                    }
                }
            }
        }
        // 방향: 하
        if(direction == 1) {
            // i = 좌우 j = 상하
            for(int i = 0; i < N; i++) {
                index = N-1; current = 0;
                for(int j = N-1; j >= 0; j--) {
                    // 최근에 본 수가 있다면
                    if(current != 0) {
                        // 최근 본 수와 일치한 경우 합치기
                        if(current == board[j][i]) {
                            playingBoard[index][i] = current * 2;
                            current = 0;
                            index -= 1;
                        // 최근 본 수와 다르다면 위치만 옮기기
                        } else if(board[j][i] != 0) {
                            index -= 1;
                            current = board[j][i];
                            playingBoard[index][i] = current;
                        }
                    // 최근에 본 수가 없을 경우
                    } else if(current == 0 && board[j][i] != 0) {
                        current = board[j][i];
                        playingBoard[index][i] = current;
                    }
                }
            }
        }
        // 방향: 좌
        if(direction == 2) {
            // i = 상하 j = 좌우
            for(int i = 0; i < N; i++) {
                index = 0; current = 0;
                for(int j = 0; j < N; j++) {
                    // 최근에 본 수가 있다면
                    if(current != 0) {
                        // 최근 본 수와 일치한 경우 합치기
                        if(current == board[i][j]) {
                            playingBoard[i][index] = current * 2;
                            current = 0;
                            index += 1;
                        // 최근 본 수와 다르다면 위치만 옮기기
                        } else if(board[i][j] != 0) {
                            index += 1;
                            current = board[i][j];
                            playingBoard[i][index] = current;
                        }
                    // 최근에 본 수가 없을 경우
                    } else if(current == 0 && board[i][j] != 0) {
                        current = board[i][j];
                        playingBoard[i][index] = current;
                    }
                }
            }
        }
        // 방향: 우
        if(direction == 3) {
            // i = 상하 j = 좌우
            for(int i = 0; i < N; i++) {
                index = N-1; current = 0;
                for(int j = N-1; j >= 0; j--) {
                    // 최근에 본 수가 있다면
                    if(current != 0) {
                        // 최근 본 수와 일치한 경우 합치기
                        if(current == board[i][j]) {
                            playingBoard[i][index] = current * 2;
                            current = 0;
                            index -= 1;
                        // 최근 본 수와 다르다면 위치만 옮기기
                        } else if(board[i][j] != 0) {
                            index -= 1;
                            current = board[i][j];
                            playingBoard[i][index] = current;
                        }
                    // 최근에 본 수가 없을 경우
                    } else if(current == 0 && board[i][j] != 0) {
                        current = board[i][j];
                        playingBoard[i][index] = current;
                    }
                }
            }
        }
        return playingBoard;
    }
}
