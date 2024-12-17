package com.checkingEvenOdd;
import java.util.Scanner;
/**
 * @author Saimun
 */
public class CheckingEvenOdd {
    public static void main(String[] args) {
        int n;
        Scanner input = new Scanner(System.in);
        System.out.println("Please Inter a Number . . .");
        n = input.nextInt();
        if(n % 2 == 0){
            System.out.println( n +" is a Even Number.");
        }else if(n % 2 == 1){
            System.out.println(n +" is a Odd Number.");
        }else{
            System.out.println("Sorry! Wrong Input . . .");
        }
    }
}
