package OOP.Tests;

import OOP.Provided.PizzaLover;
import OOP.Provided.PizzaLover.ConnectionAlreadyExistsException;
import OOP.Provided.PizzaLover.PizzaLoverAlreadyInSystemException;
import OOP.Provided.PizzaLover.PizzaLoverNotInSystemException;
import OOP.Provided.PizzaLover.SamePizzaLoverException;
import OOP.Provided.PizzaPlace;
import OOP.Provided.PizzaPlace.PizzaPlaceAlreadyInSystemException;
import OOP.Provided.PizzaPlace.PizzaPlaceNotInSystemException;
import OOP.Provided.PizzaWorld;
import OOP.Solution.PizzaPlaceImpl;
import OOP.Solution.PizzaLoverImpl;
import OOP.Solution.PizzaWorldImpl;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Program {
	public static void main(String[] args) {
		checkArgumentsPlace();
		System.out.println("Passed checkArgumentsPlace");
		checkArgumentsLover();
		System.out.println("Passed checkArgumentsLover");
		checkArgumentsWorld();
		System.out.println("Passed checkArgumentsWorld");
		basicScenario1();
		System.out.println("Passed basicScenario1");
		basicScenario2();
		System.out.println("Passed basicScenario2");
		basicScenario3();
		System.out.println("Passed basicScenario3");
	}

	static void checkArgumentsPlace() {
		PizzaPlace pp1;
		
		try {
			pp1 = new PizzaPlaceImpl(1, "Place1", 100, new TreeSet<String>());
		} catch(Exception e) {
			System.out.println("Error checking constructor.");
			System.exit(1);
		}

		PizzaPlace pp2 = new PizzaPlaceImpl(1, "Place1");
		PizzaLover pl1 = new PizzaLoverImpl(1, "Lover1");

		try {
			int x = pp1.getId();
		} catch(Exception e) {
			System.out.println("Error checking getId.");
			System.exit(1);
		}
		try {
			int x = pp1.distance();
		} catch(Exception e) {
			System.out.println("Error checking distance.");
			System.exit(1);
		}
		try {
			pp1.rate(pl1, -1);
			System.out.println("Error checking rate.1");
			System.exit(1);
		} catch(RateRangeException e) {
			
		}
		try {
			pp1.rate(pl1, 6);
			System.out.println("Error checking rate.2");
			System.exit(1);
		} catch(RateRangeException e) {
			
		}
		try {
			int x = pp1.numberOfRates();
		} catch(Exception e) {
			System.out.println("Error checking numberOfRates.");
			System.exit(1);
		}
		try {
			int x = pp1.averageRating();
		} catch(Exception e) {
			System.out.println("Error checking averageRating.");
			System.exit(1);
		}
		try {
			boolean b = pp1.equals(null);
			if(b == true) {
				System.out.println("Error checking equals.1");
				System.exit(1);
			}
			b = pp1.equals(pp2);
			if(b == false) {
				System.out.println("Error checking equals.2");
				System.exit(1);	
			}
		} catch(Exception e) {
			
		}
		try {
			String x = pp1.toString();
			if(x.length() <= 0) {
				System.out.println("Error checking toString.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking toString.2");
			System.exit(1);
		}
		try {
			int x = pp1.compareTo(pp2);
		} catch(Exception e) {
			System.out.println("Error checking compareTo.");
			System.exit(1);
		}
	}

	static void checkArgumentsLover() {
		PizzaLover pl1;
		try {
			pl1 = new PizzaLoverImpl(1, "Lover1");
		} catch(Exception e) {
			System.out.println("Error checking constructor.");
			System.exit(1);
		}
		PizzaPlace pp1 = new PizzaPlaceImpl(1, "Place1");
		try {
			int x = pl1.getId();
		} catch(Exception e) {
			System.out.println("Error checking getId.");
			System.exit(1);
		}
		try {
			pl1.favorite(pp1);
			System.out.println("Error checking favorite.1");
			System.exit(1);
		} catch (UnratedFavoritePizzaPlaceException e) {
			
		}
		try {
			pl1.favorite(null);
			System.out.println("Error checking favorite.2");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			Collection<PizzaPlace> col = pl1.favorites();
		} catch(Exception e) {
			System.out.println("Error checking favorites.");
			System.exit(1);
		}
		try {
			pl1.addFriend(null);
			System.out.println("Error checking addFriend.1");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			pl1.addFriend(pl1);
			System.out.println("Error checking addFriend.2");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			Collection<PizzaLover> col = pl1.getFriends();
		} catch(Exception e) {
			System.out.println("Error checking getFriends.");
			System.exit(1);
		}
		try {
			Collection<PizzaLover> col = pl1.favoritesByRating(Integer.MAX_VALUE);
			if(col.size() > 0) {
				System.out.println("Error checking favoritesByRating.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking favoritesByRating.2");
			System.exit(1);
		}
		try {
			Collection<PizzaLover> col = pl1.favoritesByDist(-1);
			if(col.size() > 0) {
				System.out.println("Error checking favoritesByDist.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking favoritesByDist.2");
			System.exit(1);
		}
		try {
			boolean b = pl1.equals(pl2);
			if(b == false) {
				System.out.println("Error checking equals.1");
				System.exit(1);
			}
			b = pl1.equals(null);
			if(b == true) {
				System.out.println("Error checking equals.2");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking equals.3");
			System.exit(1);
		}
		try {
			String x = pl1.toString();
			if(x.length() <= 0) {
				System.out.println("Error checking toString.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking toString.2");
			System.exit(1);
		}
		try {
			int x = pl1.compareTo(pl2);
		} catch(Exception e) {
			System.out.println("Error checking compareTo.");
			System.exit(1);
		}
	}

	static void checkArgumentsWorld() {
		PizzaWorld world1;
		PizzaLover pl1, pl2;
		PizzaPlace pp1, pp2;
		try {
			world1 = new PizzaWorldImpl();
		} catch(Exception e) {
			System.out.println("Error checking constructor.");
			System.exit(1);
		}
		try {
			pl2 = world1.joinNetwork(1, "Lover1");
			System.out.println("Error checking joinNetwork.");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			pp1 = world1.addPizzaPlace(1, "place1", new TreeSet<String>());
			pp1 = world1.addPizzaPlace(1, "place1", new TreeSet<String>());
			System.out.println("Error checking addPizzaPlace.");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			Collection<PizzaLover> col = world1.registeredPizzaLovers();
			if(col == null) {
				System.out.println("Error checking registeredPizzaLovers.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking registeredPizzaLovers.2");
			System.exit(1);
		}
		try {
			Collection<PizzaPlace> col = world1.registeredPizzaPlaces();
			if(col == null) {
				System.out.println("Error checking registeredPizzaPlaces.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking registeredPizzaPlaces.2");
			System.exit(1);
		}
		try {
			pl2 = world1.getPizzaLover(-1);
			System.out.println("Error checking getPizzaLover.");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			pp2 = world1.getPizzaPlace(-1);
			System.out.println("Error checking getPizzaPlace.");
			System.exit(1);
		} catch(Exception e) {
			
		}

		pl2 = new PizzaLoverImpl(2, "Lover2");
		try {
			world1.addConnection(pl2, pl1);
			System.out.println("Error checking addConnection.1");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			world1.addConnection(pl1, pl1);
			System.out.println("Error checking addConnection.2");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			world1.favoritesByRating(pl2);
			System.out.println("Error checking favoritesByRating.");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			world1.favoritesByDist(pl2);
			System.out.println("Error checking favoritesByDist.");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			String x = world1.toString();
			if(x.length() <= 0) {
				System.out.println("Error checking toString.1");
				System.exit(1);
			}
		} catch(Exception e) {
			System.out.println("Error checking toString.2");
			System.exit(1);
		}
		pp2 = new PizzaPlaceImpl(2, "place2");
		try {
			boolean b = world1.getRecommendation(pl1, pp1, -1);
			System.out.println("Error checking getRecommendation.1");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			boolean b = world1.getRecommendation(pl2, pp1, 1);
			System.out.println("Error checking getRecommendation.2");
			System.exit(1);
		} catch(Exception e) {
			
		}
		try {
			boolean b = world1.getRecommendation(pl1, pp2, 4);
			System.out.println("Error checking getRecommendation.3");
			System.exit(1);
		} catch(Exception e) {
			
		}
	}

	static void basicScenario1() {
		PizzaLover pl1, pl2, pl3, pl4, pl5;
		pl1 = new PizzaLoverImpl(1, "Lover1");
		pl2 = new PizzaLoverImpl(2, "Lover2");
		pl3 = new PizzaLoverImpl(3, "Lover3");
		pl4 = new PizzaLoverImpl(4, "Lover4");
		pl5 = new PizzaLoverImpl(5, "Lover5");
		boolean test1 = (pl1.getId() == 1 && pl2.getId() == 2 && pl3.getId() == 3 && pl4.getId() == 4 && pl5.getId() == 5);
		boolean test2 = (pl1.favorites().size() + pl2.favorites().size() + pl3.favorites().size() + pl4.favorites().size() + pl5.favorites().size() == 0);
		if(!test1) {
			System.out.println("Error test1");
			System.exit(1);
		}
		if(!test2) {
			System.out.println("Error test2");
			System.exit(1);
		}
		PizzaPlace pp1, pp2, pp3;
		pp1 = new PizzaPlaceImpl(1, "Place1", 100, new TreeSet<String>());
		pp2 = new PizzaPlaceImpl(2, "Place2", 200, new TreeSet<String>());
		pp3 = new PizzaPlaceImpl(3, "Place3", 500, new TreeSet<String>());
		boolean test3 = (pp1.averageRating() + pp2.averageRating() + pp3.averageRating() == 0);
		if(!test3) {
			System.out.println("Error test3");
			System.exit(1);
		}
		boolean test4 = (pp1.distance() == 100 && pp2.distance() == 200 && pp3.distance() == 500);
		if(!test4) {
			System.out.println("Error test4");
			System.exit(1);
		}
		pp1.rate(pl1, 1).rate(pl2, 2).rate(pl3, 3);
		boolean test5 = (pp1.averageRating() == 2);
		if(!test5) {
			System.out.println("Error test5");
			System.exit(1);
		}
		pl1.favorite(pp1); pl2.favorite(pp1); pl3.favorite(pp1);
		boolean test6 = (pl1.favorites().contains(pp1) && pl2.favorites().contains(pp1) && pl3.favorites().contains(pp1));
		boolean test7 = !(pl1.favorites().contains(pp2) || pl2.favorites.contains(pp2) || pl3.favorites.contains(pp2));
		if(!test6) {
			System.out.println("Error test6");
			System.exit(1);
		}
		if(!test7) {
			System.out.println("Error test7");
			System.exit(1);
		}
		pp2.rate(pl1, 2).rate(pl2, 3);
		pp1.rate(pl4, 5);
		boolean test8 = (pp1.numberOfRates() == 4 && pp2.numberOfRates() == 2);
		boolean test9 = (pp1.averageRating() == 11/4 && pp2.averageRating() == 2.5);
		if(!test8) {
			System.out.println("Error test8");
			System.exit(1);
		}
		if(!test9) {
			System.out.println("Error test9");
			System.exit(1);
		}
		try {
			pl5.favorite(pp1);
			System.out.println("Error test10 part1");
			System.exit(1);
		} catch(UnratedFavoritePizzaPlaceException e) {

		}
		pp1.rate(pl5, 4);
		try {
			pl5.favorite(pp1);
		} catch(UnratedFavoritePizzaPlaceException e) {
			System.out.println("Error test10 part2");
			System.exit(1);
		}
		boolean test11 = (pp1.averageRating() == 15/4);
		pp1.rate(pl5, 2);
		boolean test12 = (pp1.averageRating() == 13/4);
		if(!test11) {
			System.out.println("Error test11");
			System.exit(1);
		}
		if(!test12) {
			System.out.println("Error test12");
			System.exit(1);
		}
	}

	static void basicScenario2() {
		PizzaPlace pp1, pp2, pp3, pp4, pp5, pp6;
		Set<String> menu1 = new TreeSet<String>();
		Set<String> menu2 = new TreeSet<String>();
		Set<String> menu34 = new TreeSet<String>();
		Set<String> menu56 = new TreeSet<String>();
		menu1.add("Hamburger"); menu1.add("Fries"); menu1.add("Soda");
		menu2.add("Pasta Pene"); menu2.add("Pasta Ravioli"); menu2.add("Lasagna");
		menu34.add("Mushroom Soup"); menu34.add("Tofu Soup"); menu34.add("Pad-Thai");
		menu56.add("Tapas"); menu56.add("Patatas Fritas"); menu56.add("Quesadilla");
		pp1 = new PizzaPlaceImpl(1, "Place1", 100, menu1);
		pp2 = new PizzaPlaceImpl(2, "Place2", 200, menu2);
		pp3 = new PizzaPlaceImpl(3, "Place3", 1000, menu34);
		pp4 = new PizzaPlaceImpl(4, "Place4", 300, menu34);
		pp5 = new PizzaPlaceImpl(5, "Place5", 400, menu56);
		pp6 = new PizzaPlaceImpl(6, "Place6", 700, menu56);
		PizzaLover pl1, pl2, pl3;
		pl1 = new PizzaLoverImpl(1, "Lover1");
		pl2 = new PizzaLoverImpl(2, "Lover2");
		pl2 = new PizzaLoverImpl(3, "Lover3");

		boolean test1 = (pp1.distance() == 100 && pp2.distance() == 200 && pp3.distance() == 1000 && pp4.distance() == 300 && pp5.distance() == 400 && pp6.distance() == 700);
		if(!test1) {
			System.out.println("Error test1");
			System.exit(1);
		}

		pp1.rate(pl1, 1).rate(pl2, 1);
		pp2.rate(pl1, 2).rate(pl2, 0);
		pp3.rate(pl1, 4).rate(pl2, 5);
		pp4.rate(pl1, 5).rate(pl2, 0);
		pp5.rate(pl1, 3).rate(pl2, 3);
		pp6.rate(pl1, 0).rate(pl2, 5);
		boolean test2 = (pp1.averageRating() == 1 
						&& pp2.averageRating() == 1 
						&& pp3.averageRating() == 4.5 
						&& pp4.averageRating() == 2.5 
						&& pp5.averageRating() == 3 
						&& pp6.averageRating() == 2.5);
		if(!test2) {
			System.out.println("Error test2");
			System.exit(1);
		}

		pl1.favorite(pp1).favorite(pp2).favorite(pp3).favorite(pp4).favorite(pp5).favorite(pp6);
		pl2.favorite(pp6).favorite(pp5).favorite(pp4).favorite(pp3);
		Collection<PizzaPlace> favs_1 = pl1.favorites();
		Collection<PizzaPlace> favs_2 = pl2.favorites();
		boolean test3 = (favs_1.contains(pp1) && favs_1.contains(pp2) && favs_1.contains(pp3) && favs_1.contains(pp4) && favs_1.contains(pp5) && favs_1.contains(pp6));
		boolean test4 = (favs_2.contains(pp3) && favs_2.contains(pp4) && favs_2.contains(pp5) && favs_2.contains(pp6));
		if(!test3) {
			System.out.println("Error test3");
			System.exit(1);
		}
		if(!test4) {
			System.out.println("Error test4");
			System.exit(1);
		}

		String favs_names = "";
		Collection<PizzaPlace> favs1_dist = pl1.favoritesByDist(10000);
		for(PizzaPlace place : favs1_dist) {
			PizzaPlaceImpl actual = (PizzaPlaceImpl)(place);
			favs_names += actual.getName() + ",";
		}
		boolean test5 = (favs_names.equals("Place1,Place2,Place4,Place5,Place6,Place3,"));
		if(!test5) {
			System.out.println("Error test5");
			System.exit(1);
		}

		favs_names = "";
		Collection<PizzaPlace> favs2_dist = pl2.favoritesByDist(10000);
		for(PizzaPlace place : favs2_dist) {
			PizzaPlaceImpl actual = (PizzaPlaceImpl)(place);
			favs_names += actual.getName() + ",";
		}
		boolean test6 = (favs_names.equals("Place4,Place5,Place6,Place3,"));
		if(!test6) {
			System.out.println("Error test6");
			System.exit(1);
		}

		favs_names = "";
		Collection<PizzaPlace> favs1_rate = pl1.favoritesByRating(0);
		for(PizzaPlace place : favs1_rate) {
			PizzaPlaceImpl actual = (PizzaPlaceImpl)(place);
			favs_names += actual.getName() + ",";
		}
		boolean test7 = (favs_names.equals("Place3,Place5,Place4,Place6,Place1,Place2,"));
		if(!test7) {
			System.out.println("Error test7");
			System.exit(1);
		}

		favs_names = "";
		Collection<PizzaPlace> favs2_dist2 = pl2.favoritesByDist(500);
		for(PizzaPlace place : favs2_dist2) {
			PizzaPlaceImpl actual = (PizzaPlaceImpl)(place);
			favs_names += actual.getName() + ",";
		}
		boolean test8 = (favs_names.equals("Place4,Place5,"));
		if(!test6) {
			System.out.println("Error test8");
			System.exit(1);
		}

		favs_names = "";
		Collection<PizzaPlace> favs1_rate2 = pl1.favoritesByRating(3);
		for(PizzaPlace place : favs1_rate2) {
			PizzaPlaceImpl actual = (PizzaPlaceImpl)(place);
			favs_names += actual.getName() + ",";
		}
		boolean test9 = (favs_names.equals("Place3,Place5,"));
		if(!test7) {
			System.out.println("Error test9");
			System.exit(1);
		}

		pl1.addFriend(pl2);
		boolean test10 = (pl1.getFriends().contains(pl2) && pl2.getFriends().contains(pl1));
		if(!test10) {
			System.out.println("Error test10");
			System.exit(1);
		}

		try {
			pl3.addFriend(pl3);
			System.out.println("Error test11");
			System.exit(1);
		} catch(SamePizzaLoverException e) {
			//All good
		}

		try {
			pl2.addFriend(pl1);
			System.out.println("Error test12");
			System.exit(1);
		} catch(ConnectionAlreadyExistsException e) {
			//All good
		}

		pl3.addFriend(pl2);
		boolean test13 = (pl3.getFriends().contains(pl2) && pl2.getFriends().contains(pl3));
		boolean test14 = (!(pl3.getFriends().contains(pl1)) && !(pl1.getFriends().contains(pl3)));
		boolean test15 = !(pl1.getFriends().contains(pl1) || pl2.getFriends().contains(pl2) || pl3.getFriends().contains(pl3));
		if(!test13) {
			System.out.println("Error test13");
			System.exit(1);
		}
		if(!test14) {
			System.out.println("Error test14");
			System.exit(1);
		}
		if(!test15) {
			System.out.println("Error test15");
			System.exit(1);
		}
	}

	static void basicScenario3() {
		PizzaWorld world = new PizzaWorldImpl();

	}
}