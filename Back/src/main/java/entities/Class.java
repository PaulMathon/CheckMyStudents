package entities;

public class Class {

    private int idClass;
    private String domainName;
    private String pictureUrl;

    public Class(int idClass, String domainName, String pictureUrl) {
        this.idClass = idClass;
        this.domainName = domainName;
        this.pictureUrl = pictureUrl;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
