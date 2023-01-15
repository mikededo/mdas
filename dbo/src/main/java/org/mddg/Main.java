package org.mddg;

import java.util.Arrays;
import org.mddg.controller.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            help();
            return;
        }

        switch (args[0]) {
            case "actor" -> new ActorController()
                .getMostCommonActors(shiftArgs(args));
            case "genre" -> new MovieController()
                .getMoviesFromGenre(shiftArgs(args));
            case "customer" -> {
                String[] shiftedArgs = shiftArgs(args);
                CustomerController controller = new CustomerController();
                switch (shiftedArgs[0]) {
                    case "create" -> controller.createStoreCustomer(shiftArgs(shiftedArgs));
                    case "update" -> controller.updateCustomer(shiftArgs(shiftedArgs));
                }
            }
            case "store" -> new StoreController().lastCustomersForEachStore(shiftArgs(args));
            default -> help();
        }
    }

    private static String[] shiftArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    private static void help() {
        System.out.println("> Valid commands: ");
        System.out.println("\tactor\t\tFind actors that have made the most movies");
        System.out.println("\tgenre\t\tFind movies of the given genre");
        System.out.println("\tcustomer");
        System.out.println("\t  create\tcreates a new customer for a given store");
        System.out.println("\t  update\tupdates an existing customer info");
        System.out.println("\tstore\t\tFinds last created customers for each store");
    }

    private static void p(String value) {
        System.out.println(value);
    }
}
