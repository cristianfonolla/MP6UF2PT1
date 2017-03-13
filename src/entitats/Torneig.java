/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import java.util.ArrayList;
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
@Table(name = "Tornejos")
public class Torneig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTorneig;

    private String nomTorneig;

    @Transient
    private ArrayList<Equip> Equips = new ArrayList<>();

    public Torneig() {
    }

    public Torneig(String nomTorneig) {
        this.nomTorneig = nomTorneig;
    }

    public String getNomTorneig() {
        return nomTorneig;
    }

    public void setNomTorneig(String nomTorneig) {
        this.nomTorneig = nomTorneig;
    }

    public ArrayList<Equip> getEquips() {
        return Equips;
    }

    public void setEquips(ArrayList<Equip> Equips) {
        this.Equips = Equips;
    }

    @Override
    public String toString() {
        return "Torneig{" + "idTorneig=" + idTorneig + ", nomTorneig=" + nomTorneig + ", Equips=" + Equips + '}';
    }

}
