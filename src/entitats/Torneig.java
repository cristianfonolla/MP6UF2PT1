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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author cristian
 */
@Entity
@Table(name = "Tornejos")
public class Torneig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long _1_idTorneig;

    private String _2_nomTorneig;

    @ManyToMany(mappedBy = "_6_tornejos")
    private Collection<Equip> _3_Equips = new ArrayList<>();

    public Torneig() {
    }

    public Torneig(String _2_nomTorneig) {
        this._2_nomTorneig = _2_nomTorneig;
    }

    public long get1_idTorneig() {
        return _1_idTorneig;
    }

    public void set1_idTorneig(long _1_idTorneig) {
        this._1_idTorneig = _1_idTorneig;
    }

    public String get2_nomTorneig() {
        return _2_nomTorneig;
    }

    public void set2_nomTorneig(String _2_nomTorneig) {
        this._2_nomTorneig = _2_nomTorneig;
    }

    public Collection<Equip> get3_Equips() {
        return _3_Equips;
    }

    public void set3_Equips(Collection<Equip> _3_Equips) {
        this._3_Equips = _3_Equips;
    }

    @Override
    public String toString() {
        return _2_nomTorneig;
    }

}
