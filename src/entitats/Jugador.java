/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author cristian
 */
@Entity
@Table(name = "Jugadors")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long _1_id;

    private String _2_nom;

    private String _3_email;

    private String _4_telefon;

    //@Transient
    //private Equip _5_equip = new Equip();
    public Jugador() {
    }

    public Jugador(String _2_nom, String _3_email, String _4_telefon) {
        this._2_nom = _2_nom;
        this._3_email = _3_email;
        this._4_telefon = _4_telefon;
    }

    public long get1_id() {
        return _1_id;
    }

    public void set1_id(long _1_id) {
        this._1_id = _1_id;
    }

    public String get2_nom() {
        return _2_nom;
    }

    public void set2_nom(String _2_nom) {
        this._2_nom = _2_nom;
    }

    public String get3_email() {
        return _3_email;
    }

    public void set3_email(String _3_email) {
        this._3_email = _3_email;
    }

    public String get4_telefon() {
        return _4_telefon;
    }

    public void set4_telefon(String _4_telefon) {
        this._4_telefon = _4_telefon;
    }

//    public Equip get5_equip() {
//        return _5_equip;
//    }
//
//    public void set5_equip(Equip _5_equip) {
//        this._5_equip = _5_equip;
//    }
    @Override
    public String toString() {
        return "Jugador{" + "_1_id=" + _1_id + ", _2_nom=" + _2_nom + ", _3_email=" + _3_email + ", _4_telefon=" + _4_telefon + ", _5_equip=" + '}';
    }

}
