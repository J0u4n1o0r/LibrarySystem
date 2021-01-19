package view;

import controller.Controller;
import model.Book;
import model.Borrow;
import model.Reader;

import java.util.*;

/**
 *  This class holds all menu options as well as user input to be managed in controller.
 *  References: I have used various sample codes as base for the creation of all Menu and menu related methods:
 *  https://www.icesoft.org/wiki/display/ICE/Dynamic+Menu+Tutorial
 *  https://github.com/robisonsantos/MenuProject/blob/master/src/com/menu/Menu.java
 *  String builder is used to print elements for the visual of the menu and I have used this code as reference:
 *  https://stackoverflow.com/questions/5278204/passing-and-returning-stringbuilders-java
 *  @celsoM_2017216
 */

public class Menu {
    private static final Scanner sc = new Scanner(System.in);
    private static final Controller controller = new Controller();

    public void printMainMenu() {
        //arraylist will store every submenu
        List<SubMenu> menuOptions = new ArrayList<>();

        // add menu options to list
        menuOptions.add(new SubMenu("Search Book", this::printBookSearchMenu));
        menuOptions.add(new SubMenu("List all Books", this::printListAllBooksMenu));
        menuOptions.add(new SubMenu("Search a Reader", this::printReaderSearchMenu));
        menuOptions.add(new SubMenu("List all Readers", this::printListAllReadersMenu));
        menuOptions.add(new SubMenu("List Book borrowed by Reader", this::printListBorrowedReaderMenu));
        menuOptions.add(new SubMenu("Register a new borrowing", this::printBorrowMenu));
        menuOptions.add(new SubMenu("Register a return", this::printReturnMenu));
        menuOptions.add(new SubMenu("Save & Exit", () -> {
            // close the application
            System.out.println("Closing application...");
            System.exit(0);
        }));

        printMenu(repeat("_", 13) + "MENU" + repeat("_", 13) + "\n", menuOptions, true);
    }

    public void printBookSearchMenu() {
        SubMenu titleSub = new SubMenu("By Title", () -> {
            // Get Title
            System.out.println("Please enter the Title");
            String title = sc.next();
            // Search for book and print the results
            List<Book> bookSearch = controller.bookSearch(r -> r.getTitle().contains(title.toUpperCase()));
            printResult(bookSearch);
        });

        SubMenu authorSub = new SubMenu("By Author", () -> {
            // Get author
            System.out.println("Please enter the Author");
            String author = sc.next();
            // Search for book and print the results
            List<Book> bookSearch = controller.bookSearch(r -> r.getAuthor().contains(author.toUpperCase()));
            printResult(bookSearch);
        });

        SubMenu titleAndAuthorSub = new SubMenu("By Title And Author", () -> {
            // Get Title
            System.out.println("Please enter the Title");
            String title = sc.next();
            // Get author
            System.out.println("Please enter the Author");
            String author = sc.next();
            // Search for book and print the results
            List<Book> bookSearch = controller.bookSearch(r -> r.getTitle().contains(title.toUpperCase()) && r.getAuthor().contains(author.toUpperCase()));
            printResult(bookSearch);
        });

        printMenu("Search for a Book", Arrays.asList(titleSub, authorSub, titleAndAuthorSub), false);
    }

    private void printResult(List<Book> bookSearch) {
        // print error that book is not found
        if (bookSearch.isEmpty()) {
            System.out.println("Book not found please try again");
            printBookSearchMenu();
        } else {
            // print each book
            bookSearch.forEach(System.out::println);
            // small menu if a book should be borrowed
            printMenu("Do you wish to lend any of these books?", Arrays.asList(new SubMenu("Yes", this::printBorrowMenu), new SubMenu("No", this::printBookSearchMenu)), false);
        }
    }

