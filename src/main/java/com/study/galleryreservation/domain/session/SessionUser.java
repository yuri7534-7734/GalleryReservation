package com.study.galleryreservation.domain.session;

import com.study.galleryreservation.domain.session.SnsUser;
import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(SnsUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPicture() { return picture; }
}
