package sk.peter.service;

import sk.peter.db.Contact;
import sk.peter.db.DBContactService;
import sk.peter.utility.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class CRUDManager {

    private final DBContactService contactService;

    public CRUDManager(){
        this.contactService = new DBContactService();
    }

    public void printOptions(){
        System.out.println("Hello, welcome to contacts magaer\n");
        while (true){
            System.out.println("0. Get all contacts");
            System.out.println("1. Edit contact");
            System.out.println("2. Add contact");
            System.out.println("3. Delete contact");
            System.out.println("4. Search contact");
            System.out.println("5. Exit");

            final int choice = InputUtils.readInt();
            switch (choice){
                case 0 -> printAllContact();
                case 1 -> System.out.println("Not implemented");
                case 2 -> System.out.println("Not implemented");
                case 3 -> System.out.println("Not implemented");
                case 4 -> System.out.println("Not implemented");
                case 5 -> {
                    System.out.println("Good bye");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void printAllContact() {
        final List<Contact> contacts = contactService.readAll();
        contacts.forEach(System.out::println);
    }

}
