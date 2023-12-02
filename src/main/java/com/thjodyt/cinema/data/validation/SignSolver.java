package com.thjodyt.cinema.data.validation;

public class SignSolver {

    static boolean isSmallLetter(char sign) {
        return sign >= 'a' && sign <= 'z';
    }

    static boolean isBigLetter(char sign) {
        return sign >= 'A' && sign <= 'Z';
    }

    static boolean isDigit(char sign) {
        return sign >= '0' && sign <= '9';
    }

}
