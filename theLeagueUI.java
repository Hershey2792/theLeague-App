
/*
 * Class: CMSC214
 * Instructor:
 * Description: (Give a brief description for each Program)
 * Due: 04/14/21
 * Environment: (OS, Java version, IDE)
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any student.
   Print your Name here: Quentin Jefferies
 */


package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;


public class theLeagueUI extends Application {


    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {

        //create pane and scene
        BorderPane pane = new BorderPane();//need border pane to set car and buttons

        //create grid pane object
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Create Scene controls
        Text sceneTitle = new Text("theLeague App");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);


        //create Button to submit
        Button btnSubmit = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().addAll(btnSubmit);
        grid.add(hbBtn, 1, 3);

        //create TextArea for output queries on bottom of GUI
        TextArea outputText = new TextArea();
        outputText.setMinHeight(450);
        outputText.setMinWidth(400);
        outputText.setStyle("-fx-font-size: 14");
        grid.add(outputText, 1, 5);


         /*create an statistics label that includes a textbox
          the textbox will have a drop down menu with a list of stats
          */
        Label stats = new Label("Statistics:");
        grid.add(stats, 0, 2);
        ComboBox<String> statsBox = new ComboBox<>();
        statsBox.getItems().addAll("Points", "Rebounds", "Assists", "FG%", "Blocks", "Steals",
                "Minutes Played", "Select All");
        HBox box = new HBox(statsBox);
        statsBox.setEditable(true);
        grid.add(box, 1, 2);



        /*create an alltime rank textbox to put their rank on the
        alltime list inside  */
        TextArea allTimeRank = new TextArea();
        allTimeRank.setMinWidth(200);
        allTimeRank.setMinHeight(100);
        allTimeRank.setStyle("-fx-font-size: 20");
        HBox box1 = new HBox(allTimeRank);
        box1.relocate(400, 40);
        pane.getChildren().add(box1);



        /*create player name label and text field
        and create a textfield for player names with a
        drop down menu */
        Label playerName = new Label("Player Name:");
        grid.add(playerName, 0, 1);


        //create player textfield for player name and drop down menu for players in dataabase
        TextField playerTextField = new TextField();
        playerTextField.setMaxWidth(200);
        ComboBox<String> playerNames = new ComboBox();
        playerText(grid, playerNames);


        /* query event handler
        query includes points, rebounds, assists, steals, FG%
         */
        btnSubmit.setOnAction(action -> {
            try {
                outputText.clear();
                points(statsBox, playerNames, outputText, allTimeRank);
                rebounds(statsBox, playerNames, outputText, allTimeRank);
                assists(statsBox, playerNames, outputText, allTimeRank);
                fieldGoalPct(statsBox, playerNames, outputText, allTimeRank);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        //Add them to root (BorderPane)
        pane.setCenter(grid);

        pane.setStyle("-fx-background-color: white;");


        //create scene
        Scene scene = new Scene(pane, 720, 650);
        primaryStage.setTitle("theLeague");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void createPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
    }

    /*
    Opens the connection to the mysql database to get a
    drop down menu for all of the players in nba history
     */
    public void playerText(GridPane grid, ComboBox playerNames) {
        try {

            String query = "SELECT player FROM all_time_stats ORDER BY player ASC";
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost/All_Players", "scott", "tiger");
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery(query);

            while (rs.next()) {

                playerNames.getItems().addAll(rs.getString("player"));


            }
            HBox box1 = new HBox(playerNames);
            playerNames.setEditable(true);
            grid.add(box1, 1, 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /*
    Query the total points and all time points rank
    for the player selected. adds the scoring rank to
    the text areas
     */
    public void points(ComboBox<String> statsBox, ComboBox<String> playerNames, TextArea outputText, TextArea allTimeRank)
            throws SQLException, ClassNotFoundException {

        PlayerStatsQuery pointsStats = new PlayerStatsQuery();
        if (statsBox.getValue().equals("Points")) {

            ArrayList<String> list;
            ArrayList<String> list2;
            ArrayList<String> list3;
            list = pointsStats.getPoints(playerNames.getValue());
            list2 = pointsStats.getTotal(playerNames.getValue());
            list3 = pointsStats.allTimePointsRank(playerNames.getValue());

            for (int i = 0; i < list.size(); i++) {
                outputText.appendText(list.get(i));
            }

            for (int i = 0; i < list2.size(); i++) {
                outputText.appendText(list2.get(i));
            }

            for (int i = 0; i < list3.size(); i++) {
                allTimeRank.setText(list3.get(i));
            }
        }


    }


    /*
    Query the total rebounds and all time points rank
    for the player selected. adds the rebound rank to
    the text areas
     */
    public void rebounds(ComboBox<String> statsBox, ComboBox<String> playerNames, TextArea outputText, TextArea allTimeRank)
            throws SQLException, ClassNotFoundException {

        PlayerStatsQuery pointsStats = new PlayerStatsQuery();
        if (statsBox.getValue().equals("Rebounds")) {

            ArrayList<String> list;
            ArrayList<String> list2;
            ArrayList<String> list3;
            list = pointsStats.getRebounds(playerNames.getValue());
            list2 = pointsStats.getTotalRebs(playerNames.getValue());
            list3 = pointsStats.allTimeReboundsRank(playerNames.getValue());


            for (int i = 0; i < list.size(); i++) {
                outputText.appendText(list.get(i));
            }

            for (int i = 0; i < list2.size(); i++) {
                outputText.appendText(list2.get(i));
            }
            for (int i = 0; i < list3.size(); i++) {
                allTimeRank.setText(list3.get(i));
            }

        }


    }


    /*
    Query the total assists and all time assits rank
    for the player selected. adds the assisted rank to
    the text areas
    */
    public void assists(ComboBox<String> statsBox, ComboBox<String> playerNames, TextArea outputText, TextArea allTimeRank)
            throws SQLException, ClassNotFoundException {

        PlayerStatsQuery pointsStats = new PlayerStatsQuery();
        if (statsBox.getValue().equals("Assists")) {

            ArrayList<String> list;
            ArrayList<String> list2;
            ArrayList<String> list3;
            list = pointsStats.getAssists(playerNames.getValue());
            list2 = pointsStats.getTotalAssists(playerNames.getValue());
            list3 = pointsStats.allTimeAssistsRank(playerNames.getValue());


            for (int i = 0; i < list.size(); i++) {
                outputText.appendText(list.get(i));
            }

            for (int i = 0; i < list2.size(); i++) {
                outputText.appendText(list2.get(i));
            }

            for (int i = 0; i < list3.size(); i++) {
                allTimeRank.setText(list3.get(i));
            }

        }
        pointsStats.closeConnection();


    }

    public void fieldGoalPct(ComboBox<String> statsBox, ComboBox<String> playerNames, TextArea outputText, TextArea allTimeRank)
            throws SQLException, ClassNotFoundException {

        PlayerStatsQuery pointsStats = new PlayerStatsQuery();
        if (statsBox.getValue().equals("FG%")) {

            ArrayList<String> list;
            ArrayList<String> list2;
            ArrayList<String> list3;
            list = pointsStats.getFieldGoal(playerNames.getValue());
            list2 = pointsStats.getTotalFieldGoal(playerNames.getValue());
            list3 = pointsStats.allTimeFGRank(playerNames.getValue());


            for (int i = 0; i < list.size(); i++) {
                outputText.appendText(list.get(i));
            }

            for (int i = 0; i < list2.size(); i++) {
                outputText.appendText(list2.get(i));
            }

            for (int i = 0; i < list3.size(); i++) {
                allTimeRank.setText(list3.get(i));
            }

        }


    }


}









