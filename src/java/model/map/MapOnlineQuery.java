package model.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.awt.Desktop;
import org.json.JSONObject;

public class MapOnlineQuery {
    // 将二维数组按横向顺序读取存入一维数组
    private static String convertMapData(int[][] mapData) {
        int rows = mapData.length;
        int cols = mapData[0].length;
        int[] result = new int[rows * cols];
        int index = 0;

        for (int[] row : mapData) {
            for (int value : row) {
                result[index++] = value;
            }
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 2) {
                result[i] = 3;
            } else if (result[i] == 3) {
                result[i] = 2;
            }
        }
            // 将一维数组转换为字符串
        StringBuilder resultString = new StringBuilder();
        for (int value : result) {
            resultString.append(value);
        }
        System.out.println(resultString.toString());
        return resultString.toString();
    }

    private static int[][] revertMapData(int[][] mapData) {
        int rows = mapData.length;
        int cols = mapData[0].length;
        int[][] revertedMap = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (mapData[row][col] == -1) {
                    continue; // 跳过被标记为占用的格子
                }
                switch (mapData[row][col]) {
                    case 1:
                        revertedMap[row][col] = 1;
                        break;
                    case 2:
                        revertedMap[row][col] = 2;
                        if (col + 1 < cols) {
                            revertedMap[row][col + 1] = -1; // 标记右侧格子为占用
                        }
                        break;
                    case 3:
                        revertedMap[row][col] = 3;
                        if (row + 1 < rows) {
                            revertedMap[row + 1][col] = -1; // 标记下方格子为占用
                        }
                        break;
                    case 4:
                        revertedMap[row][col] = 4;
                        if (col + 1 < cols) {
                            revertedMap[row][col + 1] = -1; // 标记右侧格子为占用
                        }
                        if (row + 1 < rows) {
                            revertedMap[row + 1][col] = -1; // 标记下方格子为占用
                        }
                        if (row + 1 < rows && col + 1 < cols) {
                            revertedMap[row + 1][col + 1] = -1; // 标记右下角格子为占用
                        }
                        break;
                    case 0:
                        revertedMap[row][col] = 0; // 空格
                        break;
                }
            }
        }
        return revertedMap;
    }

    public static boolean showSolution(int[][] mapData){
        String queryData = convertMapData(revertMapData(mapData));
        StringBuilder apiUrl = null;
        apiUrl = new StringBuilder("https://klotski.online/solution?q=");
        apiUrl.append(queryData);
        try {
            // 创建 URL 对象
            URL url = new URL(apiUrl.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 设置请求头
            connection.setRequestProperty("Accept", "application/json");

            // 检查响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 检查响应内容是否为 "Invalid Layout"
                if (response.toString().contains("Invalid Layout")) {
                    System.out.println("无效布局，未打开网站。");
                    return false;
                }
                if (response.toString().contains("No solution")) {
                    System.out.println("无解，未打开网站。");
                    return false;
                }

                // 打印响应数据
                System.out.println("API 响应: " + response.toString());
            } else {
                System.out.println("请求失败，响应码: " + responseCode);
                return false;
            }

            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            // 检查是否支持桌面操作
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                // 打开浏览器访问 URL
                desktop.browse(new URI(apiUrl.toString()));
                System.out.println("已在浏览器中打开: " + apiUrl);
            } else {
                System.out.println("当前系统不支持桌面操作");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getSolution(int[][] mapData) {
        String queryData = convertMapData(revertMapData(mapData));
        StringBuilder apiUrl = new StringBuilder("https://klotski.online/solution?t=json&q=");
        apiUrl.append(queryData);
        try {
            // 创建 URL 对象
            URL url = new URL(apiUrl.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 设置请求头
            connection.setRequestProperty("Accept", "application/json");

            // 检查响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 检查响应内容是否为 HTML
                String responseString = response.toString();
                if (responseString.startsWith("<!DOCTYPE html>") || responseString.startsWith("<html>")) {
                    System.out.println("响应内容为 HTML，可能请求失败或返回了错误页面。");
                    return "响应内容为 HTML，可能请求失败或返回了错误页面。";
                }

                // 检查响应内容是否为 "Invalid Layout"
                if (responseString.contains("Invalid Layout")) {
                    System.out.println("无效布局，未打开网站。");
                    return "无效布局";
                }
                if (responseString.contains("No solution")) {
                    System.out.println("无解，未打开网站。");
                    return "无解，未打开网站。";
                }

                // 打印响应数据
                System.out.println("API 响应: " + responseString);

                // 解析 JSON 数据
                JSONObject jsonObject = new JSONObject(responseString);

                // 提取 solution 字段
                connection.disconnect();
                return jsonObject.getString("solution");
            } else {
                System.out.println("请求失败，响应码: " + responseCode);
                return "请求失败，响应码: " + responseCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "未能正确解析,请检查网络设置";
    }

    public static int getMinimumSteps(String solution) {
        if (solution == null || solution.isEmpty()) {
            throw new IllegalArgumentException("解法数据不能为空");
        }

        // 解法数据中的移动部分从第2个字符开始
        String moves = solution.substring(0);

        // 计算移动的步数
        return (moves.length() / 2);
    }



    public static void main(String[] args) {
        int[][] mapData = new int[][]{
                {3, 3, 1, 1},
                {3, 3, 3, 3},
                {4, 4, 3, 3},
                {4, 4, 3, 1},
                {0, 1, 3, 0}
        };
        //showSolution(mapData);
        // 获取 solution 数据
        String solution = getSolution(mapData);
        int steps = getMinimumSteps(solution);
        System.out.println("最少步数: " + steps);

    }

    //todo:这里有必要再解析它的解法文件吗?加上t=json就是解法文件
}
