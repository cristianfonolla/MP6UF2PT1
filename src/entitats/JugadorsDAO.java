/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

/**
 *
 * @author cristian
 */
public class JugadorsDAO {

    private Session sesion;
    private Transaction tx;

    public long guardaJugador(Jugador jugador) throws HibernateException {
        long id = 0;

        try {
            iniciaOperacio();
            id = (Long) sesion.save(jugador);
            tx.commit();
        } catch (HibernateException he) {
            tractaExcepcio(he);
            throw he;
        } finally {
            sesion.close();
        }

        return id;
    }

    public void actualitzaJugador(Jugador jugador) throws HibernateException {
        try {
            iniciaOperacio();
            sesion.update(jugador);
            tx.commit();
        } catch (HibernateException he) {
            tractaExcepcio(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public void eliminaJugador(Jugador jugador) throws HibernateException {
        try {
            iniciaOperacio();
            sesion.delete(jugador);
            tx.commit();
        } catch (HibernateException he) {
            tractaExcepcio(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public Jugador obtenJugador(long idJugador) throws HibernateException {
        Jugador jugador = null;
        try {
            iniciaOperacio();
            jugador = (Jugador) sesion.get(Jugador.class, idJugador);
        } finally {
            sesion.close();
        }

        return jugador;
    }

    public List obtenLlistaJugador() throws HibernateException {

        List llistaJugador = null;

        try {
            iniciaOperacio();
            llistaJugador = sesion.createQuery("from Jugador").list();
        } finally {
            sesion.close();
        }

        return llistaJugador;
    }

    private void iniciaOperacio() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    private void tractaExcepcio(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Error a la capa d'accés a dades", he);
    }

}

//    Jugador jugador;
//
//    public Entitat() {
//        
//    }
//
//    private void crearJugador(String nom, String email, String telefon) {
//        Session session;
//        Transaction tx;
//
//        session = HibernateUtil.getSessionFactory().openSession();
//        tx = session.beginTransaction();
//
//        //Jugador j = new Jugador(nom, email, telefon);
//
//        //session.save(j);
//
//        tx.commit();
//        session.close();
//        System.exit(0);
//    }

