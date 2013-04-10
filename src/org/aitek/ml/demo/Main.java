package org.aitek.ml.demo;

import java.util.ArrayList;
import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.User;
import org.aitek.ml.core.similarity.ManhattanDistance;
import org.aitek.ml.core.similarity.Measurable;

public class Main {

	public static void main(String[] args) {

		User lisa = new User("Lisa Rose");
		User gene = new User("Gene Seymour");
		User michael = new User("Michael Phillips");
		User claudia = new User("Claudia Puig");
		User mick = new User("Mick LaSalle");
		User jack = new User("Jack Matthews");

		List<Rankable> movies = new ArrayList<Rankable>();
		Rankable lady = new Item("Lady in the Water");
		movies.add(lady);
		Rankable snakes = new Item("Snakes on a plane");
		movies.add(snakes);
		Rankable just = new Item("Just my luck");
		movies.add(just);
		Rankable superman = new Item("Superman returns");
		movies.add(superman);
		Rankable night = new Item("Night Listener");
		movies.add(night);
		Rankable you = new Item("You, me and dupree");
		movies.add(you);

		lisa.setVote(lady, 25);
		lisa.setVote(snakes, 35);
		lisa.setVote(just, 30);
		lisa.setVote(superman, 35);
		lisa.setVote(you, 25);
		lisa.setVote(night, 30);

		gene.setVote(lady, 30);
		gene.setVote(snakes, 35);
		gene.setVote(just, 15);
		gene.setVote(superman, 50);
		gene.setVote(night, 30);
		gene.setVote(you, 35);

		michael.setVote(lady, 25);
		michael.setVote(snakes, 30);
		michael.setVote(superman, 35);
		michael.setVote(night, 40);

		claudia.setVote(snakes, 35);
		claudia.setVote(just, 30);
		claudia.setVote(superman, 40);
		claudia.setVote(you, 25);
		claudia.setVote(night, 45);

		mick.setVote(lady, 30);
		mick.setVote(snakes, 40);
		mick.setVote(just, 20);
		mick.setVote(superman, 30);
		mick.setVote(night, 30);
		mick.setVote(you, 20);

		jack.setVote(lady, 30);
		jack.setVote(snakes, 40);
		jack.setVote(superman, 50);
		jack.setVote(night, 30);
		jack.setVote(you, 35);

		Measurable euclidean = new ManhattanDistance();

		System.out.println("Euclidean Lisa - Gene:" + euclidean.getScore(movies, lisa, gene));

	}
}
