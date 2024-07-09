//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserDaoJDBCImpl implements UserDao {
//    private final Util util = Util.getInstance();
//
//    @Override
//    public void createUsersTable() {
//        String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), lastName VARCHAR(50), age TINYINT)";
//        try (Connection connection = util.getConnection()) {
//            if (connection != null) {
//                try (Statement statement = connection.createStatement()) {
//                    statement.executeUpdate(sql);
//                    System.out.println("Table 'Users' created or already exists.");
//                }
//            } else {
//                System.err.println("Failed to obtain DB connection.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void dropUsersTable() {
//        String sql = "DROP TABLE IF EXISTS Users";
//        try (Connection connection = util.getConnection()) {
//            if (connection != null) {
//                try (Statement statement = connection.createStatement()) {
//                    statement.executeUpdate(sql);
//                    System.out.println("Table 'Users' dropped if it existed.");
//                }
//            } else {
//                System.err.println("Failed to obtain DB connection.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
//        String sql = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
//        try (Connection connection = util.getConnection()) {
//            if (connection != null) {
//                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//                    preparedStatement.setString(1, name);
//                    preparedStatement.setString(2, lastName);
//                    preparedStatement.setByte(3, age);
//
//                    int affectedRows = preparedStatement.executeUpdate();
//                    if (affectedRows == 0) {
//                        throw new SQLException("Creating user failed, no rows affected.");
//                    }
//
//                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                        if (generatedKeys.next()) {
//                            long id = generatedKeys.getLong(1);
//                            System.out.println("New user added with id = " + id);
//                        } else {
//                            throw new SQLException("Creating user failed, no ID obtained.");
//                        }
//                    }
//                }
//            } else {
//                System.err.println("Failed to obtain DB connection.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void removeUserById(long id) {
//        String sql = "DELETE FROM Users WHERE id = ?";
//        try (Connection connection = util.getConnection()) {
//            if (connection != null) {
//                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                    preparedStatement.setLong(1, id);
//                    int affectedRows = preparedStatement.executeUpdate();
//                    if (affectedRows == 0) {
//                        throw new SQLException("Deleting user failed, no rows affected.");
//                    }
//                    System.out.println("User with id = " + id + " was removed.");
//                }
//            } else {
//                System.err.println("Failed to obtain DB connection.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        List<User> users = new ArrayList<>();
//        String sql = "SELECT * FROM Users";
//        try (Connection connection = util.getConnection()) {
//            if (connection != null) {
//                try (Statement statement = connection.createStatement();
//                     ResultSet resultSet = statement.executeQuery(sql)) {
//
//                    while (resultSet.next()) {
//                        long id = resultSet.getLong("id");
//                        String name = resultSet.getString("name");
//                        String lastName = resultSet.getString("lastName");
//                        byte age = resultSet.getByte("age");
//                        User user = new User(name, lastName, age);
//                        users.add(user);
//                    }
//                    System.out.println("Retrieved all users: " + users);
//                }
//            } else {
//                System.err.println("Failed to obtain DB connection.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        String sql = "TRUNCATE TABLE Users";
//        try (Connection connection = util.getConnection()) {
//            if (connection != null) {
//                try (Statement statement = connection.createStatement()) {
//                    statement.executeUpdate(sql);
//                    System.out.println("Table 'Users' was cleaned.");
//                }
//            } else {
//                System.err.println("Failed to obtain DB connection.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}