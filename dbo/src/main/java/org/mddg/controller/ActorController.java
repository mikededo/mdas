package org.mddg.controller;

import java.util.List;
import org.mddg.MySqlConnection;
import org.mddg.app.GetMostCommonActors;
import org.mddg.domain.Actor;
import org.mddg.domain.request.SharedQueryRequest;

public class ActorController extends Controller {

    public ActorController() {
        super();
    }

    public void getMostCommonActors(String[] args) {
        SharedQueryRequest request = this.parseQueryParams(args);
        List<Actor> result = new GetMostCommonActors(MySqlConnection.getConnection())
            .run(request);

        for (Actor actor : result) {
            p(actor.getFullName() + " " + actor.getMovieCount());
        }
    }
}
