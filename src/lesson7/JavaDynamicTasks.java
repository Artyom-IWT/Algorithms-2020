package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int fLength = first.length();
        int sLength = second.length();
        int[][] array = new int[fLength + 1][sLength + 1];
        for (int i = fLength; i >= 0; i--)
            for (int j = sLength; j >= 0; j--) {
                if (i == fLength || j == sLength) array[i][j] = 0;
                else if (first.charAt(i) == second.charAt(j)) array[i][j] = array[i + 1][j + 1] + 1;
                else array[i][j] = Math.max(array[i][j + 1], array[i + 1][j]);
            }
        StringBuilder sequence = new StringBuilder();
        int i = 0;
        int j = 0;
        while (i < fLength && j < sLength) {
            if (first.charAt(i) == second.charAt(j)) {
                sequence.append(first.charAt(i));
                i++;
                j++;
            }
            else if (array[i + 1][j] >= array[i][j + 1]) i++;
            else j++;
        }
        return sequence.toString();
    } // Трудоёмкость - O(N*M); Ресурсоёмкость - O(N*M); N и M - длины строк.

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        int[] previous = new int[list.size()];
        int[] current = new int[list.size() + 1];
        int length = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            int l = 1;
            int r = length;
            while (l <= r) {
                int m = (l + r) / 2;
                if (list.get(current[m]) < list.get(i)) r = m - 1;
                else l = m + 1;
            }
            previous[i] = current[l - 1];
            current[l] = i;
            if (l > length) length = l;
        }
        List<Integer> sub = new ArrayList<>();
        int p = current[length];
        for (int i = length - 1; i >= 0; i--) {
            sub.add(list.get(p));
            p = previous[p];
        }
        return sub;
    } // Трудоёмкость - O(N*logN); Ресурсоёмкость - O(N)

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
