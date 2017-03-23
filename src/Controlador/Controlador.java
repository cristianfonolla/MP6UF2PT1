/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.VistaEquips;
import Vista.VistaGeneral;
import Vista.VistaJugadors;
import entitats.Equip;
import entitats.Jugador;
import entitats.Model;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.hibernate.HibernateException;

/**
 *
 * @author cristian
 */
public class Controlador {

    Model model;
    VistaJugadors vista;
    VistaGeneral vistaGeneral;
    VistaEquips vistaEquips;
    private int filasel = -1;
    private String nomJugador;
    private String emailJugador;
    private String telefonJugador;
    private String nomEquip;
    private int classificacio;
    private TableColumn tableColumnJugador;
    private TableColumn tableColumnEquip;
    private boolean esCapita = false;

    public Controlador(Model model, VistaJugadors vista, VistaGeneral vistaGeneral, VistaEquips vistaEquips) {
        this.model = model;
        this.vista = vista;
        this.vistaGeneral = vistaGeneral;
        this.vistaEquips = vistaEquips;
        carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vistaEquips.getjComboBox1());
        tableColumnJugador = carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
        tableColumnEquip = carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);

        control();
    }

    private void control() {

        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                //Modificar
                if (actionEvent.getSource().equals(vista.getjButton1())) {

                    if (vista.getjTable1().getSelectedRow() != -1) {

                        try {

                            int numero = Integer.valueOf(vista.getjTextField3().getText());

                            if (numero >= 600000000 && numero <= 999999999) {
                                TableColumnModel tcm = vista.getjTable1().getColumnModel();
                                tcm.addColumn(tableColumnJugador);
                                Jugador j1 = (Jugador) vista.getjTable1().getValueAt(vista.getjTable1().getSelectedRow(), vista.getjTable1().getColumnCount() - 1);
                                j1.set2_nom(vista.getjTextField1().getText());
                                j1.set3_email(vista.getjTextField2().getText());
                                j1.set4_telefon(vista.getjTextField3().getText());
                                model.getClasseDAOJugadors().actualitza(j1);
                                carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
                                llimpiarCamps();
                            } else {
                                JOptionPane.showMessageDialog(null, "Introdueix un telefon correcte d'Espanya!!!");
                            }

                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Has d'introduir un telefon vàlid amb format de 9 digits XXXXXXXXX");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Has de seleccionar un Jugador");
                    }

                }
                //Crear
                if (actionEvent.getSource().equals(vista.getjButton2())) {

                    if (!vista.getjTextField1().getText().equals("") && !vista.getjTextField2().getText().equals("") && !vista.getjTextField3().getText().equals("")) {

                        try {
                            int numero = Integer.valueOf(vista.getjTextField3().getText());

                            if (numero >= 600000000 && numero <= 999999999) {
                                Jugador jugador = new Jugador(vista.getjTextField1().getText(), vista.getjTextField2().getText(), vista.getjTextField3().getText());
                                model.getClasseDAOJugadors().guarda(jugador);
                                carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
                                carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vistaEquips.getjComboBox1());
                                llimpiarCamps();
                            } else {
                                JOptionPane.showMessageDialog(null, "Introdueix un telefon correcte d'Espanya!!!");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Has d'introduir un telefon vàlid amb format de 9 digits XXXXXXXXX");
                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "Has de introduir tots els camps del Jugador");

                    }

                }
                //Borrar
                if (actionEvent.getSource().equals(vista.getjButton3())) {

                    if (vista.getjTable1().getSelectedRow() != -1) {
                        ArrayList al = (ArrayList) model.getClasseDAOEquips().obtenLlista();
                        Equip test;
                        TableColumnModel tcm = vista.getjTable1().getColumnModel();
                        tcm.addColumn(tableColumnJugador);
                        Jugador jugadorSel = (Jugador) vista.getjTable1().getValueAt(vista.getjTable1().getSelectedRow(), vista.getjTable1().getColumnCount() - 1);
                        tcm.removeColumn(tableColumnJugador);
                        esCapita = false;
                        for (int i = 0; !esCapita && i < al.size(); i++) {

                            test = (Equip) al.get(i);

                            if (test.get4_capita() == jugadorSel) {
                                esCapita = true;
                                System.out.println("Este es capita" + jugadorSel.get4_telefon());

                            }

                        }

                        if (!esCapita) {

                            try {

                                model.getClasseDAOJugadors().elimina(jugadorSel);

                                carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
                                carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vistaEquips.getjComboBox1());
                                llimpiarCamps();
                            } catch (Exception e) {
                                tcm = vista.getjTable1().getColumnModel();
                                tcm.removeColumn(tableColumnJugador);

                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "El jugador seleccionat és capità d'un equip, no es pot borrar, abortant");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Has de seleccionar un Jugador per borrar-lo!");
                    }

                }
                //Enrere Jugadors
                if (actionEvent.getSource().equals(vista.getjButton4())) {
                    vista.setVisible(false);
                }
                //Enrere Equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton4())) {
                    vistaEquips.setVisible(false);
                }
                //Boto refresh
                if (actionEvent.getSource().equals(vistaEquips.getjButton5())) {
                    carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vistaEquips.getjComboBox1());

                }
                //Crear Equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton2())) {

                    if (vistaEquips.getjTextField2().getText().equals("") || vistaEquips.getjTextField1().getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Has d'introduir tots els camps de l'Equip");
                    } else {
                        try {

                            boolean permis = true;
                            ArrayList<Equip> al = (ArrayList<Equip>) model.getClasseDAOEquips().obtenLlista();

                            for (Equip equip : al) {

                                if (equip.get4_capita().equals((Jugador) vistaEquips.getjComboBox1().getSelectedItem())) {
                                    permis = false;
                                    break;
                                } else {

                                }

                            }

                            if (permis) {
                                Equip equip = new Equip(vistaEquips.getjTextField1().getText(), Integer.valueOf(vistaEquips.getjTextField2().getText()), (Jugador) vistaEquips.getjComboBox1().getSelectedItem());

                                model.getClasseDAOEquips().guarda(equip);
                                carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                                vistaEquips.getjTextField1().setText("");
                                vistaEquips.getjTextField2().setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "El capita seleccionat ja és capità d'un equip, selecciona "
                                        + "un altre Jugador per ser capità");
                            }

                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Has d'introduir un numero al camp classificacio");
                        }
                    }

                }
                //Borrar Equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton3())) {
                    filasel = vistaEquips.getjTable1().getSelectedRow();

                    if (filasel != -1) {

                        TableColumnModel tcm = vistaEquips.getjTable1().getColumnModel();
                        tcm.addColumn(tableColumnEquip);
                        model.getClasseDAOEquips().elimina((Equip) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 1));
                        tcm.removeColumn(tableColumnEquip);
                        carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                        vistaEquips.getjTextField1().setText("");
                        vistaEquips.getjTextField2().setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Has de seleccionar un equip primer per esborrar-lo");
                    }

                }
                //Modificar equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton1())) {

                    filasel = vistaEquips.getjTable1().getSelectedRow();
                    if (filasel != -1) {
                        int permis = 1;

                        Jugador jCombo = (Jugador) vistaEquips.getjComboBox1().getSelectedItem();
                        Jugador jTaula = (Jugador) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 1);

                        if (jCombo.get2_nom().equals(jTaula.get2_nom())) {
                            permis = 2;
                        } else {
                            ArrayList<Equip> al = (ArrayList<Equip>) model.getClasseDAOEquips().obtenLlista();

                            for (Equip equip : al) {

                                if (equip.get4_capita().equals((Jugador) vistaEquips.getjComboBox1().getSelectedItem())) {
                                    permis = 0;
                                    break;
                                } else {

                                }

                            }
                        }
                        if (permis == 1) {
                            
                            TableColumnModel tcm = vistaEquips.getjTable1().getColumnModel();
                            tcm.addColumn(tableColumnEquip);
                            Equip e1 = (Equip) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 1);
                            e1.set2_nomEquip(vistaEquips.getjTextField1().getText());
                            e1.set3_classificacio(Integer.valueOf(vistaEquips.getjTextField2().getText()));
                            e1.set4_capita((Jugador) vistaEquips.getjComboBox1().getSelectedItem());
                            model.getClasseDAOEquips().actualitza(e1);
                            carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                            vistaEquips.getjTextField1().setText("");
                            vistaEquips.getjTextField2().setText("");
                        } else if (permis == 0) {
                            JOptionPane.showMessageDialog(null, "El jugador que has introduit ia es capità d'un altre equip, "
                                    + "selecciona un altre!");
                        } else if (permis == 2) {
                            
                            TableColumnModel tcm = vistaEquips.getjTable1().getColumnModel();
                            tcm.addColumn(tableColumnEquip);
                            Equip e1 = (Equip) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 1);
                            e1.set2_nomEquip(vistaEquips.getjTextField1().getText());
                            e1.set3_classificacio(Integer.valueOf(vistaEquips.getjTextField2().getText()));
                            model.getClasseDAOEquips().actualitza(e1);
                            carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                            vistaEquips.getjTextField1().setText("");
                            vistaEquips.getjTextField2().setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Has de seleccionar un equip per modificar-lo");
                    }

                }
                //Mostrar Taula Jugadors
                if (actionEvent.getSource().equals(vistaGeneral.getjButton2())) {
                    vista.setVisible(true);
                }
                //SORTIR
                if (actionEvent.getSource().equals(vistaGeneral.getjButton4())) {
                    model.tancaSessio();
                    System.exit(0);
                }
                //Mostrar Taula Equips
                if (actionEvent.getSource().equals(vistaGeneral.getjButton3())) {
                    vistaEquips.setVisible(true);
                }

            }

        };
        vista.getjButton1().addActionListener(actionListener);
        vista.getjButton2().addActionListener(actionListener);
        vista.getjButton3().addActionListener(actionListener);
        vista.getjButton4().addActionListener(actionListener);
        vistaGeneral.getjButton2().addActionListener(actionListener);
        vistaGeneral.getjButton3().addActionListener(actionListener);
        vistaGeneral.getjButton4().addActionListener(actionListener);
        vistaEquips.getjButton4().addActionListener(actionListener);
        vistaEquips.getjButton2().addActionListener(actionListener);
        vistaEquips.getjButton5().addActionListener(actionListener);
        vistaEquips.getjButton3().addActionListener(actionListener);
        vistaEquips.getjButton1().addActionListener(actionListener);

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
                if (e.getSource().equals(vistaEquips.getjTable1())) {

                    try {
                        filasel = vistaEquips.getjTable1().getSelectedRow();
                        if (filasel != -1) {
                            nomEquip = (String) vistaEquips.getjTable1().getValueAt(filasel, 1);
                            vistaEquips.getjTextField1().setText(nomEquip);
                            classificacio = (Integer) vistaEquips.getjTable1().getValueAt(filasel, 2);
                            vistaEquips.getjTextField2().setText(Integer.toString(classificacio));

                        }
                    } catch (NumberFormatException ex) {
                    }

                }

            }

        };
        vista.getjTable1().addMouseListener(mouseAdapter);
        vistaEquips.getjTable1().addMouseListener(mouseAdapter);

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

    public void carregaCombo(ArrayList resultSet, JComboBox combo) {
        combo.setModel(new DefaultComboBoxModel((resultSet != null ? resultSet.toArray() : new Object[]{})));
    }

}
