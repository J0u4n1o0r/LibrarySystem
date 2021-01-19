/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *  Class containing setters and getters for object Book, also contains Queue used as waiting list.
 *  @celsoM_2017216
 */

public class Book {

    private String id;
    private String title;
    private String author;
    private int year;
    private String gender;
    private Boolean isAvailable;
    private final Queue<Borrow> borrowQueue;

    public Book(String id, String title, String author, int year, String gender, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.gender = gender;
        this.isAvailable = isAvailable;
        borrowQueue = new Queue<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public void addBorrowToQueue(Borrow borrow) {
        borrowQueue.add(borrow);
    }

    public List<Borrow> getAllQueuedBorrows() {
        return borrowQueue.all();
    }

    public Borrow getFirstBorrowInQueue() {
        return borrowQueue.firstWithoutRemoving();
    }

    @Override
    public String toString() {
        return "Book " +
                "id= " + id + '\n' +
                "title= " + title + '\n' +
                "author= " + author + '\n' +
                "year= " + year + '\n' +
                "gender= " + gender + '\n' +
                "isAvailable= " + isAvailable + "\n";
    }
}
