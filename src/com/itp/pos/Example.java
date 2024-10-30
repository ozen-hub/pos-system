package com.itp.pos;

interface A{
    void print(int value);
}
class B implements A{

    @Override
    public void print(int value) {
        System.out.println(value);
    }
}
class Example{
    public static void main(String[] args) {
        B b1 = new B();
        b1.print(100);
        A a1 = (x)->{
            System.out.println(x);
        };
        a1.print(500);
    }
}