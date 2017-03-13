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
@Table(name = "Equips")
public class Equip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEquip;

    private String nomEquip;

    private int classificacio;
    @Transient
    private Jugador capita;
    @Transient
    private ArrayList<Jugador> jugadors = new ArrayList<>();
    @Transient
    private ArrayList<Torneig> tornejos = new ArrayList<>();

    public Equip() {
    }

    public Equip(String nomEquip, int classificacio, Jugador capita) {
        this.nomEquip = nomEquip;
        this.classificacio = classificacio;
        this.capita = capita;
    }

    public String getNomEquip() {
        return nomEquip;
    }

    public void setNomEquip(String nomEquip) {
        this.nomEquip = nomEquip;
    }

    public int getClassificacio() {
        return classificacio;
    }

    public void setClassificacio(int classificacio) {
        this.classificacio = classificacio;
    }

    public Jugador getCapita() {
        return capita;
    }

    public void setCapita(Jugador capita) {
        this.capita = capita;
    }

    public ArrayList<Jugador> getJugadors() {
        return jugadors;
    }

    public void setJugadors(ArrayList<Jugador> jugadors) {
        this.jugadors = jugadors;
    }

    public ArrayList<Torneig> getTornejos() {
        return tornejos;
    }

    public void setTornejos(ArrayList<Torneig> tornejos) {
        this.tornejos = tornejos;
    }

    @Override
    public String toString() {
        return "Equip{" + "idEquip=" + idEquip + ", nomEquip=" + nomEquip + ", Classificacio=" + classificacio + ", capita=" + capita + ", jugadors=" + jugadors + ", tornejos=" + tornejos + '}';
    }

}
