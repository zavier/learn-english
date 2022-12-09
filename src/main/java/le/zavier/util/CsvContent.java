package le.zavier.util;

import java.util.Iterator;

public class CsvContent implements Iterable<String[]>{
    private String[][] data;
    private int initRows = 8;

    private int currentRows = -1;
    private int maxColumn = 0;

    {
        data = new String[initRows][];
    }

    public int getTotalRows() {
        return currentRows + 1;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public String[] getRows(int index) {
        return data[index];
    }

    public void addRow(String[] row) {
        if (row.length > maxColumn) {
            maxColumn = row.length;
        }
        checkRowsAndAddCurrentRows();
        data[currentRows] = row;
    }

    private void checkRowsAndAddCurrentRows() {
        currentRows++;
        if (currentRows < data.length) {
            return;
        }
        data = expandTwoDimensionArray(data, 2 * data.length);
    }

    private String[][] expandTwoDimensionArray(String[][] srcData, int newLength) {
        if (newLength <= srcData.length) {
            return srcData;
        }
        String[][] temp = new String[newLength][];
        for (int i = 0; i < srcData.length; i++) {
            temp[i] = srcData[i];
        }
        return temp;
    }

    @Override
    public Iterator<String[]> iterator() {
        return new Iterator<String[]>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < currentRows;
            }

            @Override
            public String[] next() {
                return new String[index++];
            }
        };
    }
}