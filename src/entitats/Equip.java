/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author cristian
 */
@Entity
@Table(name = "Equips")
public class Equip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long _1_idEquip;

    private String _2_nomEquip;

    private int _3_classificacio;
    @OneToOne(optional = true)
    private Jugador _4_capita;

    @OneToMany(mappedBy = "_5_equip")
    private Collection<Jugador> _5_jugadors = new ArrayList<>();
//    @Transient
//    private ArrayList<Torneig> tornejos = new ArrayList<>();

    public Equip() {
    }

    public Equip(String _2_nomEquip) {
        this._2_nomEquip = _2_nomEquip;
    }

    public Equip(String _2_nomEquip, int _3_classificacio) {
        this._2_nomEquip = _2_nomEquip;
        this._3_classificacio = _3_classificacio;
    }

    public Equip(String _2_nomEquip, int _3_classificacio, Jugador _4_capita) {
        this._2_nomEquip = _2_nomEquip;
        this._3_classificacio = _3_classificacio;
        this._4_capita = _4_capita;
    }

    public long get1_idEquip() {
        return _1_idEquip;
    }

    public void set1_idEquip(long _1_idEquip) {
        this._1_idEquip = _1_idEquip;
    }

    public String get2_nomEquip() {
        return _2_nomEquip;
    }

    public void set2_nomEquip(String _2_nomEquip) {
        this._2_nomEquip = _2_nomEquip;
    }

    public int get3_classificacio() {
        return _3_classificacio;
    }

    public void set3_classificacio(int _3_classificacio) {
        this._3_classificacio = _3_classificacio;
    }

    public Jugador get4_capita() {
        return _4_capita;
    }

    public void set4_capita(Jugador _4_capita) {
        this._4_capita = _4_capita;
    }

    public Collection<Jugador> get5_jugadors() {
        return _5_jugadors;
    }

    public void set5_jugadors(ArrayList<Jugador> _5_jugadors) {
        this._5_jugadors = _5_jugadors;
    }

    @Override
    public String toString() {
        return _2_nomEquip;
    }

}
