package nantha91.com.simpleapp.model;

/**
 * Created by nantha on 25/4/15.
 */
public class Hospital {

    public static final String TABLE_HOSPITAL = "Hospital";

    public static final String NAME = "name";
    public static final String SERVICES = "services";
    public static final String MOBILE = "mobileno";
    public static final String WEBSTIE = "website";

    private String name,service,website,mobileno;

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getName() {
        return name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getService() {
        return service;
    }

    public String getWebsite() {
        return website;
    }
}
