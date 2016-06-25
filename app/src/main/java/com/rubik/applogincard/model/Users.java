package com.rubik.applogincard.model;

import java.sql.Timestamp;

/**
        * Created by Rubik on 19/6/16.
        * Model of User
     */
    public class Users {
        private int idUser;
        private String name;
        private String mail;
        private String pass;
        private Timestamp created_at;

        public Users () {}

        public Users (int id) {
            this.idUser=id;
        }

            //Register
        public Users (String _name, String _mail, String _pass) {
            this.name=_name;
            this.mail=_mail;
            this.pass=_pass;
        }

        public Users ( String _mail, String _pass) {
            this.mail=_mail;
            this.pass=_pass;
        }

        public Users (int id, String _name, String _mail, String _pass) {
            this.idUser=id;
            this.name=_name;
            this.mail=_mail;
            this.pass=_pass;
        }


        public int getIdUser() {
            return idUser;
        }

        public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public Timestamp getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Timestamp created_at) {
            this.created_at = created_at;
        }
    }
