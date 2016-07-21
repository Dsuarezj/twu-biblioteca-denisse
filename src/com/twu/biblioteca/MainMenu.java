package com.twu.biblioteca;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    int userInput;
    List<String> mainMenu = Arrays.asList("List of Books", "List of Movies", "Quit");
    List<String> booksMenu = Arrays.asList("Borrow a book", "Return a book", "Go back");
    Biblioteca biblioteca = new Biblioteca();
    Book book;





    void displayWelcome() {
        String welcomeMessage = "Welcome to: " + biblioteca.bibliotecaName;
        System.out.println(welcomeMessage);
    }


    public void readUserInput() {
        Scanner input = new Scanner(System.in);
        userInput = input.nextInt();
    }

    public int getUserInput() {
        readUserInput();
        return userInput;
    }

    public void displayMenu(List<String> menuItems) {
        System.out.println("++++++++++ Menu Option ++++++++++");
        buildAListOfItems(menuItems);
    }

    private void buildAListOfItems(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.print((i + 1) + ". " + items.get(i) + "\t");
        }
        System.out.println();
        System.out.println("Select the option number");

    }


    public void doSelectMainMenuOption() {
        while (userInput != mainMenu.size()) {
            switch (userInput) {
                case 1:
                    doBookList();
                    return;
                default:
                    System.out.printf("You chose wrong, try again. Select the option number \n");
                    readUserInput();
            }
        }

        System.out.printf("Come back again");
        System.exit(0);
    }


    public void doBookListOption() {
        while (userInput != booksMenu.size()) {
            switch (userInput) {
                case 1:
                    borrowBook();
                    callMainMenu();
                    return;
                case 2:
                    returnABook();
                    callMainMenu();
                    return;
                default:
                    System.out.printf("You chose wrong, try again. Select the option number \n");
                    readUserInput();
            }
        }
        callMainMenu();
    }


    private void callMainMenu() {
        displayMenu(mainMenu);
        getUserInput();
        doSelectMainMenuOption();
    }


    private void borrowBook() {
        List<Book> availableBookList = biblioteca.getBooksThatAreAvailable(true);
        if (availableBookList.size() == 0) {
            System.out.println("There are not books available. Come back later!");
            return;
        }

        System.out.println("Ingress the book ID that you want to borrow");
        int userBookSelection = getUserInput() - 1;

        if (userBookSelection < 0 || userBookSelection > availableBookList.size()) {
            System.out.println("We can't process that!");
            return;
        } else {
            book = availableBookList.get(userBookSelection);
            setStateOfABook(userBookSelection, availableBookList, false);
            System.out.println("You borrow: " + book.getBookName() + ". Enjoy!");
        }
    }

    private void returnABook() {
        System.out.println("++++++++++ List of your borrow books ++++++++++ ");
        List<Book> notAvailableBookList = biblioteca.getBooksThatAreAvailable(false);
        if (notAvailableBookList.size() == 0) {
            System.out.println("You don't have any borrow books!");
            return;
        }
        biblioteca.displayBookList(notAvailableBookList);
        System.out.println("Ingress the book ID that you want to return");
        int userBookSelection = getUserInput() - 1;

        if (userBookSelection < 0 || userBookSelection > notAvailableBookList.size()) {
            System.out.println("We can't process that!");
            return;
        } else {
            book = notAvailableBookList.get(userBookSelection);
            setStateOfABook(userBookSelection, notAvailableBookList, true);
            System.out.println("You return: " + book.getBookName() + ". Thanks!");
//            biblioteca.displayBookList(biblioteca.getBooksThatAreAvailable());
        }

    }

    private void setStateOfABook(int bookID, List<Book> listOfBooks, boolean state) {
        book = listOfBooks.get(bookID);
        book.setBookStateAvailable(state);
        listOfBooks.clear();
    }


    private void doBookList() {
        Biblioteca biblioteca = new Biblioteca();
        System.out.println("++++++++++ The following books are available: ++++++++++");
        biblioteca.displayBookList(biblioteca.getBooksThatAreAvailable(true));
        displayMenu(getBooksMenu());
        getUserInput();
        doBookListOption();
    }

    public List<String> getBooksMenu() {
        return booksMenu;
    }

     public List<String> getMainMenu (){
               return     mainMenu;
     }

}