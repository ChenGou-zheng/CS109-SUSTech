package model;

public class TestMap {
    public static void main(String[] args) {
        try {
            // 创建一个示例地图
            int[][] mapData = {
                    {1, 2, 2, 1},
                    {1, 3, 2, 2},
                    {1, 3, 4, 4},
                    {0, 0, 4, 4}
            };
            MapModel map = new MapModel(mapData);
            map.printMap();

            // 保存地图
            MapFileManager.saveMap("level1.json", map);
            System.out.println("地图已保存");

            // 删除地图（可选）
            // MapFileManager.deleteMap("level1.json");

            // 加载地图
            MapModel loadedMap = MapFileManager.loadMapFromFile("level1.json");
            System.out.println("加载的地图：");
            loadedMap.printMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
