package personal.project.sampledb;

/**
 * Created by admin on 2017-12-18.
 */

public class Info {
    public int _id;
    public String name;
    public String contact;
    public String email;

    public Info(){}

    public Info(int _id, String name, String contact, String email) {
        this._id = _id;
        this.name = name;
        this.contact = contact;
        this.email = email;
    }
}
