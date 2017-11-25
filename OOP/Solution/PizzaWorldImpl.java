package OOP.Solution;

import java.util.*;
import java.util.stream.Collectors;


import OOP.Provided.PizzaLover;
import OOP.Provided.PizzaLover.ConnectionAlreadyExistsException;
import OOP.Provided.PizzaLover.PizzaLoverAlreadyInSystemException;
import OOP.Provided.PizzaLover.PizzaLoverNotInSystemException;
import OOP.Provided.PizzaLover.SamePizzaLoverException;
import OOP.Provided.PizzaPlace;
import OOP.Provided.PizzaPlace.PizzaPlaceAlreadyInSystemException;
import OOP.Provided.PizzaPlace.PizzaPlaceNotInSystemException;
import OOP.Provided.PizzaWorld;

public class PizzaWorldImpl implements PizzaWorld {

	private SortedMap<Integer, PizzaLover> _system_users;
	private SortedMap<PizzaLover, Set<PizzaLover>> _user_connections;
    private SortedMap<Integer, PizzaPlace> _system_places; // id -> PizzaPlace

//	private class LoverComparator implements Comparator<PizzaLover> {
//	    public int compare(PizzaLover pl1, PizzaLover pl2) {
//	        return pl1.compareTo(pl2);
//        }
//    }
//
//    private class PlaceComparator implements Comparator<PizzaPlace>  {
//	    public int compare(PizzaPlace pp1, PizzaPlace pp2) {
//	        return pp1.compareTo(pp2);
//        }
//    }

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
	    return this._system_users.values();
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
        if(pl1.equals(pl2))
            throw new SamePizzaLoverException();
        else if(this._system_users.get(pl1.getId()) == null
                || this._system_users.get(pl2.getId()) == null)
            throw new PizzaLoverNotInSystemException();
        else if(this._user_connections.get(pl1.getId()).contains(pl2))
            throw new ConnectionAlreadyExistsException();
        else {
            this._user_connections.get(pl1).add(pl2);
            this._user_connections.get(pl2).add(pl1);
            return this;
        }
    }

    @Override
    public Collection<PizzaPlace> favoritesByRating(PizzaLover pl)
            throws PizzaLoverNotInSystemException {
	    if(this._system_users.get(pl.getId()) == null)
	        throw new PizzaLoverNotInSystemException();

        SortedSet<PizzaPlace> alreadySeen = new TreeSet<>();
        List<PizzaPlace> result = new ArrayList<>();

        for (PizzaLover friend : pl.getFriends()){
            List<PizzaPlace> appendix = friend.favoritesByRating(1).stream()
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
            List<PizzaPlace> appendix = friend.favoritesByDist(0).stream()
                    .filter(x -> !alreadySeen.contains(x))
                    .collect(Collectors.toList());
            result.addAll(appendix);
            alreadySeen.addAll(appendix);
        }
        return result;
    }

    //This method uses a Set and recursion to run a DFS on all friends.
	private boolean getRecommendation_aux(Set<PizzaLover> checked, PizzaLover pl,
										  PizzaPlace pp, int t) throws PizzaLoverNotInSystemException,
			PizzaPlaceNotInSystemException, ImpossibleConnectionException {
		if(checked.contains(pl))
            return false;
	    if(t == 0)
            return pl.favorites().contains(pp);
        if(pl.favorites().contains(pp))
            return true;
        checked.add(pl);
        boolean found = false;
        for(PizzaLover other : this._user_connections.get(pl)) {
            found |= getRecommendation(other, pp, t-1);
            if(found == true)
                break;
        }
        return found;
    }

    @Override
    public boolean getRecommendation(PizzaLover pl, PizzaPlace pp, int t)
            throws PizzaLoverNotInSystemException,
            PizzaPlaceNotInSystemException, ImpossibleConnectionException {
        if(this._system_users.get(pl.getId()) == null)
            throw new PizzaLoverNotInSystemException();
        if(false /* Place not in system */)
            throw new PizzaPlaceNotInSystemException();
        if(t < 0)
            throw new ImpossibleConnectionException();
        Set<PizzaLover> checked = new TreeSet<PizzaLover>();
        boolean found = getRecommendation_aux(checked, pl, pp, t);
        return found;
    }

}
