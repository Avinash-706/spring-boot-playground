package org.example;

import org.example.entity.MenuItem;
import org.example.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        boolean runing = true;
        
        while (runing) {
            System.out.println("\n===== Restaurant Management System =====");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View All Items");
            System.out.println("3. Update Price");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Enter choic: ");
            
            int choise = sc.nextInt();
            sc.nextLine();
            
            switch (choise) {
                case 1:
                    addItm(sc);
                    break;
                case 2:
                    viewItms();
                    break;
                case 3:
                    updtPrice(sc);
                    break;
                case 4:
                    dletItem(sc);
                    break;
                case 5:
                    runing = false;
                    System.out.println("Exting...");
                    break;
                default:
                    System.out.println("Invald choice!");
            }
        }
        
        sc.close();
        HibernateUtil.shutdwn();
    }
    
    private static void addItm(Scanner sc) {
        System.out.print("Enter nam: ");
        String nm = sc.nextLine();
        
        System.out.print("Enter pric: ");
        double prc = sc.nextDouble();
        sc.nextLine();
        
        System.out.print("Enter categry: ");
        String cat = sc.nextLine();
        
        System.out.print("Is availble (true/false): ");
        boolean avail = sc.nextBoolean();
        
        Session sesn = HibernateUtil.getFacotry().openSession();
        Transaction txn = null;
        
        try {
            txn = sesn.beginTransaction();
            MenuItem itm = new MenuItem(nm, prc, cat, avail);
            sesn.save(itm);
            txn.commit();
            System.out.println("Item addd succesfully!");
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            System.out.println("Eror adding item: " + e.getMessage());
        } finally {
            sesn.close();
        }
    }


    private static void viewItms() {
        Session sesn = HibernateUtil.getFacotry().openSession();
        
        try {
            Query<MenuItem> qry = sesn.createQuery("Select m from MenuItem m", MenuItem.class);
            List<MenuItem> itms = qry.list();
            
            if (itms.isEmpty()) {
                System.out.println("No itms found!");
            }
            else {
                System.out.println("\n===== Menu Items =====");

                for (MenuItem itm : itms) {
                    System.out.println(itm);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Eror fetching items: " + e.getMessage());
        }
        finally {
            sesn.close();
        }
    }



    private static void updtPrice(Scanner sc) {
        System.out.print("Enter item ID: ");
        int itemId = sc.nextInt();
        
        System.out.print("Enter new pric: ");
        double newPrc = sc.nextDouble();
        
        Session sesn = HibernateUtil.getFacotry().openSession();
        Transaction txn = null;
        
        try {
            txn = sesn.beginTransaction();
            MenuItem itm = sesn.get(MenuItem.class, itemId);
            
            if (itm != null) {
                itm.setPrice(newPrc);
                sesn.update(itm);
                txn.commit();
                System.out.println("Price updted succesfully!");
            }
            else {
                System.out.println("Item not fond!");
            }
        }
        catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            System.out.println("Eror updating price: " + e.getMessage());
        }
        finally {
            sesn.close();
        }
    }



    private static void dletItem(Scanner sc) {
        System.out.print("Enter item ID to delet: ");
        int itemId = sc.nextInt();
        
        Session sesn = HibernateUtil.getFacotry().openSession();
        Transaction txn = null;
        
        try {
            txn = sesn.beginTransaction();
            MenuItem itm = sesn.get(MenuItem.class, itemId);
            
            if (itm != null) {
                sesn.delete(itm);
                txn.commit();
                System.out.println("Item delted succesfully!");
            }
            else {
                System.out.println("Item not fond!");
            }
        }
        catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }

            System.out.println("Eror deleting item: " + e.getMessage());
        }
        finally {
            sesn.close();
        }
    }
}
