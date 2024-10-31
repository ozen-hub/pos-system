package com.itp.pos;

interface Animal{
    public void sound();
}

class Cat implements Animal{// padukka

    public void sound() {
        System.out.println("Meaw...");
    }
}

class Example {// Mahawa

    public static void main(String[] args) {
        Animal cat= new Cat();
        cat.sound();
    }
}