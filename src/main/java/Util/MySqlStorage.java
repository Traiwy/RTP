package Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlStorage {
    private static Connection connection;


    public MySqlStorage() {
        try {
            String databaseUrl = "jdbc:mysql://" + ConfigManager.MySQL.HOST + ":" + ConfigManager.MySQL.PORT + "/" + ConfigManager.MySQL.DATABASE;
            connection = DriverManager.getConnection(databaseUrl, ConfigManager.MySQL.USER, ConfigManager.MySQL.PASSWORD);
            System.out.println("Подключение к MySQL установлено!");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к MySQL: " + e.getMessage());
        }
    }
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Подключение к MySQL закрыто!");
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии подключения: " + e.getMessage());
            }
        }
    }
    public static Connection getConnection() {
        return connection;
    }
    public static void CreateTable() {
        String sql = "CREATE TABLE IF NOT EXISTS rtp (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    player_name VARCHAR(16) NOT NULL,\n" +
                "    x DOUBLE NOT NULL,\n" +
                "    y DOUBLE NOT NULL,\n" +
                "    z DOUBLE NOT NULL,\n" +
                "    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "    world VARCHAR(64) NOT NULL\n" +
                ");";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate(); // Выполняем запрос
            System.out.println("Таблица 'rtp' создана или уже существует.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }
    public static void logRTP(Player player, Location location) {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "INSERT INTO rtp (player_name, x, y, z, world, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, player.getName()); // Ник игрока
                statement.setDouble(2, location.getX());  // Координата X
                statement.setDouble(3, location.getY());  // Координата Y
                statement.setDouble(4, location.getZ());  // Координата Z
                statement.setString(5, location.getWorld().getName()); // Название мира
                statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
                System.out.println("Данные игрока " + player.getName() + " записаны в базу данных.");
            } catch (SQLException e) {
                System.out.println("Ошибка при записи данных в базу: " + e.getMessage());
            }
        } else {
            System.out.println("Соединение с базой данных не установлено!");
        }
    }
    public static  List<Map<String, Object>> getLastRTPLocations(String playerName) {
        Connection connection = getConnection();
        List<Map<String, Object>> records = new ArrayList<>();
        if (connection != null) {
            String sql = "SELECT id, player_name, x, y, z, world, timestamp FROM rtp WHERE player_name = ? ORDER BY timestamp DESC ";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, playerName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Map<String, Object> record = new HashMap<>();
                    record.put("player_name", resultSet.getString("player_name"));
                    record.put("x", resultSet.getDouble("x"));
                    record.put("y", resultSet.getDouble("y"));
                    record.put("z", resultSet.getDouble("z"));
                    record.put("world", resultSet.getString("world"));
                    record.put("timestamp", resultSet.getTimestamp("timestamp"));
                    records.add(record);

                }
            } catch (SQLException e) {
                System.out.println("Ошибка при получении данных из базы: " + e.getMessage());
            }
        } else {
            System.out.println("Соединение с базой данных не установлено!");
        }
        return records;
    }
}


