package com.itp.pos;

import java.util.Arrays;

class Example {
    public static void main(String[] args) {
        String x = new String("abc xyz stu dfds");

        System.out.println(x.toLowerCase());
        System.out.println(x.toUpperCase());

        String [] arr = x.split(" ");
        System.out.println(Arrays.toString(arr));
        System.out.println(x.toLowerCase().trim().length());


    }
}