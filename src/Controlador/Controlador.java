/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.VistaEquips;
import Vista.VistaGeneral;
import Vista.VistaJugadors;
import Vista.VistaTorneig;
import entitats.Equip;
import entitats.Jugador;
import entitats.Model;
import entitats.Torneig;
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

/**
 *
 * @author cristian
 */
public class Controlador {

    /*
    FAENA PER FER: ARA ESTIC FENT EL MODIFICA EQUIP PER AL APRETA MODIFICA POSAR EL CAPITAR A BUIT
     */
    Model model;
    VistaJugadors vista;
    VistaGeneral vistaGeneral;
    VistaEquips vistaEquips;
    VistaTorneig vistaTorneig;
    private int filasel = -1;
    private String nomJugador;
    private String emailJugador;
    private String telefonJugador;
    private String nomEquip;
    private int classificacio;
    private String nomTorneig;
    private TableColumn tableColumnJugador;
    private TableColumn tableColumnEquip;
    private TableColumn tableColumnTorneig;
    private boolean esCapita = false;

    public Controlador(Model model, VistaJugadors vista, VistaGeneral vistaGeneral, VistaEquips vistaEquips, VistaTorneig vistaTorneig) {
        this.model = model;
        this.vista = vista;
        this.vistaGeneral = vistaGeneral;
        this.vistaEquips = vistaEquips;
        this.vistaTorneig = vistaTorneig;

        carregaComboJugador(vista.getjComboBox1());
        carregaComboEquip(vistaEquips.getjComboBox1());
        carregaComboEquip(vistaEquips.getjComboBox2());
        carregaComboEquip(vistaEquips.getjComboBox3());

        tableColumnJugador = carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
        tableColumnEquip = carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
        tableColumnTorneig = carregaTaula((ArrayList) model.getClasseDAOTorneig().obtenLlista(), vistaTorneig.getjTable1(), Torneig.class);

        control();
    }

