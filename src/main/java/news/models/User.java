package news.models;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Petar on 8/19/2017.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String e_mail;
    private String medias;
    private Date subscription;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getMedias() {
        return medias;
    }

    public void setMedias(String medias) {
        this.medias = medias;
    }

    public Date getSubscription() {
        return subscription;
    }

    public void setSubscription(Date subscription) {
        this.subscription = subscription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
