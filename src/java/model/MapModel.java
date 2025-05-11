package model;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapModel {
    private int[][] matrix;
    private int [][]originalMatrix;
    public int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }

    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        this.originalMatrix=copyMatrix(matrix);
    }

    public void resetOriginalMatrix() {
        this.matrix = copyMatrix(originalMatrix);

    }
    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    // 转换为 JSON 对象
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("width", getWidth());
        obj.put("height", getHeight());

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < getHeight(); i++) {
            JSONArray row = new JSONArray();
            for (int j = 0; j < getWidth(); j++) {
                row.put(matrix[i][j]);
            }
            jsonArray.put(row);
        }
        obj.put("map", jsonArray);
        return obj;
    }

    // 从 JSON 对象解析
    public static MapModel fromJson(JSONObject obj) {
        int width = obj.getInt("width");
        int height = obj.getInt("height");

        int[][] map = new int[height][width];
        JSONArray jsonArray = obj.getJSONArray("map");
        for (int i = 0; i < height; i++) {
            JSONArray row = jsonArray.getJSONArray(i);
            for (int j = 0; j < width; j++) {
                map[i][j] = row.getInt(j);
            }
        }

        return new MapModel(map);
    }

    // 打印地图用于测试
    public void printMap() {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }
}