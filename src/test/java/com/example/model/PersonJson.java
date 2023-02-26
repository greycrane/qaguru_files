package com.example.model;

public class PersonJson {
    public Person person;

    public static class Person {
        public Integer id;
        public String firstName;
        public String lastName;
        public Integer age;
        public Boolean hasChildren;
    }
}
