/**
 * Предположим, у нас есть список заказов, и каждый заказ представляет собой продукт и его стоимость. Задача состоит в
 * использовании Stream API и коллекторов для решения следующих задач:
 * Создайте список заказов с разными продуктами и их стоимостями.
 * Группируйте заказы по продуктам.
 * Для каждого продукта найдите общую стоимость всех заказов.
 * Отсортируйте продукты по убыванию общей стоимости.
 * Выберите три самых дорогих продукта.
 * Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
 */
package task1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollectorsExample {
	public static void main(String[] args) {
		// Шаг 1: Создание списка заказов
		System.out.println("Шаг 1: Создание списка заказов");
		List<Order> orders = List.of(
				new Order("Laptop", 1200.0),
				new Order("Smartphone", 800.0),
				new Order("Laptop", 1500.0),
				new Order("Tablet", 500.0),
				new Order("Smartphone", 900.0)
		);

		System.out.println("Список всех заказов:");
		orders.forEach(System.out::println);
		System.out.println("\n" + "=".repeat(50) + "\n");

		// Шаг 2: Группировка заказов по продуктам
		System.out.println("Шаг 2: Группировка заказов по продуктам");
		Map<String, List<Order>> ordersByProduct = orders.stream()
				.collect(Collectors.groupingBy(Order::getProduct));

		System.out.println("Заказы, сгруппированные по продуктам:");
		ordersByProduct.forEach((product, ordersList) -> {
			System.out.println("\nПродукт: " + product);
			ordersList.forEach(order -> System.out.println("  " + order));
		});
		System.out.println("\n" + "=".repeat(50) + "\n");

		// Шаг 3: Подсчет общей стоимости для каждого продукта
		System.out.println("Шаг 3: Подсчет общей стоимости для каждого продукта");
		Map<String, Double> totalCostByProduct = orders.stream()
				.collect(Collectors.groupingBy(
						Order::getProduct,
						Collectors.summingDouble(Order::getCost)
				));

		System.out.println("Общая стоимость заказов по продуктам:");
		totalCostByProduct.forEach((product, total) ->
				System.out.printf("%s: %.2f\n", product, total)
		);
		System.out.println("\n" + "=".repeat(50) + "\n");

		// Шаг 4: Сортировка продуктов по убыванию общей стоимости
		System.out.println("Шаг 4: Сортировка продуктов по убыванию общей стоимости");
		List<Map.Entry<String, Double>> sortedProducts = totalCostByProduct.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Double>comparingByValue().reversed())
				.toList();

		System.out.println("Продукты, отсортированные по убыванию общей стоимости:");
		sortedProducts.forEach(entry ->
				System.out.printf("%s: %.2f\n", entry.getKey(), entry.getValue())
		);
		System.out.println("\n" + "=".repeat(50) + "\n");

		// Шаг 5: Выбор трех самых дорогих заказов
		System.out.println("Шаг 5: Выбор трех самых дорогих заказов");
		List<Order> topThreeOrders = orders.stream()
				.sorted((o1, o2) -> Double.compare(o2.getCost(), o1.getCost()))
				.limit(3)
				.toList();

		System.out.println("Три самых дорогих заказа:");
		topThreeOrders.forEach(System.out::println);
		System.out.println("\n" + "=".repeat(50) + "\n");

		// Шаг 6: Вывод итогового результата
		System.out.println("Шаг 6: Вывод итогового результата");

		double totalTopThree = topThreeOrders.stream()
				.mapToDouble(Order::getCost)
				.sum();

		System.out.printf("Общая стоимость трех самых дорогих заказов: %.2f\n", totalTopThree);
	}
}