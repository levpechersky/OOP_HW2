package OOP.Solution;

import OOP.Provided.PizzaLover;
import OOP.Provided.PizzaLover.ConnectionAlreadyExistsException;
import OOP.Provided.PizzaLover.PizzaLoverAlreadyInSystemException;
import OOP.Provided.PizzaLover.PizzaLoverNotInSystemException;
import OOP.Provided.PizzaLover.SamePizzaLoverException;
import OOP.Provided.PizzaPlace;
import OOP.Provided.PizzaPlace.PizzaPlaceAlreadyInSystemException;
import OOP.Provided.PizzaPlace.PizzaPlaceNotInSystemException;
import OOP.Provided.PizzaWorld;

import java.util.stream.Collectors;
import java.util.TreeSet;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.Queue;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class PizzaWorldImpl implements PizzaWorld {

	private SortedMap<Integer, PizzaLover> _system_users; // id -> PizzaLover
	private SortedMap<PizzaLover, Set<PizzaLover>> _user_connections;
    private SortedMap<Integer, PizzaPlace> _system_places; // id -> PizzaPlace

    public PizzaWorldImpl() {
	    this._system_users = new TreeMap<>();
	    this._user_connections = new TreeMap<>();
	    this._system_places = new TreeMap<>();
    }

    @Override
    public PizzaLover joinNetwork(int id, String name)
            throws PizzaLoverAlreadyInSystemException {
        if(_system_users.containsKey(id))
            throw new PizzaLoverAlreadyInSystemException();
        PizzaLover lover = new PizzaLoverImpl(id, name);
        //Add new lover to the system.
        this._system_users.put(id, lover);
        //Add lover to connections map, with empty set of connections.
        this._user_connections.put(lover, new TreeSet<>());
        return lover;
    }

    @Override
    public PizzaPlace addPizzaPlace(int id, String name, int dist,
            Set<String> menu) throws PizzaPlaceAlreadyInSystemException {
		if (this._system_places.containsKey(id))
			throw new PizzaPlaceAlreadyInSystemException();

		PizzaPlace newPlace = new PizzaPlaceImpl(id, name, dist, menu);
   		this._system_places.put(id, newPlace);
        return newPlace;
    }

    @Override
    public Collection<PizzaLover> registeredPizzaLovers() {
	    return new TreeSet<>(this._system_users.values());
    }

    @Override
    public Collection<PizzaPlace> registeredPizzaPlaces() {
		// return a copy, stay on the safe side
        return new TreeSet<>(this._system_places.values());
    }

    @Override
    public PizzaLover getPizzaLover(int id)
            throws PizzaLoverNotInSystemException {
        if(this._system_users.get(id) == null)
            throw new PizzaLoverNotInSystemException();
        return this._system_users.get(id);
    }

    @Override
    public PizzaPlace getPizzaPlace(int id)
            throws PizzaPlaceNotInSystemException {
        PizzaPlace pp = this._system_places.get(id);
        if (pp == null)
        	throw new PizzaPlaceNotInSystemException();

        return pp;
    }

    @Override
    public PizzaWorld addConnection(PizzaLover pl1, PizzaLover pl2)
            throws PizzaLoverNotInSystemException,
            ConnectionAlreadyExistsException, SamePizzaLoverException {
        if (pl1.equals(pl2))
            throw new SamePizzaLoverException();
        if (this._system_users.get(pl1.getId()) == null || this._system_users.get(pl2.getId()) == null)
            throw new PizzaLoverNotInSystemException();
        if (this._user_connections.get(pl1).contains(pl2))
            throw new ConnectionAlreadyExistsException();

        this._user_connections.get(pl1).add(pl2);
        this._user_connections.get(pl2).add(pl1);
        pl1.addFriend(pl2);
        pl2.addFriend(pl1);
        return this;
    }

    @Override
    public Collection<PizzaPlace> favoritesByRating(PizzaLover pl)
            throws PizzaLoverNotInSystemException {
	    if(this._system_users.get(pl.getId()) == null)
	        throw new PizzaLoverNotInSystemException();

        SortedSet<PizzaPlace> alreadySeen = new TreeSet<>();
        List<PizzaPlace> result = new ArrayList<>();

        for (PizzaLover friend : pl.getFriends()){
            List<PizzaPlace> appendix = friend.favoritesByRating(0).stream()
                    .filter(x -> !alreadySeen.contains(x))
                    .collect(Collectors.toList());
            result.addAll(appendix);
            alreadySeen.addAll(appendix);
        }
        return result;
    }

    @Override
    public Collection<PizzaPlace> favoritesByDist(PizzaLover pl)
            throws PizzaLoverNotInSystemException {
        if(this._system_users.get(pl.getId()) == null)
            throw new PizzaLoverNotInSystemException();

        SortedSet<PizzaPlace> alreadySeen = new TreeSet<>();
        List<PizzaPlace> result = new ArrayList<>();

        for (PizzaLover friend : pl.getFriends()){
            List<PizzaPlace> appendix = friend.favoritesByDist(Integer.MAX_VALUE).stream()
                    .filter(x -> !alreadySeen.contains(x))
                    .collect(Collectors.toList());
            result.addAll(appendix);
            alreadySeen.addAll(appendix);
        }
        return result;
    }

	private boolean getRecommendation_bfs(PizzaLover pl, PizzaPlace pp, int t) throws PizzaLoverNotInSystemException,
			PizzaPlaceNotInSystemException, ImpossibleConnectionException {
        class PizzaLoverNode {
            public PizzaLover user;
            public int distance;

            public PizzaLoverNode(PizzaLover user, int distance){
                this.user = user;
                this.distance = distance;
            }
        }

        Queue<PizzaLoverNode> queue = new LinkedList<>();
        TreeSet<PizzaLover> visited = new TreeSet<>();
        queue.add(new PizzaLoverNode(pl, 0));
        visited.add(pl);

        PizzaLoverNode current;
        while (!queue.isEmpty()){
            current = queue.element();
            if (current.distance > t)
                return false;
            if (current.user.favorites().contains(pp))
                return true;

            for (PizzaLover friend : current.user.getFriends()) {
                if (!visited.contains(friend)){
                    queue.add(new PizzaLoverNode(friend, current.distance + 1));
                    visited.add(friend);
                }
            }
            queue.remove();
        }
        return false;
    }

    @Override
    public boolean getRecommendation(PizzaLover pl, PizzaPlace pp, int t)
            throws PizzaLoverNotInSystemException,
            PizzaPlaceNotInSystemException, ImpossibleConnectionException {
        if(this._system_users.get(pl.getId()) == null)
            throw new PizzaLoverNotInSystemException();
        if(!this._system_places.containsKey(pp.getId()))
            throw new PizzaPlaceNotInSystemException();
        if(t < 0)
            throw new ImpossibleConnectionException();

        return getRecommendation_bfs(pl, pp, t);
    }

    private String friendshipToString(PizzaLover user, Set<PizzaLover> friends) {
        int id = user.getId();
        String friends_str = friends.stream()
                .sorted()
                .map(PizzaLover::getId)
                .map(integer -> integer.toString())
                .collect(Collectors.joining(", "));
        return String.format("%d -> [%s]", id, friends_str);
    }

    @Override
    public String toString() {
        String lovers_str = this._system_users.keySet().stream()
                .sorted()
                .map(integer -> integer.toString())
                .collect(Collectors.joining(", "));
        String places_str = this._system_places.keySet().stream()
                .sorted()
                .map(integer -> integer.toString())
                .collect(Collectors.joining(", "));

        StringBuilder friendship_str = new StringBuilder();
        for (PizzaLover user : this._user_connections.keySet())
            friendship_str.append(friendshipToString(user, this._user_connections.get(user)) + ".\n");

        return String.format("Registered pizza lovers: %s.\n" +
                "Registered pizza places: %s.\n" +
                "Pizza lovers:\n" +
                "%s" +
                "End pizza lovers.", lovers_str, places_str, friendship_str);
    }
}
