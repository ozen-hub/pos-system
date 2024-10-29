package com.itp.pos;

class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }
}

public class Example {
    private static void m2() throws MyException {
        System.out.println("Start m2");
        try{
            int x=10/0;
        }catch (RuntimeException e){
            throw new MyException(e.getMessage());
        }
        System.out.println("End m2");
    }

    private static void m1(){
        System.out.println("Start m1");
        m2();
        System.out.println("End m1");
    }

    public static void main(String[] args) {
        System.out.println("Start main");
            m1();
        System.out.println("End main");
    }
}
