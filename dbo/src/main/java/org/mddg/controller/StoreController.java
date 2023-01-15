package org.mddg.controller;

import java.util.List;
import org.mddg.MySqlConnection;
import org.mddg.app.GetStoreLastCreatedCustomer;
import org.mddg.domain.Pair;
import org.mddg.domain.Store;
import org.mddg.domain.User;
import org.mddg.domain.request.SharedQueryRequest;

public class StoreController extends Controller {

    public StoreController() {
        super();
    }

    public void lastCustomersForEachStore(String[] args) {
        SharedQueryRequest request = parseQueryParams(args);
        List<Pair<Store, User>> results = new GetStoreLastCreatedCustomer(
            MySqlConnection.getConnection()).run(request);

        if (results.size() == 0) {
            p("No customers found for stores");
            return;
        }

        if (request.isOrderASC()) {
            p("First created customers for stores");
        } else {
            p("Last created customers for stores");
        }
        for (Pair<Store, User> pair : results) {
            p("Store: "
                + pair.left().toString()
                + " -> "
                + pair.right().toString());
        }
    }
}
