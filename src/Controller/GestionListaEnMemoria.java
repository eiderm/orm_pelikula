/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Pelikula;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Eider
 */
public class GestionListaEnMemoria {

    private static Configuration config = new Configuration();
    private static SessionFactory faktoria = config.configure().buildSessionFactory();

    static FileReader frIkus = null;
    static BufferedReader br = null;

    public static void guardarDatos(Pelikula peli) {
        Session saioa = null;
        Transaction tx = null;

        try {
            saioa = faktoria.openSession();
            tx = saioa.beginTransaction();
            saioa.save(peli);
            tx.commit();
        } catch (HibernateException ex) {
            System.err.println("Ezin izan da pelikula gehitu!\n\n ARRAZOIAK:\n" + ex.getCause());
            if (tx != null) {
                tx.rollback(); // errorea egon bada, atzera bota
            }
        } finally {
            if (saioa != null) {
                saioa.close();
            }
        }
    }

    public static ObservableList<Pelikula> peliKargatu() {
        Session saioa = null;
        ObservableList<Pelikula> peliGuzt = FXCollections.observableArrayList();
        try {
            saioa = faktoria.openSession(); // sesioa hasi - konektatu
            for (Iterator itPelikula = saioa.createQuery("FROM Pelikula").iterate(); itPelikula.hasNext();) { // KONTSULTA
                Pelikula peli = (Pelikula) itPelikula.next(); // Hurrengoa hartu
                Hibernate.initialize(peli);
                peliGuzt.add(peli);
            }
        } catch (HibernateException e) {
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        } finally {
            if (saioa != null) {
                saioa.close(); // saioa itxi
            }

            return peliGuzt; // observableList-a datuekin bueltatu
        }

        /*SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from pelikula");
        List<Pelikula> lista = q.list();
        Iterator<Pelikula> iter = lista.iterator();
        tx.commit();
        session.close();
        ObservableList<Pelikula> data = FXCollections.observableArrayList();
        while (iter.hasNext()) {
            Pelikula h = (Pelikula) iter.next();
            data.add(h);
        }
        return data;*/
    }

    public static void peliEzabatu(Pelikula peli) {
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(peli);
        tx.commit();
        session.close();
    }

    public static ArrayList<String> cargarDatosIkusita() {

        try {
            //Strima irekitzen dugu.
            frIkus = new FileReader("Ikusita.txt");
            br = new BufferedReader(frIkus);
            String strAux;
            String[] arrAux;
            ArrayList<String> arrIkus = new ArrayList();
            while ((strAux = br.readLine()) != null) {
                arrAux = strAux.split(",");
                for (int i = 0; i < arrAux.length; i++) {
                    arrIkus.add(arrAux[i]);
                }
            }
            br.close();
            return arrIkus;
        } catch (FileNotFoundException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Artxiboa ez da aurkitu.");
            error.showAndWait();
            return null;
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Errorea egon da irakurketan.");
            error.showAndWait();
            return null;
        }
    }
}
