package org.mddg.controller;

import org.mddg.MySqlConnection;
import org.mddg.app.CreateStoreCustomer;
import org.mddg.app.UpdateCustomer;
import org.mddg.domain.request.CustomerFieldsRequest;

public class CustomerController extends Controller {

    public CustomerController() {
        super();
    }

    public void createStoreCustomer(String[] args) {
        if (!hasInsertEnoughArgs(args)) {
            return;
        }

        CustomerFieldsRequest request = validateRequest(args, true);
        if (request == null) {
            return;
        }

        Integer id = new CreateStoreCustomer(MySqlConnection.getConnection())
            .run(request);
        if (id == null) {
            p("Could not insert customer");
            return;
        }

        p("Customer inserted successfully {id:" + id + "}");
    }

    public void updateCustomer(String[] args) {
        if (!hasUpdateEnoughArgs(args)) {
            return;
        }

        Integer id = null;
        for (String arg : args) {
            if (arg.startsWith("--id=") || arg.startsWith("-i")) {
                id = Integer.valueOf(arg.split("=")[1]);
            }
        }
        if (id == null) {
            p("Customer id (-i, --id) is required");
        }

        CustomerFieldsRequest request = validateRequest(args, false);
        if (request == null) {
            return;
        }
        if (!request.hasAnyValue()) {
            p("Cannot update without any value");
           return;
        }

        new UpdateCustomer(MySqlConnection.getConnection())
            .run(request, id);
        p("Customer updated successfully");
    }

    private boolean hasInsertEnoughArgs(String[] args) {
        if (args.length < 4) {
            p("Not enough arguments: create --first-name=<name> --last-name=<surname> --address-id=<id> --store-id=<id> [--email=<email>]");
            p("  -f\t--first-name\t\tCustomer first name (first_name column)");
            p("  -l\t--last-name\t\tCustomer last name (last_name column)");
            p("  -e\t--email\t\t\tCustomer email (email column)");
            p("  -a\t--address-id\t\tCustomer address id (address_id column)");
            p("  -s\t--store-id\t\tCustomer store id (store_id column)");

            return false;
        }

        return true;
    }

    private boolean hasUpdateEnoughArgs(String[] args) {
        if (args.length < 1) {
            p("Not enough arguments: update --id=<id> [--first-name=<name> --last-name=<surname> --address-id=<id> --store-id=<id> --email=<email>]");
            p("  -i\t--id\t\t\tCustomer id (customer_id column)");
            p("  -f\t--first-name\t\tCustomer first name (first_name column)");
            p("  -l\t--last-name\t\tCustomer last name (last_name column)");
            p("  -e\t--email\t\t\tCustomer email (email column)");
            p("  -a\t--address-id\t\tCustomer address id (address_id column)");
            p("  -s\t--store-id\t\tCustomer store id (store_id column)");

            return false;
        }

        return true;
    }

    private CustomerFieldsRequest validateRequest(String[] args, boolean failForRequired) {
        CustomerFieldsRequest request = new CustomerFieldsRequest();
        boolean hasFirstNameParam = false,
            hasLastNameParam = false,
            hasAddressParam = false,
            hasStoreParam = false;

        for (String arg : args) {
            if (request.isFirstNameParam(arg)) {
                request.setFirstNameParam(arg);
                hasFirstNameParam = true;
            } else if (request.isLastNameParam(arg)) {
                request.setLastNameParam(arg);
                hasLastNameParam = true;
            } else if (request.isEmailParam(arg)) {
                request.setEmailParam(arg);
            } else if (request.isAddressParam(arg)) {
                request.setAddressParam(arg);
                hasAddressParam = true;
            } else if (request.isStoreParam(arg)) {
                request.setStoreParam(arg);
                hasStoreParam = true;
            }
        }

        if (!failForRequired) {
            return request;
        }

        if (!hasFirstNameParam) {
            p("Missing required first name param.");
            return null;
        }
        if (!hasLastNameParam) {
            p("Missing required last name param.");
            return null;
        }
        if (!hasAddressParam) {
            p("Missing required address param.");
            return null;
        }
        if (!hasStoreParam) {
            p("Missing required store param.");
            return null;
        }

        return request;
    }
}
