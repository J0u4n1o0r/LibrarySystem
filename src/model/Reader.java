/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *  Class containing setters and getters for object Reader.
 *  @celsoM_2017216
 */
public class Reader {

    private String id;
    private String name;
    private String surname;
    private String gender;
    private String address;
    private String country;
    private String email;
    private int age;
    private String joinDate;

    public Reader(String id, String name, String surname, String gender, String address, String country, String email, int age, String joinDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.address = address;
        this.country = country;
        this.email = email;
        this.age = age;
        this.joinDate = joinDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "Reader " +
                "id= " + id + '\n' +
                "name= " + name + '\n' +
                "surname= " + surname + '\n' +
                "gender= " + gender + '\n' +
                "address= " + address + '\n' +
                "country= " + country + '\n' +
                "email= " + email + '\n' +
                "age= " + age + +'\n' +
                "joinDate= " + joinDate + '\n';
    }
}
