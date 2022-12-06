package org.example;

import POJO.ItinaryJava;

import java.util.ArrayList;

public class ActiveMqResponse {
    public ItinaryJava itinary;
    public String exception;

    @Override
    public String toString() {
        return itinary.toString();
    }
}
