package com.example.springsecurity02;


public class StackErrorTest {
    private static int count = 1;
    public static void d(){
        System.out.println(count++);
        d();
    }

    public static void main(String[] args) {
        StackErrorTest.d();
    }
}
