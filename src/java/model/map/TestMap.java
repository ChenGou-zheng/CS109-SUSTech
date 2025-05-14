package model.map;
import model.map.MapOnlineQuery;

//字段解析
/*map忽略
mapType:0自由单定(19格障碍物),1自由多定图形,2限制滑动方向(纵横,边界,日字线路),3异形
mapName传统文化_maptype
leaseMove根据查询
mapId Hash生成
targetPosition 标记,或许要用多元组
 */
public class TestMap {
    public static void loadDefaultMapData(){
        try{
            int[][] mapData = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 3, 1, 3},
                    {3, 3, 1, 3},
                    {1, 0, 0, 1}
            };
            int mapType = 0;
            int[] targetPosition = {4, 1}; // 目标位置
            StringBuilder mapName = new StringBuilder("走投无路");

            switch(mapType){
                case 0:
                    mapName.append("_自由单定");
                    break;
                case 1:
                    mapName.append("_五兵四将");
                    break;
                case 2:
                    mapName.append("_自由多定");
                    break;
                case 30:
                    mapName.append("_限制滑动方向_纵横");
                    break;
                case 31:
                    mapName.append("_限制滑动方向_边界");
                    break;
                case 32:
                    mapName.append("_限制滑动方向_日字");
                    break;
                case 4:
                    mapName.append("_异形降临");
                    break;            }

            String solution = MapOnlineQuery.getSolution(mapData);
            int steps = MapOnlineQuery.getMinimumSteps(solution);
            System.out.println("最少步数: " + steps);


            MapModel mapModel = new MapModel(mapData, mapType, mapName.toString(), steps, "default", targetPosition);
            MapFileManager.saveMap(mapModel);
            mapModel.printMap();
            System.out.println("地图已保存");


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        loadDefaultMapData();
    }

    public static void Test(String[] args) {
        try {
            // 创建一个示例地图
            int[][] defaultMap = new int[][]{
                    {3, 3, 1, 1},
                    {3, 3, 3, 3},
                    {4, 4, 3, 3},
                    {4, 4, 3, 1},
                    {0, 1, 3, 0}
            };

            int[][] mapModel = new int[][]{
                    {3, 3, 1, 1},
                    {3, 3, 3, 3},
                    {4, 4, 3, 3},
                    {4, 4, 3, 1},
                    {0, 1, 3, 0}
            };
            //showSolution(mapData);
            // 获取 solution 数据




            // 保存地图
            //MapFileManager.saveMap(mapModel);
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
