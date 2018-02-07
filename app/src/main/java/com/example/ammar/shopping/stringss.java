package com.example.ammar.shopping;

import java.util.Stack;


/**
 * Created by uzairhassan on 06/05/2017.
 */

public class stringss {
    public boolean is_palindrome(char[] arr) {
        int l = arr.length;
        boolean test = true;
        if (!arr.equals("")) {
            for (int i = 0, j = l - 1; i < l / 2; i++, j--) {
                if (arr[i] != arr[j]) {
                    test = false;
                    break;
                }
                if (test == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasvalidparanthesis(char[] arr) {
        Stack<Integer> s;
        s = new Stack<Integer>();
        boolean flag = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'a' || arr[i] == 'b' || arr[i] == 'c' || arr[i] == 'd' || arr[i] == 'e' || arr[i] == 'f' || arr[i] == 'g' || arr[i] == 'h' || arr[i] == 'i' || arr[i] == 'j' || arr[i] == 'k' || arr[i] == 'l' || arr[i] == 'm' || arr[i] == 'n' || arr[i] == 'o' || arr[i] == 'p' || arr[i] == 'q' || arr[i] == 'r' || arr[i] == 's' || arr[i] == 't' || arr[i] == 'u' || arr[i] == 'v' || arr[i] == 'w' || arr[i] == 'x' || arr[i] == 'y' || arr[i] == 'z' || arr[i] == '(' || arr[i] == ')') {
                if (arr[i] == ')' && s.isEmpty()) {
                    return false;
                } else if (arr[i] == '(') {
                    s.push(1);
                } else if (arr[i] == ')') {
                    s.pop();
                }
            } else {
                //string not valid contains other than 'a-z' , '(', ')' alphabet;
                return false;
            }
        }
        if (s.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean areanagrams(char[] chr1, char[] chr2) {

        // we can use best sorting algorithms like merge,quick,count sort
        //because of time shortage I used this sorting method

        if (chr1.length == chr2.length) {
            for (int i = 0; i < chr1.length; i++) {
                for (int j = i + 1; j < chr1.length; j++) {
                    int charofstr1 = chr1[i];
                    int charofstr2 = chr1[j];
                    if (charofstr1 > charofstr2) {
                        char temp = chr1[i];
                        chr1[i] = chr1[j];
                        chr1[j] = temp;
                    }
                }
            }
            for (int i = 0; i < chr2.length; i++) {
                for (int j = i + 1; j < chr2.length; j++) {
                    int charofstr1 = chr2[i];
                    int charofstr2 = chr2[j];
                    if (charofstr1 > charofstr2) {
                        char temp = chr2[i];
                        chr2[i] = chr2[j];
                        chr2[j] = temp;
                    }
                }
            }
            boolean test = true;
            for (int i = 0; i < chr1.length; i++) {
                if (chr1[i] != chr2[i]) {
                    test = false;
                    break;
                }
            }
            if (test == true) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
