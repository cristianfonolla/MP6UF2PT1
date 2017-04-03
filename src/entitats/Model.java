/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import org.hibernate.Session;
import utils.HibernateUtil;

/**
 *
 * @author alumne
 */
public class Model {

    private static Session sesio = HibernateUtil.getSessionFactory().openSession();
    private ClasseDAO<Jugador> getClasseDAOJugadors = new ClasseDAO<>(Jugador.class, sesio);
    private ClasseDAO<Equip> classeDAOEquips = new ClasseDAO<>(Equip.class, sesio);
    private ClasseDAO<Torneig> classeDAOTorneig = new ClasseDAO<>(Torneig.class, sesio);

    public ClasseDAO<Equip> getClasseDAOEquips() {
        return classeDAOEquips;
    }

    public ClasseDAO<Jugador> getClasseDAOJugadors() {
        return getClasseDAOJugadors;
    }

    public ClasseDAO<Torneig> getClasseDAOTorneig() {
        return classeDAOTorneig;
    }

    public void tancaSessio() {
        System.out.println("Tancant sessio, adeu!");
        sesio.close();
    }

}
