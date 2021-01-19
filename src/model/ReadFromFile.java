
package model;

import controller.Controller;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Class used to read records from .txt file to arraylists
 *  References: Modification of code "Search from file" given in class by @apont on Week 7.
 *  @celsoM_2017216
 */

public class ReadFromFile {

    /**
     * Loads all readers from the readers.txt file to readers arraylist
     * @return readers
     */
    public List<Reader> loadReadersList() {

        List<Reader> readers = new ArrayList<>();
        String[] data;
        String id;
        String name;
        String surname;
        String gender;
        String address;
        String country;
        String email;
        int age;
        String joinDate;

        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/readers.txt"));
            String textLine = br.readLine();

            while (textLine != null) {

                data = textLine.split(":");
                id = data[0];
                name = data[1];
                surname = data[2];
                gender = data[3];
                address = data[4];
                country = data[5];
                email = data[6];
                age = Integer.parseInt(data[7]);
                joinDate = data[8];
                readers.add(new Reader(id, name, surname, gender, address, country, email, age, joinDate));
                textLine = br.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }

    /**
     * Loads all books from the books.txt file to books arraylist
     * builds queue for the book
     * @return books
     */
    public List<Book> loadBooksList() {

        List<Book> books = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/books.txt"));
            String contentLine = br.readLine();

            String[] data;
            String id;
            String title;
            String author;
            int year;
            String gender;

            while (contentLine != null) {

                data = contentLine.split(",");
                id = data[0];
                title = data[1].toUpperCase();
                author = data[2].toUpperCase();
                year = Integer.parseInt(data[3]);
                gender = data[4];

                books.add(new Book(id, title, author, year, gender, true));
                contentLine = br.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        // build queue for all loaded books
        List<Borrow> orderedBorrows = new Controller().orderBorrowings(Comparator.comparing(Borrow::getBorrowDate));
        for (Book book : books) {
            for (Borrow borrow : orderedBorrows) {
                // borrow matches the book id and is not returned
                if (borrow.getBookId().equals(book.getId()) && !borrow.isBorrowReturned()) {
                    if (borrow.isInQueue()) {
                        // add borrow to queue
                        book.addBorrowToQueue(borrow);
                    } else {
                        // the book is not available because it is reserved
                        book.setAvailable(false);
                    }
                }
            }
        }

        return books;
    }

    /**
     * Loads all borrows from the borrowings.txt file to borrowing arraylist
     * @return borrows
     */
    public List<Borrow> loadBorrowList() {

        List<Borrow> borrowingList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);


        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/borrowings.txt"));
            String contentLine = br.readLine();

            String[] data;
            String userId;
            String bookId;
            String title;
            String name;
            Date borrowDate = null;
            Date returnDate = null;
            String borrowId;
            boolean isInQueue;

            while (contentLine != null) {

                data = contentLine.split(",");
                userId = data[0];
                name = data[1];
                bookId = data[2];
                title = data[3];

                try {
                    borrowDate = df.parse(data[4]);

                    if (!data[5].equals("null"))
                        returnDate = df.parse(data[5]);
                    else
                        returnDate = null;
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                }

                borrowId = data[6];
                isInQueue = Boolean.parseBoolean(data[7]);

                borrowingList.add(new Borrow(userId, name, bookId, title, borrowDate, returnDate, isInQueue, borrowId));
                contentLine = br.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return borrowingList;
    }


}