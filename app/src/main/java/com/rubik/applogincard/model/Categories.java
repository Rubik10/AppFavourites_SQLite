package com.rubik.applogincard.model;

    /**
     * Created by Rubik on 22/6/16.
     */
    public class Categories {

        private int idCategory;
        private String category;

        public Categories () {}

        public Categories (int id) {
            this.idCategory = id;
        }

        public Categories (int id, String cat) {
            this.idCategory = id;
            category=cat;
        }

        public Categories (String cat) {
            category=cat;
        }

        public int getIdCategory() {
            return idCategory;
        }

        public void setIdCategory(int idCategory) {
            this.idCategory = idCategory;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

    }

