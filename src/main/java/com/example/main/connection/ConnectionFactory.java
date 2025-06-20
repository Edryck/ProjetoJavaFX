package com.example.main.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DATABASE_NAME = "finstock";
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "112358";

    /**
     * Cria e retorna uma nova conexão com o banco de dados.
     * @return um objeto Connection ou null se a conexão falhar.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro na conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}