package com.leetcode.dp;

/**
 *  A message containing letters from A-Z is being encoded to numbers using the following mapping:
 *
 *  'A' -> 1
 *  'B' -> 2
 *  ...
 *  'Z' -> 26
 *
 *  Given an encoded message containing digits, determine the total number of ways to decode it.
 *
 *  For example,
 *  Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 *
 *  The number of ways decoding "12" is 2.
 *
 */
public class DecodeWays {
    /**
     * Best solution!
     *
     * Time: O(n) Space: O(1)
     *
     */
    public int numDecodings3(String s) {
        int len = s.length();
        if(len == 0) return 0;
        int first = isValid(s, 0, 1) ? 1 : 0;
        if(len == 1) return first;
        int second = isValid(s, 0, 2) ? 1 : 0;
        if(isValid(s, 1, 2)) second += first;
        if(len == 2) return second;

        int result = 0;
        for(int i = 2; i < len; i++){
            result = 0;
            if(isValid(s, i, i + 1)) result += second;
            if(isValid(s, i - 1, i + 1)) result += first;
            first = second;
            second = result;
        }
        return result;
    }

    public int numDecodings(String s) {
        int len = s.length();
        if(len == 0) return 0;
        int[] memo = new int[len];
        if(isValid(s, 0, 1)) memo[0] = 1;
        if(len == 1) return memo[0];
        if(isValid(s, 1, 2)) memo[1] += memo[0];
        if(isValid(s, 0, 2)) memo[1]++;

        for(int i = 2; i < len; i++){
            if(isValid(s, i, i + 1)) memo[i] += memo[i - 1];
            if(isValid(s, i - 1, i + 1)) memo[i] += memo[i - 2];
        }
        return memo[len - 1];
    }

    public boolean isValid(String s, int i, int j){
        if(i >= j) return false;
        if(s.charAt(i) == '0') return false;
        int num = Integer.parseInt(s.substring(i, j));
        return num >= 1 && num <= 26;
    }

    /**
     * Recursive solution: O(2^n)
     *
     */
    public int numDecodings2(String s) {
        if(s.length() == 0) return 0;
        return numDecodings2(s, s.length() - 1);
    }

    public int numDecodings2(String s, int index){
        int result = 0;
        if(index == 0){
            return isValid(s, 0, 1) ? 1 : 0;
        }

        if(index == 1){
            result += numDecodings2(s, 0);
            if(isValid(s, 0, 2)) result += 1;
            return result;
        }

        if(isValid(s, index, index + 1)) result += numDecodings2(s, index - 1);
        if(isValid(s, index - 1, index + 1)) result += numDecodings2(s, index - 2);
        return result;
    }

    public static void main(String[] args){
        DecodeWays test = new DecodeWays();
        System.out.println(test.numDecodings2("123"));
    }
}
