package models;

public class Password {
    private String title;
    private String password;
    private String url;
    private String comment;

    public Password(String title, String password, String url, String comment) {
        this.title = title;
        this.password = password;
        this.url = url;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Password{" +
                "title='" + title + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
