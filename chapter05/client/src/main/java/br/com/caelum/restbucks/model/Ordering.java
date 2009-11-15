package br.com.caelum.restbucks.model;

import br.com.caelum.restbucks.model.Item.Coffee;
import br.com.caelum.restbucks.model.Item.Milk;
import br.com.caelum.restbucks.model.Item.Size;

public class Ordering {

	private final Order order = new Order();

	public static Ordering order() {
		return new Ordering();
	}

	public Ordering withRandomItems() {
		int quantity = random(2, 5);
		for (int i = 0; i < quantity; i++) {
			Item item = new Item(random(Coffee.class), random(1, 3), random(Milk.class), random(Size.class));
		}
		return this;
	}

	private <T extends Enum> T random(Class<T> type) {
		return type.getEnumConstants()[random(0,type.getEnumConstants().length)];
	}

	private int random(int from, int to) {
		return (int) (Math.random() * (to-from) +from);
	}

	public Order build() {
		return this.order;
	}

}
