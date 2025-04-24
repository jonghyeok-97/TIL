import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PG_카카오블라인드_메뉴리뉴얼 {

    private static List<String> madeMenu = new ArrayList<>(); // 메뉴 구성 리스트

    public String[] solution(String[] orders, int[] course) {
        String[] answer = {};

        for(int i = 0; i < course.length; i++) {
            int course1 = course[i];
            for(int j = 0; j < orders.length; j++) {
                String order = orders[j];
                if(course1 > order.length()) continue;
                char[] menus = order.toCharArray();
                Arrays.sort(menus);
                makeCombinationMenu(0, menus, course1, "");
            }
        }

        Map<String, Integer> map = madeMenu.stream()
                .collect(Collectors.toMap(
                        menu -> menu,
                        menu -> 1,
                        (value1, value2) -> value1 + value2
                ));

        // map.values()stream()
        //     .sorted(Collectors.reverseOrder())
        //     .collect(Collectors)

        List<String> answers = new ArrayList<>();

        for(int i = 0; i < course.length; i++) {
            int order = course[i];
            List<String> sortedMenu = map.keySet().stream()
                    .filter(key -> key.length() == order)
                    .filter(key -> map.get(key) >= 2)
                    .sorted((key1, key2) -> map.get(key2).compareTo(map.get(key1)))
                    .collect(Collectors.toList());
            // System.out.println(sortedMenu);
            if(!sortedMenu.isEmpty()) {
                String menu = sortedMenu.get(0);
                int maxValue = map.get(menu);
                sortedMenu.stream()
                        .filter(m -> map.get(m) == maxValue)
                        .forEach(m -> answers.add(m));
            }
        }
        return answers.stream()
                .sorted()
                .toArray(String[]::new);
    }

    private void makeCombinationMenu(int start, char[] menus, int maxCourseSize, String makingMenu) {
        if(makingMenu.length() == maxCourseSize) {
            // System.out.println(makingMenu);
            madeMenu.add(makingMenu);
            return;
        }
        for(int i = start; i < menus.length; i++) {
            char c = menus[i];
            makeCombinationMenu(i + 1, menus, maxCourseSize, makingMenu + c);
        }
    }
}
