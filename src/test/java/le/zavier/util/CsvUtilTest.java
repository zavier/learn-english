package le.zavier.util;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvUtilTest {
    private CsvUtil util;
//    @Before
    public void setUp() {
        util = new CsvUtil();
    }

//    @Test
    public void testReadFile() {
        File file = new File("E:\\test2.csv");
        CsvContent lists = util.readCsvFile(file);
    }

//    @Test
    public void testWriteFile() {
        File file = new File("E:\\test2.csv");
        String s = "你好,测试,hello";
        String s1 = "今天,昨天,明天";
        util.writeCsvFile(Lists.newArrayList(s, s1), file);
    }
}
