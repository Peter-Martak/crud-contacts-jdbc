package sk.peter.service;

import sk.peter.db.Contact;
import sk.peter.db.DBContactService;
import sk.peter.utility.InputUtils;

import java.sql.SQLOutput;
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
            System.out.println("4. Search contacts by email");
            System.out.println("5. Exit");

            final int choice = InputUtils.readInt();
            switch (choice){
                case 0 -> printAllContact();
                case 1 -> editContact();
                case 2 -> createContact();
                case 3 -> deleteContact();
                case 4 -> searchContact();
                case 5 -> {
                    System.out.println("Good bye");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void searchContact() {
        System.out.println("Enter email");
        String mail = InputUtils.readString();
        contactService.search(mail);

    }

    private void editContact() {
        List<Contact> contacts = contactService.readAll();
        while (true){
            System.out.println("0. Cancel");
            printListContact(contacts);

            System.out.println("Enter contact you want to edit: ");
             final int choice = InputUtils.readInt();

            if (choice == 0){
                return;
            } else if (choice < 1 || choice > contacts.size()){
                System.out.println("Invalid choice");
                continue;
            }
            System.out.println(contacts.get(choice - 1).toString());
            System.out.println("Which value do you want to update?");
            System.out.println("0. Cancel");
            System.out.println("1. Name");
            System.out.println("2. Email");
            System.out.println("3. Phone");

            final int value = InputUtils.readInt();

            if (value == 0){
                return;
            }

            final int id = contacts.get(choice - 1).getId();
            String name = contacts.get(choice - 1).getName();
            String email = contacts.get(choice - 1).getEmail();
            String phone = contacts.get(choice - 1).getPhone();

            switch (value){
                case 1 -> {
                    System.out.println("Enter new name");
                     name = InputUtils.readString();
                    if( contactService.edit(id, name, email, phone) > 0){
                        System.out.println("Contact edited successfully");
                    }
                    return;
                }
                case 2 -> {
                    System.out.println("Enter new email");
                    email = InputUtils.readString();
                    contactService.edit(id, name, email, phone);
                    if( contactService.edit(id, name, email, phone) > 0){
                        System.out.println("Contact edited successfully");
                    }
                    return;
                }
                case 3 ->{
                    System.out.println("Enter new phone");
                    phone = InputUtils.readString();
                    contactService.edit(id, name, email, phone);
                    if( contactService.edit(id, name, email, phone) > 0){
                        System.out.println("Contact edited successfully");
                    }
                    return;
                }
                default -> System.out.println("Invalid input");
            }

        }
    }

    private void deleteContact() {
        List<Contact> contacts = contactService.readAll();

        int choice;
        while (true) {
            System.out.println("0. Cancel");
            printListContact(contacts);
            System.out.println("Enter number of contact you want to delete");
            choice = InputUtils.readInt();

                if (choice == 0) {
                    return;
                } else if (choice < 1 || choice > contacts.size()) {
                    System.out.println("Invalid choice");
                    continue;
                }

                if ((contactService.delete(contacts.get(choice - 1).getId()) > 0)) {
                    System.out.println("Contact deleted successfully");
                    return;
                }
            }
        }

    private void createContact() {
        System.out.println("Enter name");
        final String name = InputUtils.readString();
        System.out.println("Enter phone");
        final String phone = InputUtils.readString();
        System.out.println("Enter email");
        final String email = InputUtils.readString();

        if(contactService.create(name,email,phone) > 0){
            System.out.println("Contact created successfully");
        }
    }

    private void printListContact(List<Contact> contacts){
        for (int i = 0; i < contacts.size(); i++ ){
            System.out.println(i + 1 + ". " + contacts.get(i));
        }
    }

    private void printAllContact() {
        final List<Contact> contacts = contactService.readAll();
        contacts.forEach(System.out::println);
    }

}
