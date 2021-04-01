package dto;

import entity.New;


public class NewsData {

    private New aNew;

    private String typeName;

    private String username;


    public NewsData() {
    }

    public NewsData(New aNew, String typeName, String username) {
        this.aNew = aNew;
        this.typeName = typeName;
        this.username = username;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "aNew=" + aNew +
                ", typeName='" + typeName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public New getaNew() {
        return aNew;
    }

    public void setaNew(New aNew) {
        this.aNew = aNew;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
