package model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.map.MapOnlineQuery;

public class MapGenerator {
    private static List<int[]> initializeEmptySpaces(int rows, int cols) {
        List<int[]> emptySpaces = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                emptySpaces.add(new int[]{i, j});
            }
        }
        return emptySpaces;
    }

    private static void placeBlock(int[][] map, List<int[]> emptySpaces, int blockId, int size, Random random) {
        boolean placed = false;
        while (!placed && !emptySpaces.isEmpty()) {
            int index = random.nextInt(emptySpaces.size());
            int[] position = emptySpaces.get(index);
            int row = position[0];
            int col = position[1];

            if (canPlaceBlock(map, row, col, size)) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        map[row + i][col + j] = blockId;
                        final int occupiedRow = row + i;
                        final int occupiedCol = col + j;
                        emptySpaces.removeIf(space -> space[0] == occupiedRow && space[1] == occupiedCol);
                    }
                }
                placed = true;
            }
        }
    }

    private static boolean canPlaceBlock(int[][] map, int row, int col, int size) {
        if (row + size > 5 || col + size > 4) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[row + i][col + j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void addZeroBlocks(int[][] map, List<int[]> emptySpaces, Random random) {
        for (int i = 0; i < 2; i++) { // 生成两个 0 块
            if (!emptySpaces.isEmpty()) {
                int index = random.nextInt(emptySpaces.size());
                int[] position = emptySpaces.remove(index); // 从空闲位置中移除
                map[position[0]][position[1]] = 0; // 设置为 0 块
            }
        }
    }

    public static int[][] generateMap() {
        int[][] map = new int[5][4];
        List<int[]> emptySpaces = initializeEmptySpaces(5, 4);
        Random random = new Random();

        // 按优先级放置方块
        placeBlock(map, emptySpaces, 4, 2, random); // 放置大块（2*2）
        for (int i = 0; i < 4; i++) {
            placeBlock(map, emptySpaces, 3, 1, random); // 放置竖块（2*1）
        }
        for (int i = 0; i < 2; i++) {
            placeBlock(map, emptySpaces, 2, 2, random); // 放置横块（1*2）
        }
        for (int i = 0; i < 4; i++) {
            placeBlock(map, emptySpaces, 1, 1, random); // 放置单块（1*1）
        }

        // 添加两个 0 块
        addZeroBlocks(map, emptySpaces, random);

        return map;
    }
//todo:这个速度疑似太慢了.全局检查深拷贝和浅拷贝问题
    public static int[][] checkMap(){
        boolean isGenerated = false;
        while (!isGenerated) {
            int[][] map = generateMap();
            if (MapOnlineQuery.showSolution(map)) {
                isGenerated = true;
                return map;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        checkMap();
    }
}
//todo:后续可添加功能
/*
支持自定义地图大小
允许用户输入行数和列数，动态生成不同大小的地图。


增加块类型的多样性
支持更多类型的方块（如L形、T形等），并调整放置逻辑。


添加地图验证功能
在生成地图后，验证地图是否满足某些规则（如是否可解）。


支持地图导出与导入
将生成的地图保存为文件（如JSON或CSV格式），并支持从文件加载地图。


优化块放置算法
使用更高效的算法（如回溯或启发式算法）来减少碎片化，提高生成效率。


加入随机种子
允许用户设置随机种子，以便生成相同的地图。


可视化地图生成过程
在生成地图时，动态显示方块的放置过程，提升用户体验。


支持多种生成模式
提供不同的生成模式（如完全随机、规则化布局等）。
 */