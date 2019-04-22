package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	public Card personCard;
	public Card roomCard;
	public Card weaponCard;
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	public Solution(Card person, Card room, Card weapon) {
		this.personCard = person;
		this.roomCard = room;
		this.weaponCard = weapon;
		this.person = personCard.getCardName();
		this.room = roomCard.getCardName();
		this.weapon = weaponCard.getCardName();
	}
	
	@Override
	public String toString() {
		return person + " in the " + room + " with the " + weapon;
	}
	
}
