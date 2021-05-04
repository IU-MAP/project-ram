package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private NumberAxis Chart;

    @FXML
    private ToggleButton Compute;

    @FXML
    private AnchorPane Field;

    @FXML
    private TextField Tf;

    @FXML
    private TextField Ti;

    @FXML
    private LineChart<Number, Number> errorsChart;

    @FXML
    private LineChart<Number, Number> approxChart;

    @FXML
    private CheckBox isEuler;

    @FXML
    private CheckBox isExact;

    @FXML
    private CheckBox isImprovedEuler;

    @FXML
    private CheckBox isRungeKutta;

    @FXML
    private TextField maxX;

    @FXML
    private LineChart<Number, Number> methodsChart;

    @FXML
    private TextField n;

    @FXML
    private TextField x0;

    @FXML
    private TextField y0;

    @FXML
    void initialize() {
    }

    @FXML
    void computation() {
    }
}