package model.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.awt.Desktop;

public class smalltool {
    // 将二维数组按横向顺序读取存入一维数组
    public static String convertMapData(int[][] mapData) {
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

    //将运行中的mapData中的-1复原
    //public static String revertMapData(int[][] mapData) {


    //}

    public static void main(String[] args) {
        System.out.println("注意此处的步数计算规则不同.");
        // 示例二维数组
        int[][] mapData = new int[][]{
                {3, 3, 1, 1},
                {3, 3, 3, 3},
                {4, 4, 3, 3},
                {4, 4, 3, 1},
                {0, 1, 3, 0}
        };

        // 调用方法并打印结果
        String queryS = convertMapData(mapData);
        StringBuilder apiUrl = null;
        apiUrl = new StringBuilder("https://klotski.online/solution?q=");
        apiUrl.append(queryS);



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

                // 打印响应数据
                System.out.println("API 响应: " + response.toString());
            } else {
                System.out.println("请求失败，响应码: " + responseCode);
            }

            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
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
        }
    }
}
