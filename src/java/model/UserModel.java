package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserModel {
    private static final String FILE_PATH = "users.json";
    private JSONArray users;

    public UserModel() {
        loadUsers();
    }

    // 加载用户数据
    private void loadUsers() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                users = new JSONArray();
                saveUsers();
            } else {
                String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
                users = new JSONArray(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            users = new JSONArray();
        }
    }

    // 保存用户数据到文件
    private void saveUsers() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(users.toString(4)); // 格式化输出
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String username, String password, String email) {
        if (getUser(username) != null) {
            return false; // 用户已存在
        }
        JSONObject user = new JSONObject();
        user.put("username", username);
        user.put("password", password);
        user.put("email", email); // 添加邮箱字段
        user.put("gameData", new JSONObject());
        users.put(user);
        saveUsers();
        return true;
    }

    // 更新用户邮箱
    public boolean updateEmail(String username, String newEmail) {
        JSONObject user = getUser(username);
        if (user == null) {
            return false; // 用户不存在
        }
        user.put("email", newEmail);
        saveUsers();
        return true;
    }

    // 获取用户邮箱
    public String getEmail(String username) {
        JSONObject user = getUser(username);
        return user != null ? user.getString("email") : null;
    }

    // 修改用户密码
    public boolean updatePassword(String username, String newPassword) {
        JSONObject user = getUser(username);
        if (user == null) {
            return false; // 用户不存在
        }
        user.put("password", newPassword);
        saveUsers();
        return true;
    }

    // 删除用户
    public boolean deleteUser(String username) {
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                users.remove(i);
                saveUsers();
                return true;
            }
        }
        return false; // 用户不存在
    }

    // 验证用户名和密码
    public boolean validateUser(String username, String password) {
        JSONObject user = getUser(username);
        return user != null && user.getString("password").equals(password);
    }

    // 获取用户对象
    private JSONObject getUser(String username) {
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                return user;
            }
        }
        return null;
    }

    // 获取用户的游戏数据
    public JSONObject getGameData(String username) {
        JSONObject user = getUser(username);
        return user != null ? user.getJSONObject("gameData") : null;
    }

    // 更新用户的游戏数据
    public boolean updateGameData(String username, JSONObject gameData) {
        JSONObject user = getUser(username);
        if (user == null) {
            return false; // 用户不存在
        }
        user.put("gameData", gameData);
        saveUsers();
        return true;
    }
}
/*如何进一步拓展用户数据字段？
todo：完整版本的json数据集
可以通过在 UserModel 中的用户 JSON 对象中添加新的字段来扩展用户数据。以下是步骤和示例代码：
1. 添加新字段
在 addUser 方法中为用户对象添加更多字段。例如，添加 email 和 registrationDate 字段。
2. 更新相关方法
确保在修改、删除和获取用户数据时处理这些新字段
// 添加用户
public boolean addUser(String username, String password, String email, String registrationDate) {
    if (getUser(username) != null) {
        return false; // 用户已存在
    }
    JSONObject user = new JSONObject();
    user.put("username", username);
    user.put("password", password);
    user.put("email", email); // 新增字段
    user.put("registrationDate", registrationDate); // 新增字段
    user.put("gameData", new JSONObject()); // 为游戏数据留出空间
    users.put(user);
    saveUsers();
    return true;
}

// 修改用户邮箱
public boolean updateEmail(String username, String newEmail) {
    JSONObject user = getUser(username);
    if (user == null) {
        return false; // 用户不存在
    }
    user.put("email", newEmail);
    saveUsers();
    return true;
}

// 获取用户邮箱
public String getEmail(String username) {
    JSONObject user = getUser(username);
    return user != null ? user.getString("email") : null;
}
 */