package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}

	// This might not be needed
	public Card() {
		// TODO Auto-generated constructor stub
	}

	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return cardType;
	}

	@Override
	public String toString() {
		return getCardName();
	}
}
