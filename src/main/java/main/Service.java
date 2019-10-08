package main;

public class Service {
    String greeting (String string){
        return new StringBuilder(string).reverse().toString();
    }
}
