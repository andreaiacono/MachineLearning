package org.aitek.ml.demo;

import java.util.ArrayList;
import java.util.List;

import org.aitek.ml.domain.Book;
import org.aitek.ml.domain.Item;
import org.aitek.ml.domain.Reader;
import org.aitek.ml.similarity.ManhattanDistance;
import org.aitek.ml.similarity.Similarity;

public class Main {

	public static void main(String[] args) {

		Reader lisa = new Reader("Lisa Rose");
		Reader gene = new Reader("Gene Seymour");
		Reader michael = new Reader("Michael Phillips");
		Reader claudia = new Reader("Claudia Puig");
		Reader mick = new Reader("Mick LaSalle");
		Reader jack = new Reader("Jack Matthews");

		List<Item> movies = new ArrayList<Item>();
		Item lady = new Book("Lady in the Water");
		movies.add(lady);
		Item snakes = new Book("Snakes on a plane");
		movies.add(snakes);
		Item just = new Book("Just my luck");
		movies.add(just);
		Item superman = new Book("Superman returns");
		movies.add(superman);
		Item night = new Book("Night Listener");
		movies.add(night);
		Item you = new Book("You, me and dupree");
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

		Similarity euclidean = new ManhattanDistance();

		System.out.println("Euclidean Lisa - Gene:" + euclidean.getDistanceBetweenVoters(movies, lisa, gene));

	}
}
