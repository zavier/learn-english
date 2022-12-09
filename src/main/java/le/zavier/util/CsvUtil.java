package le.zavier.util;

import le.zavier.exception.CheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CsvUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    public static boolean isCsvFile(String fileName) {
        if (fileName.endsWith(".csv")) {
            return true;
        }
        return false;
    }

    /**
     * 读取CSV文件
     * @param srcfile 文件路径
     * @return
     */
    public static CsvContent readCsvFile(File srcfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(srcfile);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在,{}", srcfile);
            throw new CheckException("文件不存在" + srcfile);
        }
        return readCsvFile(fileInputStream);
    }

    public static CsvContent readCsvFile(InputStream inputStream) {
        CsvContent csvContent = new CsvContent();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                csvContent.addRow(line.split(","));
            }
        } catch (IOException e) {
            logger.error("解析CSV文件出错", e);
            throw new CheckException("解析CSV文件出错");
        }
        return csvContent;
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
