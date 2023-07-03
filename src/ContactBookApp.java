import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}

class ContactBook {
    private List<Contact> contacts;
    private Gson gson;

    public ContactBook() {
        contacts = new ArrayList<>();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void printAllContacts() {
        for (Contact contact : contacts) {
            System.out.println("Name: " + contact.getName());
            System.out.println("Phone Number: " + contact.getPhoneNumber());
            System.out.println("Email: " + contact.getEmail());
            System.out.println("-------------------------");
        }
    }

    public void saveToFile(String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(contacts, writer);
            System.out.println("Contacts saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            Contact[] loadedContacts = gson.fromJson(reader, Contact[].class);
            contacts.clear();
            Collections.addAll(contacts, loadedContacts);
            System.out.println("Contacts loaded from file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading contacts from file: " + e.getMessage());
        }
    }
}

public class ContactBookApp {
    public static void main(String[] args) {
        ContactBook contactBook = new ContactBook();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Contact Book App");
        System.out.println("Enter 'help' to see available commands.");

        boolean exitRequested = false;

        while (!exitRequested) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "help":
                    printHelp();
                    break;
                case "add":
                    addContact(contactBook, scanner);
                    break;
                case "remove":
                    removeContact(contactBook, scanner);
                    break;
                case "print all":
                    contactBook.printAllContacts();
                    break;
                case "save":
                    saveContacts(contactBook, scanner);
                    break;
                case "load":
                    loadContacts(contactBook, scanner);
                    break;
                case "exit":
                    exitRequested = true;
                    break;
                default:
                    System.out.println("Unsupported command. Enter 'help' to see available commands.");
                    break;
            }
        }

        System.out.println("Exiting Contact Book App");
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("add - Add a new contact");
        System.out.println("remove - Remove a contact");
        System.out.println("print all - Print all contacts");
        System.out.println("save - Save contacts to a file");
        System.out.println("load - Load contacts from a file");
        System.out.println("exit - Exit the application");
    }

    private static void addContact(ContactBook contactBook, Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, email);
        contactBook.addContact(contact);
        System.out.println("Contact added successfully");
    }

    private static void removeContact(ContactBook contactBook, Scanner scanner) {
        System.out.print("Enter name of contact to remove: ");
        String name = scanner.nextLine();

        List<Contact> contacts = contactBook.getAllContacts();
        Contact contactToRemove = null;

        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                contactToRemove = contact;
                break;
            }
        }

        if (contactToRemove != null) {
            contactBook.removeContact(contactToRemove);
            System.out.println("Contact removed successfully");
        } else {
            System.out.println("Contact not found");
        }
    }

    private static void saveContacts(ContactBook contactBook, Scanner scanner) {
        System.out.print("Enter file name to save contacts: ");
        String fileName = scanner.nextLine();
        contactBook.saveToFile(fileName);
    }

    private static void loadContacts(ContactBook contactBook, Scanner scanner) {
        System.out.print("Enter file name to load contacts: ");
        String fileName = scanner.nextLine();
        contactBook.loadFromFile(fileName);
    }
}
