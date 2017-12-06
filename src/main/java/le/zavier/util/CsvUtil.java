package le.zavier.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import le.zavier.exception.CheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CsvUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    /**
     * 读取CSV文件
     * @param srcfile 文件路径
     * @return
     */
    public static List<List<String>> readCsvFile(File srcfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(srcfile);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在,{}", srcfile);
            throw new CheckException("文件不存在" + srcfile);
        }
        return readCsvFile(fileInputStream);
    }

    public static List<List<String>> readCsvFile(InputStream inputStream) {
        List<List<String>> lists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lists.add(Arrays.asList(line.split(",")));
            }
        } catch (IOException e) {
            logger.error("解析CSV文件出错", e);
            throw new CheckException("解析CSV文件出错");
        }
        return lists;
    }

    /**
     * 写入CSV文件
     * @param content 以逗号分隔的字符串List
     * @param file 要写入的文件
     */
    public static void writeCsvFile(List<String> content, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : content) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            logger.error("写入CSV文件失败");
            throw new CheckException("写入CSV文件失败" + file.getAbsolutePath());
        }
    }
}
