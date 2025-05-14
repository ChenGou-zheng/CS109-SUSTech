package model;

import model.map.MapFileManager;
import model.map.MapModel;

public class TestMap {
    public static void main(String[] args) {
        try {
            // 创建一个示例地图
            int[][] defaultMap = new int[][]{
                    {3, 3, 1, 1},
                    {3, 3, 3, 3},
                    {4, 4, 3, 3},
                    {4, 4, 3, 1},
                    {0, 1, 3, 0}
            };
            int[] targetPosition = {3, 1}; // 目标位置
            MapModel mapModel = new MapModel(defaultMap, 0, "null", 9, "x000", targetPosition);

            mapModel.printMap();

            // 保存地图
            MapFileManager.saveMap(mapModel);
            System.out.println("地图已保存");

            // 删除地图（可选）
            // MapFileManager.deleteMap("level1.json");

            // 加载地图
            MapModel loadedMap = MapFileManager.loadMapFromFile("x000");
            System.out.println("加载的地图：");
            loadedMap.printMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
