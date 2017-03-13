/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import entitats.Equip;
import entitats.Jugador;
import entitats.JugadorsDAO;
import entitats.Torneig;
import java.util.ArrayList;

/**
 *
 * @author cristian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        JugadorsDAO jugadorsDAO = new JugadorsDAO();
//        Jugador jugadorRecuperat = null;
//        long idAEliminar = 0;

        Jugador jugador1 = new Jugador("Cristian", "cris@gmail.com", "646585985");
        Equip equip1 = new Equip("Roquetes", 1, jugador1);        
        equip1.getJugadors().add(jugador1);
        ArrayList<Equip> alE = new ArrayList<>();
        alE.add(equip1);
        Torneig torneig1 = new Torneig("Campeonat de Catalunya");
        torneig1.setEquips(alE);

        System.out.println(jugador1);
        System.out.println(equip1);
        System.out.println(torneig1);

//        Jugador jugador2 = new Jugador("Pepito","pepito@gmail.com","646574125");
//        Jugador jugador3 = new Jugador("Pepita","pepito@gmail.com","658965874");
//Guardem les tres instàncies, i copiem l'id del jugador per usar-lo posteriorment 
//        idAEliminar = jugadorsDAO.guardaJugador(jugador1);
//        jugadorsDAO.guardaJugador(jugador2);
//        jugadorsDAO.guardaJugador(jugador3);
//
////Modifiquem el jugador 2 i l'actualitzem 
//        jugador2.setNom("Nou Jugador 2");
//        jugadorsDAO.actualitzaJugador(jugador2);
//
////Recuperem el jugador1 de la base de dades 
//        jugadorRecuperat = jugadorsDAO.obtenJugador(idAEliminar);
//        System.out.println("Recuperem a " + jugadorRecuperat.getNom());
//
////Eliminem al jugadorRecuperat (que és el jugador3) 
//        jugadorsDAO.eliminaJugador(jugadorRecuperat);
//
////Obtenim la llista de jugadors que queden a la base de dades i la mostrem 
//        List<Jugador> llistaJugadors = jugadorsDAO.obtenLlistaJugador();
//        System.out.println("Hi ha " + llistaJugadors.size() + "jugadors a la base de dades.");
//
//        for (Jugador c : llistaJugadors) {
//            System.out.println("-> " + c.getNom());
//        }
        System.exit(0);

    }

}
