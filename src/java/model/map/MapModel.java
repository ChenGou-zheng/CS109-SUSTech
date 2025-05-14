package model.map;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class MapModel implements Serializable {
    private static final long serialVersionUID = 1L; // 推荐添加序列化版本号
    private int[][] matrix;
    private int[][]originalMatrix;
    private int[] targetPosition = new int[2];
    private int mapType;
    private String mapName;
    private int leaseMove;
    private String mapId;//唯一主键标识，或许使用hash对应？
//todo:hash自循环算法，生成单个json文件，然后代入hash算法得到mapId，然后写入json文件，批量生成最后合并
//todo:各文件设定x000后计算hash值得到id,然后再分离为不同的json文件
    //todo:在Advanced模式中有过多参数，使用起来或许有点复杂？使用builder模式？还是其实使用场合不多？
    //todo:targetPosition 未在其他位置更新
    public int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }

    public MapModel(int[][] matrix, int mapType, String mapName, int leaseMove, String mapId, int[] targetPosition) {
        this.matrix = matrix;
        this.originalMatrix=copyMatrix(matrix);
        this.targetPosition = getTargetPosition();
        this.mapId = mapId;
        this.mapType = mapType;
        this.mapName = mapName;
        this.leaseMove = leaseMove;
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
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
            throw new IllegalArgumentException("Invalid row or column index: (" + row + ", " + col + ")");
        }
        return matrix[row][col];
    }
    public int[][] getMatrix() {
        return matrix;
    }//todo:输出深拷贝,还是这里需要统一量？状态管理！

    public void setMatrix(int row, int col, int value) {
        //这里假定row和col已经检查过了
        this.matrix[row][col] = value;
    }
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    public int getMapType() {return mapType;}
    public int getLeaseMove() {return leaseMove;}
    public String getMapName() {return mapName;}
    public String getMapId() {return mapId;}
    public void setMapId(String mapId) {this.mapId = mapId;}
    public int[] getTargetPosition() {return targetPosition;}

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
        obj.put("targetPosition", targetPosition);
        obj.put("mapType", mapType);
        obj.put("mapName", mapName);
        obj.put("leaseMove", leaseMove);
        obj.put("mapId", mapId);
        obj.put("originalMatrix", originalMatrix); // 新增字段

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

//todo:人工识别挖掘各种数据集？
//todo：美术资源
    // 从 JSON 对象解析
    public static MapModel fromJson(JSONObject obj) {
        int width = obj.getInt("width");
        int height = obj.getInt("height");

        // 解析 map 字段为 int[][]
        JSONArray jsonArray = obj.getJSONArray("map");
        int[][] map = new int[height][width];
        for (int i = 0; i < height; i++) {
            JSONArray row = jsonArray.getJSONArray(i);
            for (int j = 0; j < width; j++) {
                map[i][j] = row.getInt(j);
            }
        }

        // 解析 targetPosition 字段为 int[]
        JSONArray targetArray = obj.getJSONArray("targetPosition");
        int[] targetPosition = new int[targetArray.length()];
        for (int i = 0; i < targetArray.length(); i++) {
            targetPosition[i] = targetArray.getInt(i);
        }

        // 创建 MapModel 对象
        return new MapModel(
                map,
                obj.optInt("mapType", 0),
                obj.optString("mapName", ""),
                obj.optInt("leaseMove", 0),
                obj.optString("mapId", ""),
                targetPosition
        );
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

    public String getMapNameByMapId(String mapId) {
        if (mapId.equals(this.mapId)) {
        return mapName;
        } else {
            return null;
        }
    }
}