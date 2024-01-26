package controllers;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.entitys.PunctualBean;

/**
 *
 * @author Ian.
 */
public class DatePickerCellPunctual extends TableCell<PunctualBean, Date> {

    private DatePicker datePicker;

    DatePickerCellPunctual() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.setOnAction((e) -> {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);
        //The pattern for the date format should be read from a properties´ file
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(datePicker);
            } else if (item != null) {
                String date = dateFormatter.format(item);
                setText(date);
                setGraphic(null);
            }
        }
    }

    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
    }
}