    public void printBorrowMenu() {
        // get book id
        System.out.println("Please enter the Book ID:");
        String bookID = sc.next();
        Book book = controller.findBook(bookID);

        if (book != null) {
            // get reader
            System.out.println("Please enter Reader ID: ");
            String readerID = sc.next();
            Reader reader = controller.findReader(readerID);

            if (reader != null) {
                if (controller.isBookNotBorrowed(book)) {
                    // borrow book
                    controller.borrowBook(reader, book);
                    System.out.println("Book " + book.getTitle() + " was successfully borrowed to " + reader.getName());
                } else {
                    // book is borrowed -> ask for queue
                    System.out.println("Book " + book.getTitle() + " is not available at the moment");
                    showQueueMenu(reader, book);
                }
            } else {
                // reader was not found -> error
                System.out.println("Reader ID " + readerID + " not found...");
            }

        } else {
            // book was not found -> error
            System.out.println("Book ID " + bookID + " not found...");
        }
    }

    private void showQueueMenu(Reader reader, Book book) {
        // small menu if the reader should be added to the queue
        printMenu("Do you want to be added to the queue?", Arrays.asList(new SubMenu("Yes", () -> {
            controller.addToQueue(reader, book);
            printMainMenu();
        }), new SubMenu("No", this::printMainMenu)), false);
    }

