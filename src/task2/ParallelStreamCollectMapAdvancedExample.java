/**
 * Создайте коллекцию студентов, где каждый студент содержит информацию о предметах, которые он изучает, и его оценках
 * по этим предметам.
 * Используйте Parallel Stream для обработки данных и создания Map, где ключ - предмет, а значение - средняя оценка по
 * всем студентам.
 * Выведите результат: общую Map с средними оценками по всем предметам.
 */

package task2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParallelStreamCollectMapAdvancedExample {
	public static void main(String[] args) {
		List<Student> students = Arrays.asList(
				new Student("Student1", Map.of("Math", 90, "Physics", 85)),
				new Student("Student2", Map.of("Math", 95, "Physics", 88)),
				new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
				new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
		);

		// Используем parallel stream для параллельной обработки данных
		Map<String, Double> averageGradesBySubject = students.parallelStream()
				// Преобразуем список студентов в поток пар предмет-оценка
				.flatMap(student -> student.getGrades().entrySet().stream())
				// Группируем по предмету и вычисляем среднее значение
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,    // Группируем по предмету
						Collectors.averagingDouble(Map.Entry::getValue)    // Вычисляем среднюю оценку
				));

		// Выводим результаты
		System.out.println("Средние оценки по предметам:");
		averageGradesBySubject.forEach((subject, average) ->
				System.out.printf("%s: %.2f%n", subject, average));
	}
}