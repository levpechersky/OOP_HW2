package OOP.Solution;

import java.util.Collection;
import java.util.Set;

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

	@Override
	public PizzaLover joinNetwork(int id, String name)
			throws PizzaLoverAlreadyInSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaPlace addPizzaPlace(int id, String name, int dist,
			Set<String> menu) throws PizzaPlaceAlreadyInSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaLover> registeredPizzaLovers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaPlace> registeredPizzaPlaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaLover getPizzaLover(int id)
			throws PizzaLoverNotInSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaPlace getPizzaPlace(int id)
			throws PizzaPlaceNotInSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaWorld addConnection(PizzaLover pl1, PizzaLover pl2)
			throws PizzaLoverNotInSystemException,
			ConnectionAlreadyExistsException, SamePizzaLoverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaPlace> favoritesByRating(PizzaLover pl)
			throws PizzaLoverNotInSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PizzaPlace> favoritesByDist(PizzaLover pl)
			throws PizzaLoverNotInSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getRecommendation(PizzaLover pl, PizzaPlace pp, int t)
			throws PizzaLoverNotInSystemException,
			PizzaPlaceNotInSystemException, ImpossibleConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

}
