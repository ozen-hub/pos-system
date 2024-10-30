package com.itp.pos;
class Example{

    /*static void sum(int x, int y){}
    static void sum(int x, int y, int z){}
    static void sum(int x, int y, int z, int r){}*/
    static void sum(int... params){
        int ttl=0;
        System.out.println(params.length);
        for (int i = 0; i < params.length; i++) {
            ttl+=params[i];
        }
    }

    public static void main(String[] args) {
        sum(10,20);
        sum(10,20,30);
        sum(10,20,30,40);
        sum(10,20,30,40,50);
    }
}