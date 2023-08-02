package com.pragma.usuario.micro.domain.model.common;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String ADMINISTRATOR = "ADMINISTRATOR";
    private static final String OWNER = "OWNER";
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String CLIENT = "CLIENT";

    public static Boolean isEmail (String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        return mather.find();
    }

    public static Boolean isValidPhoneNumer (String phoneNumer){
        if (phoneNumer.length() != 13) {
            return false;
        }

        Pattern pattern = Pattern.compile("\\+[0-9]");
        Matcher mather = pattern.matcher(phoneNumer);

        return mather.find();
    }

    public static Boolean isValidDocument (String document){
        return document.matches("[0-9]+");
    }

    public static Boolean isValidYear (String date){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(date, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);

        return periodo.getYears() >= 18;
    }

}
