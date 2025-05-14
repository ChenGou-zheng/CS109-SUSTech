package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserTest {
    public static void main(String[] args) {
        UserModel userModel = new UserModel();

        // 添加用户
        userModel.addUser("player1", "password123","12412903@mail.sustech.edu.cn");

        // 验证用户
        System.out.println(userModel.validateUser("player1", "password123")); // true

        // 修改密码
        userModel.updatePassword("player1", "newpassword");

        // 删除用户
        userModel.deleteUser("player1");

        // 更新游戏数据
        JSONObject gameData = new JSONObject();
        gameData.put("level", 5);
        gameData.put("score", 1000);
        userModel.updateGameData("player1", gameData);

        // 获取游戏数据
        System.out.println(userModel.getGameData("player1"));
    }
}
