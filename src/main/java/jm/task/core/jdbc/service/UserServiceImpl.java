//package jm.task.core.jdbc.service;
//
//import jm.task.core.jdbc.dao.UserDao;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.service.UserService;
//
//import java.util.List;
//
//public class UserServiceImpl implements UserService {
//    private UserDao userDao;
//
//    public UserServiceImpl() {
//        this.userDao = new UserDaoJDBCImpl();
//    }
//
//    @Override
//    public void createUsersTable() {
//        userDao.createUsersTable();
//    }
//
//    @Override
//    public void dropUsersTable() {
//        userDao.dropUsersTable();
//    }
//
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
//        userDao.saveUser(name, lastName, age);
//        System.out.println("User с именем – " + name + " добавлен в базу данных");
//    }
//
//
//    @Override
//    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
//    }
//
//    @Override
//    public void removeUserById(long id) {
//        userDao.removeUserById(id);
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        userDao.cleanUsersTable();
//    }
//}

package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;


import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoHibernateImpl(); // Использую Hibernate
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}