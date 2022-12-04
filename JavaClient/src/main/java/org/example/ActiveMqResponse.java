package org.example;

import POJO.ItinaryJava;

public class ActiveMqResponse {
    public ItinaryJava itinary;
    public String exception;

    @Override
    public String toString() {
        return itinary.toString();
    }
}
