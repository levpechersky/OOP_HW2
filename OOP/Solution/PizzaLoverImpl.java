package OOP.Solution;

import java.util.Collection;
import java.util.Set;

import OOP.Provided.PizzaLover;
import OOP.Provided.PizzaPlace;

public class PizzaLoverImpl implements PizzaLover {

	@Override
	public int compareTo(PizzaLover arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PizzaLover favorite(PizzaPlace pp)
			throws UnratedFavoritePizzaPlaceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaPlace> favorites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaLover addFriend(PizzaLover pl) throws SamePizzaLoverException,
			ConnectionAlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PizzaLover> getFriends() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaPlace> favoritesByRating(int rLimit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaPlace> favoritesByDist(int dLimit) {
		// TODO Auto-generated method stub
		return null;
	}

}
