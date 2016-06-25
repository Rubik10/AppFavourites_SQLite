package com.rubik.applogincard.model;

/**
 * Created by Rubik on 19/6/16.
 */
public class Favorites {
    private int idFav;
    private Users user;
    private Images image;

    public Favorites () {}

    public Favorites (int idFav, Users _user, Images img) {
       this.idFav = idFav;
        this.user = _user;
        this.image=img;
    }

    public Favorites (Users _user, Images img) {
        this.user = _user;
        this.image=img;
    }



    public int getIdFav() {
        return idFav;
    }

    public void setIdFav(int idFav) {
        this.idFav = idFav;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }


}

