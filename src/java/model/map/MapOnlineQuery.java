package model.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.awt.Desktop;

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
        return resultString.toString();
    }

    private static int[][] revertMapData(int[][] mapData) {
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                switch (mapData[row][col]) {
                    case 1:
                        break;
                    case 2:
                        if (col + 1 < mapData[row].length) { // 检查右侧格子是否越界
                            mapData[row][col + 1] = 2; // 占用右侧格子
                        }
                        break;
                    case 3:
                        if (row + 1 < mapData.length) { // 检查下方格子是否越界
                            mapData[row + 1][col] = 3; // 占用下方格子
                        }
                        break;
                    case 4:
                        if (row + 1 < mapData.length && col + 1 < mapData[row].length) { // 检查右下角格子是否越界
                            mapData[row][col + 1] = 4; // 占用右侧格子
                            mapData[row + 1][col] = 4; // 占用下方格子
                            mapData[row + 1][col + 1] = 4; // 占用右下角格子
                        }
                        break;
                    case 0:
                        break;//空地方无块
                    case -1:
                        break;//被覆盖已占用
                }
            }
        }
        return mapData;
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

    //todo:这里有必要再解析它的解法文件吗?加上t=json就是解法文件
}
