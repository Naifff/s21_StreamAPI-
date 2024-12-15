package task3;

import java.util.concurrent.RecursiveTask;

class FactorialTask extends RecursiveTask<Long> {
	// Диапазон чисел для вычисления произведения
	private final int start;
	private final int end;

	// Конструктор для начального создания задачи
	public FactorialTask(int n) {
		this.start = 1;
		this.end = n;
	}

	// Конструктор для создания подзадач с конкретным диапазоном
	private FactorialTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		// Базовый случай: если начало равно концу,
		// значит у нас одно число - возвращаем его
		if (start == end) {
			return (long) start;
		}

		// Базовый случай: если диапазон содержит два числа,
		// просто перемножаем их
		if (end - start == 1) {
			return (long) start * end;
		}

		// Находим середину диапазона для разделения задачи
		int mid = start + (end - start) / 2;

		// Создаем подзадачу для первой половины диапазона
		FactorialTask leftTask = new FactorialTask(start, mid);
		// Запускаем первую подзадачу асинхронно
		leftTask.fork();

		// Создаем подзадачу для второй половины диапазона
		FactorialTask rightTask = new FactorialTask(mid + 1, end);
		// Вычисляем вторую подзадачу в текущем потоке
		Long rightResult = rightTask.compute();

		// Ожидаем завершения первой подзадачи и получаем её результат
		Long leftResult = leftTask.join();

		// Возвращаем произведение результатов подзадач
		return leftResult * rightResult;
	}
}