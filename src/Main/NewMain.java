/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.Controlador;
import Vista.VistaEquips;
import Vista.VistaGeneral;
import Vista.VistaJugadors;
import Vista.VistaTorneig;
import entitats.Model;

/**
 *
 * @author cristian
 */
public class NewMain {

    static Model model = new Model();
    static VistaJugadors vista = new VistaJugadors();
    static VistaGeneral vistaGeneral = new VistaGeneral();
    static VistaEquips vistaEquips = new VistaEquips();
    static VistaTorneig vistaTorneig = new VistaTorneig();

    public static void main(String[] args) {
        vista.setVisible(false);
        vistaEquips.setVisible(false);
        vistaTorneig.setVisible(false);
        vistaGeneral.setVisible(true);
        new Controlador(model, vista, vistaGeneral, vistaEquips, vistaTorneig);
    }

    private static void inicivariables() {
//
//        ClasseDAO jugadorDAO = new ClasseDAO(Jugador.class);
//        Jugador contacteRecuperat = null;
//        long idAEliminar = 0;
//
////Creem tres instàncies de Contacte 
//        Jugador contacte1 = new Jugador("Contacte 1", "contacte1@contacte.com", "12345678");
//        Jugador contacte2 = new Jugador("Contacte 2", "contacte2@contacte.com", "87654321");
//        Jugador contacte3 = new Jugador("Contacte 3", "contacte3@contacte.com", "45612378");
//
////Guardem les tres instàncies, i copiem l'id del contacte1 per usar-lo posteriorment 
//        idAEliminar = jugadorDAO.guarda(contacte1);
//        jugadorDAO.guarda(contacte2);
//        jugadorDAO.guarda(contacte3);
//
////Modifiquem el contacte 2 i l'actualitzem 
//        contacte2.setNom("Nou Contacte 2");
//        jugadorDAO.actualitza(contacte2);
//
////Recuperem el contacte1 de la base de dades 
//        contacteRecuperat = (Jugador) jugadorDAO.obte(idAEliminar);
//        //System.out.println("Recuperem a " + contacteRecuperat.getNom());
//
////Eliminem al contacteRecuperat (que és el contacte3) 
//        jugadorDAO.elimina(contacteRecuperat);
//
////Obtenim la llista de contactes que queden a la base de dades i la mostrem 
//        List<Jugador> llistaContactes = jugadorDAO.obtenLlista();
//        System.out.println("Hi ha " + llistaContactes.size() + "contactes a la base de dades.");
//
//        for (Jugador c : llistaContactes) {
//            System.out.println("-> " + c.getNom());
//        }
//
//        System.exit(0);

    }
}
