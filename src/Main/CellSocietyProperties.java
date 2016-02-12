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
        return (int) get(key);
    }

    public double getDouble(String key) {
        return (double) get(key);
    }

    public Paint getPaint(String key) {
        return Paint.valueOf((String) get(key));
    }

    public String getString(String key) {
        return (String) get(key);
    }
}
