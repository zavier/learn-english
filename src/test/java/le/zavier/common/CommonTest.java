package le.zavier.common;

import java.util.ArrayList;
import java.util.List;

public class CommonTest {

    public static void main(String[] args) {
        List<String> lists = new ArrayList<>();
        boolean b = lists.stream().noneMatch("1112"::startsWith);
        System.out.println(b);
    }
}
