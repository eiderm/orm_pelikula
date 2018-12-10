package View;

import Controller.GestionListaEnMemoria;
import Controller.ModTableCell;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javax.xml.parsers.ParserConfigurationException;
import Model.Pelikula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import org.xml.sax.SAXException;

/**
 *
 * @author Eider
 */
public class MainWindow extends Application {

    private final TableView<Pelikula> table = new TableView<>();
    final HBox hb = new HBox();
    private GestionListaEnMemoria mk;
    private ObservableList<Pelikula> data;
    private Label label;
    private Scene scene;
    private Scene colorScene;

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, SAXException, ParserConfigurationException, SQLException {
        mk = new GestionListaEnMemoria();
        //data ObservableList-a hutsik inizializatzen dugu
        data = mk.peliKargatu();

        Scene scene = new Scene(new Group());

        stage.setTitle("Pelikulak");
        stage.setWidth(880);
        stage.setHeight(520);
        final Label label = new Label("Pelikula");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        Callback<TableColumn<Pelikula, String>, TableCell<Pelikula, String>> comboBoxCellFactory
                = (TableColumn<Pelikula, String> param) -> new ModTableCell();

        TableColumn<Pelikula, String> izenaCol = new TableColumn<>("Izena");
        izenaCol.setMinWidth(130);
        izenaCol.setCellValueFactory(new PropertyValueFactory<>("izena"));
        izenaCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        izenaCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setIzena(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });

        TableColumn<Pelikula, String> direkCol = new TableColumn<>("Direktorea");
        direkCol.setMinWidth(130);
        direkCol.setCellValueFactory(new PropertyValueFactory<>("direktorea"));
        direkCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        direkCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDirektorea(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });

        TableColumn<Pelikula, Integer> urteCol = new TableColumn<>("Urtea");
        urteCol.setMinWidth(100);
        urteCol.setCellValueFactory(new PropertyValueFactory<>("urtea"));
        urteCol.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter()));
        urteCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setUrtea(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });

        TableColumn<Pelikula, String> generoCol = new TableColumn<>("Generoa");
        generoCol.setMinWidth(150);
        generoCol.setCellValueFactory(new PropertyValueFactory<>("generoa"));
        generoCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        generoCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setGeneroa(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });

        TableColumn<Pelikula, String> ikusCol = new TableColumn<>("Ikusita");
        ikusCol.setMinWidth(100);
        ikusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIkusita()));
        ikusCol.setCellFactory(comboBoxCellFactory);
        ikusCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIkusita(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });

        table.setPadding(new Insets(0, 0, 0, 0));
        table.setItems(data);
        table.getColumns().addAll(izenaCol, direkCol, urteCol, generoCol, ikusCol);

        final TextField addIzena = new TextField();
        addIzena.setPromptText("Izena");
        addIzena.setMaxWidth(izenaCol.getPrefWidth());

        final TextField addDirektorea = new TextField();
        addDirektorea.setMaxWidth(direkCol.getPrefWidth());
        addDirektorea.setPromptText("Direktorea");

        final NumberTextField addUrtea = new NumberTextField();
        addUrtea.setMaxWidth(urteCol.getPrefWidth());
        addUrtea.setPromptText("Urtea");

        final TextField addGeneroa = new TextField();
        addGeneroa.setMaxWidth(generoCol.getPrefWidth());
        addGeneroa.setPromptText("Generoa");

        final ComboBox addIkusita = new ComboBox(FXCollections.observableList(GestionListaEnMemoria.cargarDatosIkusita()));
        addIkusita.setMaxWidth(100);
        addIkusita.setPromptText("Ikusita");

        Button addButton = new Button("Gehitu");
        addButton.setOnAction((ActionEvent e)
                -> {

            if ("".equals(addIzena.getText()) || "".equals(addDirektorea.getText()) || addIkusita.getSelectionModel().isEmpty() || addUrtea.getText() == "" || addGeneroa.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ezin da gehitu!");
                alert.setContentText("Ezin da daturik gorde taulan guztiak bete barik.");
                alert.showAndWait();
            } else {
                Pelikula p = new Pelikula(addIzena.getText(), addDirektorea.getText(), Integer.parseInt(addUrtea.getText()), addGeneroa.getText(), addIkusita.getSelectionModel().getSelectedItem().toString());
                data.add(p);
                mk.guardarDatos(p);
                addIzena.clear();
                addDirektorea.clear();
                addUrtea.clear();
                addGeneroa.clear();
                addIkusita.getSelectionModel().clearSelection();
            }
        });

        //objetua ezabatzeko aukera
        Button removeButton = new Button("Ezabatu");
        removeButton.setOnAction((ActionEvent e) -> {
            Pelikula peli = table.getSelectionModel().getSelectedItem();
            data.remove(peli);
            mk.peliEzabatu(peli);
        });

        //kontenedore honetan, behean dauden textua idazteko kanpoak eta botoiak sartuko ditugu
        hb.getChildren().addAll(addIzena, addDirektorea, addUrtea, addGeneroa, addIkusita, addButton, removeButton);
        hb.setPadding(new Insets(10, 0, 0, 0));
        hb.setSpacing(5);

        //kontenedore honetan berriz lehen sortutako labela, taula eta goian sortu dugun kontenedorea sartuko ditugu
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
