package com.geek.spaceshooter.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreManager {
    private static final ScoreManager ourInstance = new ScoreManager();

    public static ScoreManager getInstance() {
        return ourInstance;
    }

    private ArrayList<Integer> scores;

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private ScoreManager() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:scores.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.load();
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public int saveAndGetPos(int score) {
        int pos = 0;
        if (score == 0) {
            pos = -1;
        }
        if (!this.scores.contains(score)) {
            this.scores.add(score);
            Collections.sort(this.scores, new Comparator<Integer>() {
                @Override
                public int compare(Integer i1, Integer i2) {
                    return i1 > i2 ? -1 : 1;
                }
            });
            this.scores.remove(this.scores.size() - 1);
        }
        for (int i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i) == score) {
                pos = i + 1;
            }
        }

        saveToFile();
        return pos;
    }

    private void load() {
        this.scores = new ArrayList<Integer>();

        try {
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery("SELECT score FROM scores");
            while (this.resultSet.next()) {
                this.scores.add(this.resultSet.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        if (this.scores == null) {
            throw new RuntimeException("Nothing to save");
        }

        try {
            this.preparedStatement =
                    this.connection.prepareStatement("UPDATE scores SET 'score' = ? WHERE id = ?");
            for (int i = 0; i < this.scores.size(); i++) {
                this.preparedStatement.setInt(1, this.scores.get(i));
                this.preparedStatement.setInt(2, i + 1);
                this.preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (statement != null) this.statement.close();
            if (preparedStatement != null) this.preparedStatement.close();
            if (resultSet != null) this.resultSet.close();
            if (connection != null) this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
