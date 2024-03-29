package OOP.Solution;

import OOP.Provided.PizzaLover;
import OOP.Provided.PizzaPlace;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PizzaPlaceImpl implements PizzaPlace {
	private int id;
	private int distFromTech;
	private String name;
	private HashSet<String> menu;
	private TreeMap<PizzaLover, Integer> ratings;

	public PizzaPlaceImpl(int id, String name, int distFromTech, Set<String> menu){
		this.id = id;
		this.name = name;
		this.distFromTech = distFromTech;
		this.menu = new HashSet<>(menu); // copy set
		this.ratings = new TreeMap<>();
	}

	@Override
	public int compareTo(PizzaPlace arg0) {
		Integer id = this.id, other_id = arg0.getId();
		return id.compareTo(other_id);
	}

	@Override
	public boolean equals(Object o){
		if (o == null || o.getClass() != this.getClass())
			return false;

		PizzaPlaceImpl lhs = this;
		PizzaPlaceImpl rhs = (PizzaPlaceImpl) o;

		return (lhs.compareTo(rhs) == 0) && (rhs.compareTo(lhs) == 0);
	}

	@Override
	public String toString(){
		String menu_string = this.menu.stream().
				sorted().
				map(Object::toString).
				collect(Collectors.joining(", "));
		String res = String.format("PizzaPlace: %s.\n" +
				"Id: %d.\n" +
				"Distance: %d.\n" +
				"Menu: %s.", this.name, this.id, this.distFromTech, menu_string);
		return res;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int distance() {
		return this.distFromTech;
	}

	@Override
	public PizzaPlace rate(PizzaLover pl, int r) throws RateRangeException {
		if (r < 0 || r > 5)
			throw new RateRangeException();
		this.ratings.put(pl, r); // puts new value or replaces an old one
		return this;
	}

	@Override
	public int numberOfRates() {
		return this.ratings.size();
	}

	@Override
	public double averageRating() {
		if (this.numberOfRates() == 0)
			return 0.0;

		double ratings_sum = ratings.values().stream().mapToInt(Integer::intValue).sum();
		return ratings_sum / this.numberOfRates();
	}

	public boolean wasRatedBy(PizzaLover pizzaLover) {
		return ratings.containsKey(pizzaLover);
	}

	public String getName() {
		return name;
	}
}
