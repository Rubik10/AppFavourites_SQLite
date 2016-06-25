package com.rubik.applogincard.model;

/**
 * Created by Rubik on 22/6/16.
 */
public class Images {

    private int idImage;
    private String name;
    private String url;
    private int idCategory;


    //Constructor
    public Images (){}
    public Images (int id) {this.idImage = id;}

    public Images(int id, String _name, String _url, int idCat) {
        this.idImage=id;
        this.name = _name;
        this.url = _url;
        this.idCategory = idCat;
    }

    public Images(String _name, String _url, int idCat) {
        this.name = _name;
        this.url = _url;
        this.idCategory = idCat;
        //  cat.setIdCategory(idCat);
    }

    public Images(int id , String _name, String _url) {
        this.idImage=id;
        this.name = _name;
        this.url = _url;
    }

                /* GETTERS & SETTERS */

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

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

       /* public void setCat(Categories cat) {
            this.cat = cat;
        }

        public Categories getCat() {

            return cat;
        }*/

    public void setCat(int cat) {
        this.idCategory = cat;
    }

    public int getCat() {
        return idCategory;
    }
}

