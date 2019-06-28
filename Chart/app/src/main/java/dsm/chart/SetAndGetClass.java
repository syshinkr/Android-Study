package dsm.chart;

/**
 * Created by admin on 2017-10-10.
 */
/** 지은이 코드 */
public class SetAndGetClass {

    private final static SetAndGetClass setAndGetClass = new SetAndGetClass();

    private SetAndGetClass(){}

    private BluetoothControl bluetoothControl;

    public static SetAndGetClass getInstance(){
        return setAndGetClass;
    }

    public BluetoothControl getBlutoothControl() {
        return bluetoothControl;
    }

    public void setBlutoothControl(BluetoothControl blutoothControl) {
        this.bluetoothControl = bluetoothControl;
    }
}
