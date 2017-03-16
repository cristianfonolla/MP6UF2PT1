/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Vista;
import entitats.ClasseDAO;
import entitats.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author cristian
 */
public class Controlador {

    ClasseDAO modelJugador;
    Vista vista;
    private int filasel = -1;
    private String nomJugador;
    private String emailJugador;
    private String telefonJugador;
    private TableColumn tableColumn;

    public Controlador(ClasseDAO modelJugador, Vista vista) {
        this.modelJugador = modelJugador;
        this.vista = vista;
        tableColumn = carregaTaula((ArrayList) modelJugador.obtenLlista(), vista.getjTable1(), Jugador.class);
        control();

    }

    private void control() {

        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                //Modificar
                if (actionEvent.getSource().equals(vista.getjButton1())) {

                    TableColumnModel tcm = vista.getjTable1().getColumnModel();
                    tcm.addColumn(tableColumn);
                    Jugador j1 = (Jugador) vista.getjTable1().getValueAt(vista.getjTable1().getSelectedRow(), vista.getjTable1().getColumnCount() - 1);
                    j1.set2_nom(vista.getjTextField1().getText());
                    j1.set3_email(vista.getjTextField2().getText());
                    j1.set4_telefon(vista.getjTextField3().getText());
                    modelJugador.actualitza(j1);
                    carregaTaula((ArrayList) modelJugador.obtenLlista(), vista.getjTable1(), Jugador.class);
                    llimpiarCamps();

                }
                //Crear
                if (actionEvent.getSource().equals(vista.getjButton2())) {

                    Jugador jugador = new Jugador(vista.getjTextField1().getText(), vista.getjTextField2().getText(), vista.getjTextField3().getText());
                    modelJugador.guarda(jugador);
                    carregaTaula((ArrayList) modelJugador.obtenLlista(), vista.getjTable1(), Jugador.class);
                    llimpiarCamps();

                }
                //Borrar
                if (actionEvent.getSource().equals(vista.getjButton3())) {
                    TableColumnModel tcm = vista.getjTable1().getColumnModel();
                    tcm.addColumn(tableColumn);
                    modelJugador.elimina(vista.getjTable1().getValueAt(vista.getjTable1().getSelectedRow(), vista.getjTable1().getColumnCount() - 1));
                    tcm.removeColumn(tableColumn);
                    carregaTaula((ArrayList) modelJugador.obtenLlista(), vista.getjTable1(), Jugador.class);
                    llimpiarCamps();

                }
                //Sortir
                if (actionEvent.getSource().equals(vista.getjButton4())) {
                    System.exit(0);
                }

            }

        };
        vista.getjButton1().addActionListener(actionListener);
        vista.getjButton2().addActionListener(actionListener);
        vista.getjButton3().addActionListener(actionListener);
        vista.getjButton4().addActionListener(actionListener);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                if (e.getSource().equals(vista.getjTable1())) {
                    try {
                        filasel = vista.getjTable1().getSelectedRow();
                        if (filasel != -1) {
                            nomJugador = (String) vista.getjTable1().getValueAt(filasel, 1);
                            vista.getjTextField1().setText(nomJugador);
                            emailJugador = (String) vista.getjTable1().getValueAt(filasel, 2);
                            vista.getjTextField2().setText(emailJugador);
                            telefonJugador = (String) vista.getjTable1().getValueAt(filasel, 3);
                            vista.getjTextField3().setText((telefonJugador));
                        }
                    } catch (NumberFormatException ex) {
                    }
                }

            }

        };
        vista.getjTable1().addMouseListener(mouseAdapter);

    }

    public TableColumn carregaTaula(ArrayList resultSet, JTable taula, Class<?> classe) {

        //variables locals
        Vector columnNames = new Vector();
        Vector data = new Vector();
        //Per poder actualitzar la BD des de la taula usaríem el model comentat
        //ModelCanvisBD model;
        DefaultTableModel model;

        //Anotem el nº de camps de la classe
        Field[] camps = classe.getDeclaredFields();
        //Ordenem els camps alfabèticament
        Arrays.sort(camps, new OrdenarCampClasseAlfabeticament());
        int ncamps = camps.length;
        //Recorrem els camps de la classe i posem els seus noms com a columnes de la taula
        //Com hem hagut de posar _numero_ davant el nom dels camps, mostrem el nom a partir de la 4ª lletra 
        for (Field f : camps) {
            columnNames.addElement(f.getName().substring(3));
        }
        //Afegixo al model de la taula una columna on guardaré l'objecte mostrat a cada fila (amago la columna al final per a que no aparegue a la vista)
        columnNames.addElement("objecte");
        //Si hi ha algun element a l'arraylist omplim la taula
        if (resultSet.size() != 0) {

            //Guardem els descriptors de mètode que ens interessen (els getters), més una columna per guardar l'objecte sencer
            Vector<Method> methods = new Vector(ncamps + 1);
            try {

                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(classe).getPropertyDescriptors();
                Arrays.sort(descriptors, new OrdenarMetodeClasseAlfabeticament());
                for (PropertyDescriptor pD : descriptors) {
                    Method m = pD.getReadMethod();
                    if (m != null & !m.getName().equals("getClass")) {
                        methods.addElement(m);
                    }
                }

            } catch (IntrospectionException ex) {
                //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Object m : resultSet) {
                Vector row = new Vector(ncamps + 1);

                for (Method mD : methods) {
                    try {
                        row.addElement(mD.invoke(m));
                    } catch (IllegalAccessException ex) {
                        //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Aquí guardo l'objecte sencer a la darrera columna
                row.addElement(m);
                //Finalment afegixo la fila a les dades
                data.addElement(row);
            }
        }

        //Utilitzem el model que permet actualitzar la BD des de la taula
        //model = new ModelCanvisBD(data, columnNames, Model.getConnexio(), columnNames.size() - 1);
        model = new DefaultTableModel(data, columnNames);
        taula.setModel(model);

        //Borro la darrera columna per a que no aparegue a la vista, però abans la guardo en una variable que al final serà el que retorna el mètode
        TableColumnModel tcm = taula.getColumnModel();
        TableColumn columna = tcm.getColumn(tcm.getColumnCount() - 1);
        tcm.removeColumn(columna);

        //Fixo l'amplada de les columnes que sí es mostren
        TableColumn column;
        for (int i = 0; i < taula.getColumnCount(); i++) {
            column = taula.getColumnModel().getColumn(i);
            column.setMaxWidth(250);
        }

        return columna;

    }

    private void llimpiarCamps() {
        vista.getjTextField1().setText("");
        vista.getjTextField2().setText("");
        vista.getjTextField3().setText("");
    }

    public static class OrdenarMetodeClasseAlfabeticament implements Comparator {

        public int compare(Object o1, Object o2) {

            Method mo1 = ((PropertyDescriptor) o1).getReadMethod();
            Method mo2 = ((PropertyDescriptor) o2).getReadMethod();

            if (mo1 != null && mo2 != null) {
                return (int) mo1.getName().compareToIgnoreCase(mo2.getName());
            }
            if (mo1 == null) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static class OrdenarCampClasseAlfabeticament implements Comparator {

        public int compare(Object o1, Object o2) {
            return (int) (((Field) o1).getName().compareToIgnoreCase(((Field) o2).getName()));
        }
    }

}
