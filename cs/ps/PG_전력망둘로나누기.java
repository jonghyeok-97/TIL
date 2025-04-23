import java.util.ArrayList;
import java.util.List;

public class PG_전력망둘로나누기 {

    private static boolean[] visited;

    public int solution(int n, int[][] wires) {
        int answer = 1000;
        List<Integer>[] nodes = new ArrayList[n+1];


        for (int i = 1; i <= n; i++) {
            nodes[i] = new ArrayList<>();
        }

        // 송전탑 연결
        for(int i = 0; i < wires.length; i++) {
            int first = wires[i][0];
            int second = wires[i][1];
            nodes[first].add(second);
            nodes[second].add(first);
        }

        for(int i = 1; i <= n; i++) {

            List<Integer> nodeNumbers = nodes[i];

            for(int nodeNumber : nodeNumbers) {
                visited = new boolean[n + 1];
                visited[i] = true;
                visited[nodeNumber] = true;
                int first = dfs(i, nodeNumber, nodes);
                int second = dfs(nodeNumber, i, nodes);
                answer = Math.min(answer, Math.abs(first - second));
            }
        }

        return answer;
    }

    private int dfs(int start, int blocked, List<Integer>[] nodes) {
        int sum = 1;
        visited[start] = true;

        for(int number : nodes[start]) {
            if(visited[number] || number == blocked) continue;
            sum += dfs(number, blocked, nodes);
        }

        return sum;
    }
}
