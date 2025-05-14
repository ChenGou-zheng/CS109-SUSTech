package model.map;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.map.MapOnlineQuery;

public class MapGenerator {

//todo:这个速度疑似太慢了.全局检查深拷贝和浅拷贝问题
public static int[][] generateMap() {
    int rows = 5;
    int cols = 4;
    int[][] map = new int[rows][cols];
    List<int[]> emptySpaces = initializeEmptySpaces(rows, cols);

    // 放置曹操 (2*2)
    placeBlock(map, emptySpaces, 4, 2, 2);

    // 放置5个2号或3号棋子
    Random random = new Random();
    for (int i = 0; i < 5; i++) {
        int blockType = random.nextBoolean() ? 2 : 3; // 随机选择横向(2)或竖向(3)
        placeBlock(map, emptySpaces, blockType, blockType == 2 ? 1 : 2, blockType == 2 ? 2 : 1);
    }

    // 放置4个1*1的棋子
    for (int i = 0; i < 4; i++) {
        placeBlock(map, emptySpaces, 1, 1, 1);
    }

    // 确保有两个空位
    for (int i = 0; i < 2; i++) {
        if (!emptySpaces.isEmpty()) {
            int[] position = emptySpaces.remove(0);
            map[position[0]][position[1]] = 0;
        }
    }

    return map;
}

    private static List<int[]> initializeEmptySpaces(int rows, int cols) {
        List<int[]> emptySpaces = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                emptySpaces.add(new int[]{i, j});
            }
        }
        return emptySpaces;
    }

    private static void placeBlock(int[][] map, List<int[]> emptySpaces, int blockId, int height, int width) {
        for (int i = 0; i < emptySpaces.size(); i++) {
            int[] position = emptySpaces.get(i);
            int row = position[0];
            int col = position[1];

            if (canPlaceBlock(map, row, col, height, width)) {
                for (int r = 0; r < height; r++) {
                    for (int c = 0; c < width; c++) {
                        map[row + r][col + c] = blockId;
                        final int occupiedRow = row + r; // 使用 final 局部变量
                        final int occupiedCol = col + c; // 使用 final 局部变量

                        emptySpaces.removeIf(space -> space[0] == occupiedRow && space[1] == occupiedCol);
                    }
                }
                return;
            }
        }
        throw new RuntimeException("无法放置方块，检查生成逻辑");
    }

    private static boolean canPlaceBlock(int[][] map, int row, int col, int height, int width) {
        if (row + height > map.length || col + width > map[0].length) {
            return false;
        }
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (map[row + r][col + c] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printMap(int[][] map) {
        for (int[] row : map) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }


    public static int[][] checkMap() {
        int maxAttempts = 10000; // 设置最大尝试次数，避免死循环
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int[][] map = generateMap();
            if (MapOnlineQuery.showSolution(map)) {
                return map; // 找到可解的地图，直接返回
            }
        }
       throw new RuntimeException("生成次数过多,请重新尝试"); // 超过最大尝试次数，抛出异常
    }

    public static void main(String[] args) {
        int[][] map = checkMap();
        printMap(map);
        System.out.println("找到可解的地图，已保存到文件!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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