    public void printListAllBooksMenu() {
        SubMenu orderByName = new SubMenu("By Title", () -> {
            // sort books by title and print them
            List<Book> sortedBooks = controller.orderBooks(Comparator.comparing(Book::getTitle));
            System.out.println(sortedBooks);
        });
        SubMenu orderByID = new SubMenu("By Author", () -> {
            // sort books by author and print them
            List<Book> sortedBooks = controller.orderBooks(Comparator.comparing(Book::getAuthor));
            System.out.println(sortedBooks);
        });
        SubMenu orderByNameAndID = new SubMenu("By Author and Title", () -> {
            // sort books by title and title and print them
            List<Book> sortedBooks = controller.orderBooks(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle));
            System.out.println(sortedBooks);
        });
        printMenu("List Books", Arrays.asList(orderByName, orderByID, orderByNameAndID), false);
    }

    public void printListAllReadersMenu() {
        SubMenu orderByName = new SubMenu("By Name", () -> {
            // sort the readers by name and print them
            List<Reader> sortedReaders = controller.orderReaders(Comparator.comparing(Reader::getName));
            System.out.println(sortedReaders);
        });
        SubMenu orderByID = new SubMenu("By ID", () -> {
            // sort the readers by id and print them
            List<Reader> sortedReaders = controller.orderReaders(Comparator.comparing(Reader::getId));
            System.out.println(sortedReaders);
        });
        SubMenu orderByNameAndID = new SubMenu("By Name and ID", () -> {
            // sort the readers by name and id and print them
            List<Reader> sortedReaders = controller.orderReaders(Comparator.comparing(Reader::getName).thenComparing(Reader::getId));
            System.out.println(sortedReaders);
        });
        printMenu("List Customers", Arrays.asList(orderByName, orderByID, orderByNameAndID), false);
    }

    public void printListBorrowedReaderMenu() {
        // get readerid and search for a reader
        System.out.println("Please enter Reader id: ");
        String input = sc.next();
        Reader reader = controller.readerSearch(r -> r.getId().equals(input));

        // if a reader was found
        if (reader != null) {
            boolean borrowFound = false;
            // load all borrowings sorted by date
            List<Borrow> currentBorrows = controller.orderBorrowings(Comparator.comparing(Borrow::getBorrowDate));
            for (Borrow borrow : currentBorrows) {
                // print the borrowing if the borrowing is not returned and not in queue
                if (borrow.getReaderId().equals(reader.getId()) && !borrow.isBorrowReturned() && !borrow.isInQueue()) {
                    Book book = controller.findBook(borrow.getBookId());

                    // print the title only on the first
                    if (!borrowFound) {
                        System.out.println("Currently borrowed book(s):");
                    }
                    System.out.println(book);
                    borrowFound = true;
                }
            }

            if (!borrowFound) {
                System.out.println("The reader has currently no book borrowed");

            }
        }

    }

    public void printReaderSearchMenu() {
        SubMenu byNameOption = new SubMenu("By Name", () -> {
            // Get reader name
            System.out.println("Please enter the Name");
            String name = sc.next();

            //Search reader by name
            controller.readerSearch(r -> r.getName().equals(name));
        });

        SubMenu byIdOption = new SubMenu("By ID", () -> {
            // Get reader id
            System.out.println("Please enter the ID");
            String id = sc.next();
            // Search reader by id
            controller.readerSearch(r -> r.getId().equals(id));
        });

        SubMenu byIdAndNameOption = new SubMenu("By ID And Name", () -> {
            // Get reader id
            System.out.println("Please enter the ID");
            String id = sc.next();
            // Get reader name
            System.out.println("Please enter the Name");
            String name = sc.next();
            // Search reader by id and name
            controller.readerSearch(r -> r.getId().equals(id) && r.getName().equals(name));
        });

        printMenu("Search for a Reader", Arrays.asList(byNameOption, byIdOption, byIdAndNameOption), false);
    }

    public void printReturnMenu() {
        // get and search book id
        System.out.println("Please enter the Book ID:");
        String bookID = sc.next();
        Book book = controller.findBook(bookID);

        if (book != null) {
            // check if book is borrowed
            if (!controller.isBookNotBorrowed(book)) {
                // trigger the return
                Borrow nextBorrow = controller.returnBook(book);
                System.out.println("Book " + book.getTitle() + " was successfully returned");

                if (nextBorrow != null) {
                    // print the next person in queue
                    System.out.println(repeat("*", 8) + "Person waiting" + repeat("*", 8));
                    Reader reader = controller.findReader(nextBorrow.getReaderId());
                    System.out.println(reader);

                    showLendMenu(book);
                }
            } else {
                // book not borrowed -> error
                System.out.println("Book " + book.getTitle() + " is currently not borrowed");
            }
        } else {
            // book was not found -> error
            System.out.println("Book ID " + bookID + " not found...");
        }
    }

    private void showLendMenu(Book book) {
        // small menu to lend book to the next person in queue
        printMenu("Lend book to the next Person?", Arrays.asList(new SubMenu("Yes", () -> {
            controller.removeFirstBorrowInQueueAndAssignNextPerson(book);
            printMainMenu();
        }), new SubMenu("No", this::printMainMenu)), false);
    }

    /**
     * Prints the concrete menu
     */
    private void printMenu(String title, List<SubMenu> options, boolean isMainMenu) {
        System.out.println();

        // print line if !mainMenu
        if (!isMainMenu)
            System.out.println(repeat("_", 30));
        //print the title and options table header
        System.out.println(title);
        System.out.println("|_NR:_|_Task__________________");

        // prints each selectable option
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("| [%d] | %s\n", i + 1, options.get(i).title);
        }

        // return to main menu if !mainMenu
        if (!isMainMenu) {
            System.out.println("| [0] | Return to Main Menu");
        }

        // print bottom table line
        System.out.println(repeat("_", 30));

        // prompt user to select a option
        System.out.println("\nPlease choose one of the options above:");

        // get selected option
        try {
            String selectedOption = sc.next();

            int option = Integer.parseInt(selectedOption);
            // if option is 0 and !mainMenu print main menu
            if (option == 0 && !isMainMenu) {
                printMainMenu();
                return;
            }

            // run the selected option
            if (option <= options.size() && option > 0) {
                SubMenu selectedMenu = options.get(option - 1);
                System.out.println(repeat("*", 30));
                System.out.printf("You selected '%s'\n", selectedMenu.title);
                System.out.println(repeat("*", 30));

                // call run on AbstractSubMenuEntry or print AbstractMenu
                selectedMenu.action.run();
            } else {
                // wrong option selected -> error
                printWrongInputMessage(options, isMainMenu);
            }

        } catch (NumberFormatException e) {
            // wrong option selected -> error
            printWrongInputMessage(options, isMainMenu);
        }

        // repeatedly print the menu
        printMenu(title, options, isMainMenu);
    }

    private final static class SubMenu {
        private final String title;
        private final Runnable action;

        public SubMenu(String title, Runnable action) {
            this.title = title;
            this.action = action;
        }
    }

    private void printWrongInputMessage(List<SubMenu> options, boolean isMainMenu) {
        System.out.printf("Please only use Numbers between %d and %d\n", isMainMenu ? 1 : 0, options.size());
    }

    // method to repeat a String n times
    private String repeat(String s, int times) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < times; i++) {
            returnValue.append(s);
        }
        return returnValue.toString();
    }
}
