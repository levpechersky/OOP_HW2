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

import java.util.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class PizzaWorldTest {
	// when changing those - update ToStringTest accordingly
	private static final Set<String> traditional_pizzas = new HashSet<>(Arrays.asList(
			"Margarita", "Hawaiian", "Greek", "Napoli", "Sicilian", "Marinara"
	));
	private static final Set<String> meat_topping_pizzas = new HashSet<>(Arrays.asList(
			"Chicken", "Bacon", "Ground Beef", "Pepperoni", "Anchovies"
	));
	private static final Set<String> veggie_topping_pizzas = new HashSet<>(Arrays.asList(
			"Mushrooms", "Tomatoes", "Onion", "Olives"
	));
	private static final Set<String> complements = new HashSet<>(Arrays.asList(
			"Calzone", "Fries", "Onion rings", "Coca-cola", "Cola zero"
	));

	@Test
	public void CreationTest() {
		PizzaWorld world = new PizzaWorldImpl();
		assertTrue(world.registeredPizzaLovers().isEmpty());
		assertTrue(world.registeredPizzaPlaces().isEmpty());
	}

	@Test
	public void toStringTest() {
		PizzaWorld world = new PizzaWorldImpl();

		// Initial state:
		String emptyWorldExpectedStr = "Registered pizza lovers: .\n" +
				"Registered pizza places: .\n" +
				"Pizza lovers:\n" +
				"End pizza lovers.";
		assertEquals(world.toString(), emptyWorldExpectedStr);

		try { // Let's add places:
			world.addPizzaPlace(10, "Italiano", 125, traditional_pizzas);
			world.addPizzaPlace(20, "Hut", 15, veggie_topping_pizzas);
			world.addPizzaPlace(30, "Dominos", 650, meat_topping_pizzas);
			world.addPizzaPlace(40, "BadPizza", 350, complements);
		} catch (PizzaPlaceAlreadyInSystemException e){
			fail("You have probably messed something up in a test");
		}

		try { // Now add users:
			world.joinNetwork(12345, "Leonardo");
			world.joinNetwork(23456, "Donatello");
			world.joinNetwork(56789, "Michelangelo");
			world.joinNetwork(45678, "Rafael");
			// obviously, the following two aren't friends of TNMT:
			world.joinNetwork(666555, "Shredder");
			world.joinNetwork(333444, "Krang");
		} catch (PizzaLoverAlreadyInSystemException e){
			fail("You have probably messed something up in a test");
		}

		try { // Now let's make friends:
			// Make TNMT a clique: Leonardo with all
			world.addConnection(world.getPizzaLover(12345), world.getPizzaLover(23456));
			world.addConnection(world.getPizzaLover(12345), world.getPizzaLover(56789));
			world.addConnection(world.getPizzaLover(12345), world.getPizzaLover(45678));
			// Make TNMT a clique: Donatello with all
			world.addConnection(world.getPizzaLover(23456), world.getPizzaLover(12345));
			world.addConnection(world.getPizzaLover(23456), world.getPizzaLover(56789));
			world.addConnection(world.getPizzaLover(23456), world.getPizzaLover(45678));
			// Make TNMT a clique: Michelangelo with all
			world.addConnection(world.getPizzaLover(56789), world.getPizzaLover(12345));
			world.addConnection(world.getPizzaLover(56789), world.getPizzaLover(23456));
			world.addConnection(world.getPizzaLover(56789), world.getPizzaLover(45678));
			// Make TNMT a clique: Rafael with all
			world.addConnection(world.getPizzaLover(45678), world.getPizzaLover(12345));
			world.addConnection(world.getPizzaLover(45678), world.getPizzaLover(23456));
			world.addConnection(world.getPizzaLover(45678), world.getPizzaLover(56789));

			// Krang is a friend of Shredder, but not the other way around:
			world.addConnection(world.getPizzaLover(666555), world.getPizzaLover(333444));
		}catch (Exception e){
			fail("You have probably messed something up in a test");
		}

		String expectedStr = "Registered pizza lovers: 12345, 23456, 45678, 56789, 333444, 666555.\n" +
				"Registered pizza places: 10, 20, 30, 40.\n" +
				"Pizza lovers:\n" +
				"12345 -> [23456, 45678, 56789].\n" +
				"23456 -> [12345, 45678, 56789].\n" +
				"45678 -> [12345, 23456, 56789].\n" +
				"56789 -> [12345, 23456, 45678].\n" +
				"333444 -> [].\n" +
				"666555 -> [333444].\n" +
				"End pizza lovers.";
		assertEquals(world.toString(), expectedStr);
	}

}