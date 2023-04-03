package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {
        private static QueryRunner runner;
        private static Connection conn;
        @SneakyThrows
        public static void setUp() {
            runner = new QueryRunner();
            conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pas");
        }

        @SneakyThrows
        public static String getVerificationCode(){
            setUp();
            var dataSQL = "SELECT code FROM auth_codes ORDER BY created";
            return runner.query(conn, dataSQL, new ScalarHandler<String>());
        }

        @SneakyThrows
        public static void clearData(){
            setUp();
            var deleteUser = "DELETE FROM users";
            var deleteCard = "DELETE FROM cards";
            var deleteCode = "DELETE FROM auth_codes";
            var deleteTransactions = "DELETE FROM card_transactions";
            runner.update(conn, deleteCode);
            runner.update(conn, deleteTransactions);
            runner.update(conn, deleteCard);
            runner.update(conn, deleteUser);
        }

}