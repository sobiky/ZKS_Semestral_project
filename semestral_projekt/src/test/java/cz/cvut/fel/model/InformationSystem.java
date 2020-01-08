package cz.cvut.fel.model;

public class InformationSystem {
    private String name;
    private String url;
    private String description;
    private String admName;
    private String admEmail;
    private String admPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdmName() {
        return admName;
    }

    public void setAdmName(String admName) {
        this.admName = admName;
    }

    public String getAdmEmail() {
        return admEmail;
    }

    public void setAdmEmail(String admEmail) {
        this.admEmail = admEmail;
    }

    public String getAdmPhone() {
        return admPhone;
    }

    public void setAdmPhone(String admPhone) {
        this.admPhone = admPhone;
    }
}
