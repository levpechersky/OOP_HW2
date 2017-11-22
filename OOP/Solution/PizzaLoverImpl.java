package OOP.Solution;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.function.Predicate;


import OOP.Provided.PizzaLover;
import OOP.Provided.PizzaPlace;

public class PizzaLoverImpl implements PizzaLover {

    private int _id;
    private String _name;

    //Set of all of this lover's favorite places.
    private Set<PizzaPlace> _favorite_places;

    //Set of all of this lover's friends.
    private Set<PizzaLover> _user_friends;

    //Compares 2 pizza places by the following priority:
    // 1. Rating
    // 2. Dist
    // 3. ID (going up)
    private class RateComparator implements Comparator<PizzaPlace> {
        public int compare(PizzaPlace pp1, PizzaPlace pp2) {
			Integer id1 = pp1.getId(), id2 = pp2.getId();
			Integer dist1 = pp1.distance(), dist2 = pp2.distance();
			Double rating1 = pp1.averageRating(), rating2 = pp2.averageRating();
			if(pp1.averageRating() == pp2.averageRating()) {
                if(pp1.distance() == pp2.distance()) {
                    return id1.compareTo(id2);
                }
                else
                    return dist1.compareTo(dist2);
            }
            else
                return rating1.compareTo(rating2);
        }
    }

    //Compares 2 pizza places by the following priority:
    // 1. Dist
    // 2. Rating
    // 3. ID (going up)
    private class DistComparator implements Comparator<PizzaPlace> {
        public int compare(PizzaPlace pp1, PizzaPlace pp2) {
        	Integer id1 = pp1.getId(), id2 = pp2.getId();
        	Integer dist1 = pp1.distance(), dist2 = pp2.distance();
        	Double rating1 = pp1.averageRating(), rating2 = pp2.averageRating();
            if(pp1.distance() == pp2.distance()) {
                if(pp1.averageRating() == pp2.averageRating()) {
                    return id1.compareTo(id2);
                }
                else
                    return rating1.compareTo(rating2);
            }
            else
                return dist1.compareTo(dist2);
        }
    }

    private class PlaceNameComparator implements Comparator<PizzaPlace> {
        public int compare(PizzaPlace pp1, PizzaPlace pp2) {
            return pp1.compareTo(pp2);
        }
    }

    private Predicate<PizzaPlace> withinDist(int dLimit) {
        return pp -> ((dLimit == -1) ? true : (pp.distance() < dLimit));
    }

    private Predicate<PizzaPlace> withinRate(int rLimit) {
        return pp -> (pp.averageRating() > rLimit);
    }

    public PizzaLoverImpl(int id, String name) {
        this._id = id;
        this._name = name;
        this._favorite_places = 
            new TreeSet<PizzaPlace>(new PlaceNameComparator());
        this._user_friends = new TreeSet<PizzaLover>();
    }

    @Override
    public int getId() {
        return this._id;
    }

    @Override
    public PizzaLover favorite(PizzaPlace pp)
            throws UnratedFavoritePizzaPlaceException {
        PizzaPlaceImpl place = (PizzaPlaceImpl)(pp);
        if(!(place.wasRatedBy(this)))
            throw new UnratedFavoritePizzaPlaceException();
        this._favorite_places.add(pp);
        return this;
    }

    @Override
    public Collection<PizzaPlace> favorites() {
        return this._favorite_places.stream().collect(Collectors.toList());
    }

    @Override
    public PizzaLover addFriend(PizzaLover pl) throws SamePizzaLoverException,
            ConnectionAlreadyExistsException {
        if(pl.getId() == this._id)
            throw new SamePizzaLoverException();
        if(this._user_friends.contains(pl))
            throw new ConnectionAlreadyExistsException();
        this._user_friends.add(pl);
        return this;
    }

    @Override
    public Set<PizzaLover> getFriends() {
        return this._user_friends;
    }

    @Override
    public Collection<PizzaPlace> favoritesByRating(int rLimit) {
        RateComparator rate_comparer = new RateComparator();
        Comparator<PizzaPlace> byRating = 
            ((pp1, pp2) -> rate_comparer.compare(pp1, pp2));
        return _favorite_places.stream()
                .sorted(byRating)
                .filter(withinRate(rLimit))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PizzaPlace> favoritesByDist(int dLimit) {
        DistComparator dist_comparer = new DistComparator();
        Comparator<PizzaPlace> byDist = 
            ((pp1, pp2) -> dist_comparer.compare(pp1, pp2));
        return _favorite_places.stream()
                .sorted(byDist)
                .filter(withinDist(dLimit))
                .collect(Collectors.toList());
    }

    @Override
    public int compareTo(PizzaLover arg0) {
    	Integer id = this._id, other_id = arg0.getId();
        return id.compareTo(other_id);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PizzaLoverImpl)) {
            return false;
        }
        PizzaLoverImpl other = (PizzaLoverImpl)o;
        return this._id == other.getId();
    }

    private String favoritesToString(String delimiter) {
    	int i;
        String accumulate = "";
        if(this._favorite_places.size() == 0)
            return "";
        List<PizzaPlace> fav_places = new ArrayList<PizzaPlace>(this._favorite_places);
        for(i = 0; i < fav_places.size() - 1; i++) {
            accumulate += ((PizzaPlaceImpl)(fav_places.get(i))).getName();
            accumulate += delimiter;
        }
        accumulate += fav_places.get(i);
        return accumulate;
    }

    @Override
    public String toString() {
        String row1 = "Pizza lover: " + this._name + ".\n";
        String row2 = "Id: " + this._id + ".\n";
        String row3 = "Favorites: " + favoritesToString(", ");
        return row1 + row2 + row3;
    }

}
