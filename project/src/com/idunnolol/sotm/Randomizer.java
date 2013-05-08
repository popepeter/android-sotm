package com.idunnolol.sotm;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.idunnolol.sotm.data.Card;
import com.idunnolol.sotm.data.GameSetup;
import com.idunnolol.sotm.data.Db;
import com.idunnolol.sotm.data.Card.Type;

public class Randomizer {

	private Random mRand;

	// The base game setup; when we randomize, we fill in any cards set to RANDOM
	private GameSetup mBaseGameSetup;

	public Randomizer(GameSetup baseGameSetup) {
		mBaseGameSetup = baseGameSetup;
		mRand = new Random();
	}

	/**
	 * We might not be able to randomize if there aren't enough options available
	 */
	public boolean canRandomize() {
		return getFirstLackingType() == null;
	}

	/**
	 * @return the first type without enough enabled cards, or null if we're good to go
	 */
	public Type getFirstLackingType() {
		if (Db.getCards(Type.HERO).size() < mBaseGameSetup.getHeroCount()) {
			return Type.HERO;
		}
		else if (Db.getCards(Type.VILLAIN).size() == 0) {
			return Type.VILLAIN;
		}
		else if (Db.getCards(Type.ENVIRONMENT).size() == 0) {
			return Type.ENVIRONMENT;
		}

		return null;
	}

	public GameSetup randomize() {
		GameSetup gameSetup = new GameSetup(mBaseGameSetup);

		// Go through all heroes, villains and environments
		// and randomly select cards to fill in when the
		// card is "random"
		Set<Card> usedCards = new HashSet<Card>();
		List<Card> heroes = gameSetup.getHeroes();
		int size = heroes.size();
		for (int a = 0; a < size; a++) {
			Card hero = heroes.get(a);
			if (hero != Card.RANDOM) {
				usedCards.add(hero);
			}
			else {
				Card card;
				do {
					card = getRandomCard(Type.HERO);
				}
				while (usedCards.contains(card));

				gameSetup.setHero(a, card);
				usedCards.add(card);
			}
		}

		if (gameSetup.getVillain() == Card.RANDOM) {
			gameSetup.setVillain(getRandomCard(Type.VILLAIN));
		}

		if (gameSetup.getEnvironment() == Card.RANDOM) {
			gameSetup.setEnvironment(getRandomCard(Type.ENVIRONMENT));
		}

		return gameSetup;
	}

	private Card getRandomCard(Type type) {
		List<Card> cards = Db.getCards(type);
		int index = mRand.nextInt(cards.size());
		return cards.get(index);
	}
}