    private void control() {

        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                //Modificar
                if (actionEvent.getSource().equals(vista.getjButton1())) {

                    if (vista.getjTable1().getSelectedRow() != -1) {

                        Equip eq = (Equip) vista.getjComboBox1().getSelectedItem();

                        if (eq.get2_nomEquip().equals("BUIT")) {
                            try {

                                int numero = Integer.valueOf(vista.getjTextField3().getText());

                                if (numero >= 600000000 && numero <= 999999999) {
                                    TableColumnModel tcm = vista.getjTable1().getColumnModel();
                                    tcm.addColumn(tableColumnJugador);
                                    Jugador j1 = (Jugador) vista.getjTable1().getValueAt(vista.getjTable1().getSelectedRow(), vista.getjTable1().getColumnCount() - 1);
                                    j1.set2_nom(vista.getjTextField1().getText());
                                    j1.set3_email(vista.getjTextField2().getText());
                                    j1.set4_telefon(vista.getjTextField3().getText());
                                    j1.set5_equip(null);
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
                            try {

                                int numero = Integer.valueOf(vista.getjTextField3().getText());

                                if (numero >= 600000000 && numero <= 999999999) {
                                    TableColumnModel tcm = vista.getjTable1().getColumnModel();
                                    tcm.addColumn(tableColumnJugador);
                                    Jugador j1 = (Jugador) vista.getjTable1().getValueAt(vista.getjTable1().getSelectedRow(), vista.getjTable1().getColumnCount() - 1);
                                    j1.set2_nom(vista.getjTextField1().getText());
                                    j1.set3_email(vista.getjTextField2().getText());
                                    j1.set4_telefon(vista.getjTextField3().getText());
                                    j1.set5_equip((Equip) vista.getjComboBox1().getSelectedItem());
                                    model.getClasseDAOJugadors().actualitza(j1);
                                    carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
                                    carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                                    llimpiarCamps();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Introdueix un telefon correcte d'Espanya!!!");
                                }

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Has d'introduir un telefon vàlid amb format de 9 digits XXXXXXXXX");
                            }
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

                                Equip equip = (Equip) vista.getjComboBox1().getSelectedItem();

                                if (equip.get2_nomEquip().equals("BUIT")) {

                                    Jugador jugador = new Jugador(vista.getjTextField1().getText(), vista.getjTextField2().getText(), vista.getjTextField3().getText(), null);
                                    model.getClasseDAOJugadors().guarda(jugador);
                                    carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
                                    carregaComboEquip(vistaEquips.getjComboBox1());
                                    carregaComboEquip(vistaEquips.getjComboBox2());
                                    JOptionPane.showMessageDialog(null, "Recorda de assignar un Equip al Jugador!");
                                    llimpiarCamps();
                                } else {

                                    Jugador jugador = new Jugador(vista.getjTextField1().getText(), vista.getjTextField2().getText(), vista.getjTextField3().getText(), (Equip) vista.getjComboBox1().getSelectedItem());
                                    model.getClasseDAOJugadors().guarda(jugador);
                                    carregaTaula((ArrayList) model.getClasseDAOJugadors().obtenLlista(), vista.getjTable1(), Jugador.class);
                                    carregaComboEquip(vistaEquips.getjComboBox1());
                                    carregaComboEquip(vistaEquips.getjComboBox2());
                                    llimpiarCamps();
                                    carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                                }

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
                                carregaComboEquip(vistaEquips.getjComboBox1());
                                carregaComboEquip(vistaEquips.getjComboBox2());
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
                //Crear Equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton2())) {

                    if (vistaEquips.getjTextField2().getText().equals("") || vistaEquips.getjTextField1().getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Has d'introduir tots els camps de l'Equip");
                    } else {
                        try {
                            Jugador j = (Jugador) vistaEquips.getjComboBox1().getSelectedItem();

                            if (j.get2_nom().equals("BUIT")) {

                                Equip equip = new Equip(vistaEquips.getjTextField1().getText(), Integer.valueOf(vistaEquips.getjTextField2().getText()), null);
                                model.getClasseDAOEquips().guarda(equip);
                                carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                                vistaEquips.getjTextField1().setText("");
                                vistaEquips.getjTextField2().setText("");
                                carregaComboJugador(vista.getjComboBox1());

                            } else {

                                boolean permis = true;
                                ArrayList<Equip> al = (ArrayList<Equip>) model.getClasseDAOEquips().obtenLlista();

                                for (Equip equip : al) {

                                    if (equip.get4_capita() == null) {

                                    } else if (equip.get4_capita().equals((Jugador) vistaEquips.getjComboBox1().getSelectedItem())) {
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
                                    carregaComboJugador(vista.getjComboBox1());

                                } else {
                                    JOptionPane.showMessageDialog(null, "El capita seleccionat ja és capità d'un equip, selecciona "
                                            + "un altre Jugador per ser capità");
                                }
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
                        Equip equipSel = (Equip) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 1);

                        boolean permisPerBorrar = true;
                        for (Jugador jugador : model.getClasseDAOJugadors().obtenLlista()) {

                            if (jugador.get5_equip() == null) {

                            } else if (jugador.get5_equip().get2_nomEquip().equals(equipSel.get2_nomEquip())) {
                                permisPerBorrar = false;
                            }

                        }

                        if (permisPerBorrar) {

                            tcm.addColumn(tableColumnEquip);
                            model.getClasseDAOEquips().elimina(equipSel);
                            tcm.removeColumn(tableColumnEquip);
                            carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                            vistaEquips.getjTextField1().setText("");
                            vistaEquips.getjTextField2().setText("");
                            carregaComboJugador(vista.getjComboBox1());

                        } else {
                            tcm.removeColumn(tableColumnEquip);
                            JOptionPane.showMessageDialog(null, "No és pot borrar, l'equip seleccionat és l'equip d'un "
                                    + "jugador, primer esborra el Jugador o posa l'Equip del Jugador a BUIT.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Has de seleccionar un equip primer per esborrar-lo");
                    }

                }
                //Modificar equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton1())) {

                    try {
                        filasel = vistaEquips.getjTable1().getSelectedRow();
                        if (filasel != -1) {

                            Jugador jugador = (Jugador) vistaEquips.getjComboBox1().getSelectedItem();

                            if (jugador.get2_nom().equals("BUIT")) {
                                int permis = 1;

                                Jugador jCombo = (Jugador) vistaEquips.getjComboBox1().getSelectedItem();
                                Jugador jTaula = (Jugador) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 3);

                                if (jTaula == null) {

                                } else if (jCombo.get2_nom().equals(jTaula.get2_nom())) {
                                    permis = 2;
                                } else {
                                    ArrayList<Equip> al = (ArrayList<Equip>) model.getClasseDAOEquips().obtenLlista();

                                    for (Equip equip : al) {

                                        if (equip.get4_capita() == null) {

                                        } else if (equip.get4_capita().equals((Jugador) vistaEquips.getjComboBox1().getSelectedItem())) {
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
                                    e1.set4_capita(null);
                                    model.getClasseDAOEquips().actualitza(e1);
                                    carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                                    vistaEquips.getjTextField1().setText("");
                                    vistaEquips.getjTextField2().setText("");
                                    carregaComboJugador(vista.getjComboBox1());

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
                                    carregaComboJugador(vista.getjComboBox1());

                                }
                            } else {

                                int permis = 1;

                                Jugador jCombo = (Jugador) vistaEquips.getjComboBox1().getSelectedItem();
                                Jugador jTaula = (Jugador) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 3);

                                if (jTaula == null) {

                                } else if (jCombo.get2_nom().equals(jTaula.get2_nom())) {

                                    permis = 2;

                                } else {
                                    ArrayList<Equip> al = (ArrayList<Equip>) model.getClasseDAOEquips().obtenLlista();

                                    for (Equip equip : al) {

                                        if (equip.get4_capita() == null) {

                                        } else if (equip.get4_capita().equals((Jugador) vistaEquips.getjComboBox1().getSelectedItem())) {
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
                                    carregaComboJugador(vista.getjComboBox1());
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
                                    carregaComboJugador(vista.getjComboBox1());
                                }

                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Has de seleccionar un equip per modificar-lo");
                        }
                    } catch (NumberFormatException e) {
                        TableColumnModel tcm = vistaEquips.getjTable1().getColumnModel();
                        tcm.removeColumn(tableColumnEquip);
                        JOptionPane.showMessageDialog(null, "Introdueix un numero a la classificació");
                    }

                }
                //Afegir jugador arraylist de equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton6())) {

                    filasel = vistaEquips.getjTable1().getSelectedRow();

                    if (vistaEquips.getjComboBox2().getSelectedItem() != null) {
                        if (filasel != -1) {
                            TableColumnModel tcm = vistaEquips.getjTable1().getColumnModel();
                            tcm.addColumn(tableColumnEquip);
                            Equip e1 = (Equip) vistaEquips.getjTable1().getValueAt(vistaEquips.getjTable1().getSelectedRow(), vistaEquips.getjTable1().getColumnCount() - 1);
                            Jugador j = (Jugador) vistaEquips.getjComboBox2().getSelectedItem();
                            boolean borrar = false;
                            for (Jugador jugador : e1.get5_jugadors()) {

                                if (jugador.get2_nom().equals(j.get2_nom())) {
                                    borrar = true;
                                }

                            }

                            if (!borrar) {
                                e1.get5_jugadors().add((Jugador) vistaEquips.getjComboBox2().getSelectedItem());
                                model.getClasseDAOEquips().guarda(e1);
                                tcm.removeColumn(tableColumnEquip);
                                carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);
                            } else {
                                e1.get5_jugadors().remove(j);
                                model.getClasseDAOEquips().guarda(e1);
                                tcm.removeColumn(tableColumnEquip);
                                carregaTaula((ArrayList) model.getClasseDAOEquips().obtenLlista(), vistaEquips.getjTable1(), Equip.class);

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Has de seleccionar primer un Equip al que afegir/borrar Jugadors");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Has de crear Jugadors per introduir-los dintre de la plantilla "
                                + "d'un equip.");
                    }

                }
                //Afegir tornejos a la taula equips
                if (actionEvent.getSource().equals(vistaEquips.getjButton5())) {

                    filasel = vistaEquips.getjTable1().getSelectedRow();

                    if (filasel != -1) {

                        Torneig torneig = (Torneig) vistaEquips.getjComboBox3().getSelectedItem();

                        TableColumnModel tcm = vistaEquips.getjTable1().getColumnModel();
                        tcm.addColumn(tableColumnEquip);
                        Equip e1 = (Equip) vistaEquips.getjTable1().getValueAt(
                                vistaEquips.getjTable1().getSelectedRow(),
                                vistaEquips.getjTable1().getColumnCount() - 1
                        );

                        /*
                        Variable de control:
                         - 1 = Afegir torneig taula Equips
                         - 2 = Borrar el torneig sel·leccionat de la taula Equips
                         - 3 = Nom 'BUIT' esborrar tot el contignut de l'ArrayList
                         */
                        int control = 1;

                        if (torneig.get2_nomTorneig().equals("BUIT")) {
                            control = 3;
                        } else {

                            for (Torneig t : e1.get6_tornejos()) {

                                if (t.get2_nomTorneig().equals(torneig.get2_nomTorneig())) {
                                    control = 2;
                                }

                            }

                        }

                        if (control == 1) {
                            e1.get6_tornejos().add(
                                    torneig
                            );
                        } else if (control == 2) {
                            e1.get6_tornejos().remove(
                                    torneig
                            );
                        } else if (control == 3) {
                            e1.get6_tornejos().clear();
                        } else {
                            System.out.println("UNEXPECTED");
                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "Selecciona primer un equip per asignar-lo"
                                + " a un torneig existent.");

                    }
                    carregaTaula(
                            (ArrayList) model.getClasseDAOEquips().obtenLlista(),
                            vistaEquips.getjTable1(), Equip.class
                    );

                    carregaTaula(
                            (ArrayList) model.getClasseDAOTorneig().obtenLlista(),
                            vistaTorneig.getjTable1(), Torneig.class
                    );

                }
                //Mostrar Taula Jugadors
                if (actionEvent.getSource().equals(vistaGeneral.getjButton2())) {
                    vista.setVisible(true);
                }
                //Mostrar Taula Tornejos
                if (actionEvent.getSource().equals(vistaGeneral.getjButton5())) {
                    vistaTorneig.setVisible(true);
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
                //Amagar taula tornejos
                if (actionEvent.getSource().equals(vistaTorneig.getjButton4())) {
                    vistaTorneig.setVisible(false);
                }
                //Crear Tornejos
                if (actionEvent.getSource().equals(vistaTorneig.getjButton2())) {

                    if (!vistaTorneig.getjTextField1().getText().trim().equals("")) {
                        Torneig torneig = new Torneig(vistaTorneig.getjTextField1().getText());
                        model.getClasseDAOTorneig().guarda(torneig);
                        vistaTorneig.getjTextField1().setText("");
                        carregaComboEquip(vistaEquips.getjComboBox3());
                        carregaTaula((ArrayList) model.getClasseDAOTorneig().obtenLlista(), vistaTorneig.getjTable1(), Torneig.class);
                    } else {
                        JOptionPane.showMessageDialog(null, "Has d'introduir tots els camps del torneig");
                    }

                }
                //Borrar Tornejos
                if (actionEvent.getSource().equals(vistaTorneig.getjButton3())) {

                    filasel = vistaTorneig.getjTable1().getSelectedRow();

                    if (filasel != -1) {
                        TableColumnModel tcm = vistaTorneig.getjTable1().getColumnModel();
                        tcm.addColumn(tableColumnTorneig);
                        Torneig t = (Torneig) vistaTorneig.getjTable1().getValueAt(
                                filasel,
                                vistaTorneig.getjTable1().getColumnCount() - 1
                        );
                        model.getClasseDAOTorneig().elimina(t);
                        vistaTorneig.getjTextField1().setText("");
                        carregaComboEquip(vistaEquips.getjComboBox3());
                        carregaTaula((ArrayList) model.getClasseDAOTorneig().obtenLlista(), vistaTorneig.getjTable1(), Torneig.class);
                    } else {
                        JOptionPane.showMessageDialog(null, "Has de seleccionar un torneig per borrar-lo");
                    }

                }
                //Modificar Torneig
                if (actionEvent.getSource().equals(vistaTorneig.getjButton1())) {

                    filasel = vistaTorneig.getjTable1().getSelectedRow();

                    if (filasel != -1) {
                        TableColumnModel tcm = vistaTorneig.getjTable1().getColumnModel();
                        tcm.addColumn(tableColumnTorneig);
                        Torneig t = (Torneig) vistaTorneig.getjTable1().getValueAt(
                                filasel,
                                vistaTorneig.getjTable1().getColumnCount() - 1
                        );
                        t.set2_nomTorneig(vistaTorneig.getjTextField1().getText());
                        model.getClasseDAOTorneig().actualitza(t);
                        vistaTorneig.getjTextField1().setText("");
                        carregaComboEquip(vistaEquips.getjComboBox3());
                        carregaTaula((ArrayList) model.getClasseDAOTorneig().obtenLlista(), vistaTorneig.getjTable1(), Torneig.class);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecciona un torneig per modificar-lo");
                    }

                }
                //Afegir Equip arraylist torneig
//                if (actionEvent.getSource().equals(vistaTorneig.getjButton6())) {
//
//                    filasel = vistaTorneig.getjTable1().getSelectedRow();
//
//                    if (filasel != -1) {
//                        TableColumnModel tcm = vistaTorneig.getjTable1().getColumnModel();
//                        tcm.addColumn(tableColumnTorneig);
//                        Torneig t = (Torneig) vistaTorneig.getjTable1().getValueAt(
//                                filasel,
//                                vistaTorneig.getjTable1().getColumnCount() - 1
//                        );
//
//                        Equip equip = (Equip) vistaTorneig.getjComboBox1().getSelectedItem();
//                        boolean borrar = false;
//
//                        for (Equip e : t.get3_Equips()) {
//
//                            if (e.get2_nomEquip().equals(equip.get2_nomEquip())) {
//                                borrar = true;
//                            }
//
//                        }
//
//                        if (!borrar) {
//                            t.get3_Equips().add(
//                                    (Equip) vistaTorneig.getjComboBox1().getSelectedItem()
//                            );
//                            model.getClasseDAOTorneig().guarda(t);
//                            vistaTorneig.getjTextField1().setText("");
//                            carregaTaula((ArrayList) model.getClasseDAOTorneig().obtenLlista(), vistaTorneig.getjTable1(), Torneig.class);
//                        } else {
//                            t.get3_Equips().remove(
//                                    equip
//                            );
//                            model.getClasseDAOTorneig().guarda(t);
//                            vistaTorneig.getjTextField1().setText("");
//                            carregaTaula((ArrayList) model.getClasseDAOTorneig().obtenLlista(), vistaTorneig.getjTable1(), Torneig.class);
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Selecciona un torneig per afegir-li/borrar-li l'equip.");
//                    }
//
//                }

            }

        };
        vista.getjButton1().addActionListener(actionListener);
        vista.getjButton2().addActionListener(actionListener);
        vista.getjButton3().addActionListener(actionListener);
        vista.getjButton4().addActionListener(actionListener);
        vistaGeneral.getjButton2().addActionListener(actionListener);
        vistaGeneral.getjButton3().addActionListener(actionListener);
        vistaGeneral.getjButton4().addActionListener(actionListener);
        vistaGeneral.getjButton5().addActionListener(actionListener);
        vistaEquips.getjButton4().addActionListener(actionListener);
        vistaEquips.getjButton2().addActionListener(actionListener);
        vistaEquips.getjButton3().addActionListener(actionListener);
        vistaEquips.getjButton1().addActionListener(actionListener);
        vistaEquips.getjButton6().addActionListener(actionListener);
        vistaEquips.getjButton5().addActionListener(actionListener);
        vistaTorneig.getjButton2().addActionListener(actionListener);
        vistaTorneig.getjButton3().addActionListener(actionListener);
        vistaTorneig.getjButton1().addActionListener(actionListener);
        //vistaTorneig.getjButton6().addActionListener(actionListener);
        vistaTorneig.getjButton4().addActionListener(actionListener);

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
                if (e.getSource().equals(vistaTorneig.getjTable1())) {

                    try {
                        filasel = vistaTorneig.getjTable1().getSelectedRow();
                        if (filasel != -1) {

                            nomTorneig = (String) vistaTorneig.getjTable1().getValueAt(filasel, 1);
                            vistaTorneig.getjTextField1().setText(nomTorneig);

                        }
                    } catch (NumberFormatException ex) {
                    }

                }

            }

        };
        vista.getjTable1().addMouseListener(mouseAdapter);
        vistaEquips.getjTable1().addMouseListener(mouseAdapter);
        vistaTorneig.getjTable1().addMouseListener(mouseAdapter);

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
            //column.setMaxWidth(250);
        }

        return columna;

    }

    private void llimpiarCamps() {
        vista.getjTextField1().setText("");
        vista.getjTextField2().setText("");
        vista.getjTextField3().setText("");
    }

    private void carregaComboJugador(JComboBox combo) {

        carregaCombo((ArrayList) model.getClasseDAOEquips().obtenLlista(), combo);
        ArrayList ale = (ArrayList) model.getClasseDAOEquips().obtenLlista();
        ale.add(new Equip("BUIT"));
        carregaCombo(ale, vista.getjComboBox1());
    }

    private void carregaComboEquip(JComboBox combo) {

        if (combo == vistaEquips.getjComboBox2()) {
            carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), combo);
        } else if (combo == vistaEquips.getjComboBox1()) {
            carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), combo);

            ArrayList alj = (ArrayList) model.getClasseDAOJugadors().obtenLlista();
            alj.add(new Jugador("BUIT"));
            carregaCombo(alj, combo);
        } else if (combo == vistaEquips.getjComboBox3()) {
            carregaCombo((ArrayList) model.getClasseDAOJugadors().obtenLlista(), combo);
            ArrayList alt = (ArrayList) model.getClasseDAOTorneig().obtenLlista();
            alt.add(new Torneig("BUIT"));
            carregaCombo(alt, combo);
        }

    }

    private void carregaComboTorneig(JComboBox combo) {

        carregaCombo((ArrayList) model.getClasseDAOEquips().obtenLlista(), combo);
//        ArrayList ale = (ArrayList) model.getClasseDAOEquips().obtenLlista();
//        ale.add(new Equip("BUIT"));
//        carregaCombo(ale, vistaTorneig.getjComboBox1());
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
