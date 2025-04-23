import java.util.LinkedList;
import java.util.Queue;

public class PG_서버증설횟수 {

    public int solution(int[] players, int m, int k) {
        int answer = 0;
        Queue<Server> queue = new LinkedList<>();

        for(int i = 0; i < players.length; i++) {
            while(!queue.isEmpty() && queue.peek().endTime == i) {
                queue.poll();
            }
            int serverCount = players[i] / m;
            if(serverCount > queue.size()) {
                int increaseCount = serverCount - queue.size();
                for(int j = 0; j < increaseCount; j++) {
                    queue.add(new Server(i, i + k));
                    answer++;
                }
            }

        }
        return answer;
    }

    class Server {
        int startTime;
        int endTime;

        Server(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
