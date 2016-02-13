package Main;

import javafx.scene.paint.Paint;

import java.util.Properties;

/**
 * Created by rhondusmithwick on 2/12/16.
 *
 * @author Rhondu Smithwick
 */
public class CellSocietyProperties extends Properties {
    public CellSocietyProperties() {
        super();
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(get(key));
    }

    public Paint getPaint(String key) {
        return Paint.valueOf(get(key));
    }

    public String get(String key) {
        return getProperty(key);
    }

    public void put(String key, Object value) {
        setProperty(key, value.toString());
    }

}