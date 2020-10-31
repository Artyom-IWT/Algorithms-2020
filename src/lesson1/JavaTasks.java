package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     */
    static public void sortTimes(String inputName, String outputName) throws IOException, ParseException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(inputName));
        String line = reader.readLine();
        List<Integer> times = new ArrayList<>();
        while (line != null) {
            if (!line.matches("((0\\d)|(1[0-2])):([0-5])(\\d):([0-5])(\\d) (AM|PM)"))
                throw new IllegalArgumentException();
            String[] array = line.split("[:\\sAPM]");
            int seconds = 0;
            int multiplier = 3600;
            for (String s : array) {
                int i = Integer.parseInt(s);
                if (i == 12 && multiplier == 3600) seconds += 0;
                else seconds += i * multiplier;
                multiplier /= 60;
            }
            if (line.contains("AM")) times.add(seconds);
            else if(line.contains("PM")) times.add(seconds + 12 * 3600);
            line = reader.readLine();
        }
        int[] timesA = times.stream().mapToInt(i -> i).toArray();
        Arrays.sort(timesA);
        DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        DateFormat s = new SimpleDateFormat("ss");
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName));
        for (int i = 0; i < timesA.length; i++) {
            Date date;
            String out;
            date = s.parse(String.valueOf(timesA[i]));
            out = df.format(date);
            writer.write(out);
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *[А-Яа-яЁё\s\-\d]+
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException{
        BufferedReader reader = Files.newBufferedReader(Paths.get(inputName));
        Comparator<String> comparator = (o1, o2) -> {
            String s1 = o1.replaceAll("[\\d ]", "");
            String s2 = o2.replaceAll("[\\d ]", "");
            if (s1.equals(s2)) {
                Integer int1 = Integer.parseInt(o1.replaceAll("[^\\d]", ""));
                Integer int2 = Integer.parseInt(o2.replaceAll("[^\\d]", ""));
                return int1.compareTo(int2);
            }
            return o1.compareTo(o2);
        };
        Map<String, TreeSet<String>> map = new TreeMap<>(comparator);
        String line = reader.readLine();
        while (line != null) {
            if (!line.matches("([А-Яа-яЁё\\-]+ )+- ([А-Яа-яЁё\\-]+ )+\\d+"))
                throw new IllegalArgumentException();
            String[] array = line.split(" - ");
            if (!map.containsKey(array[1])) map.put(array[1], new TreeSet<>());
            map.get(array[1]).add(array[0]);
            line = reader.readLine();
        }
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName));
        for (Map.Entry<String, TreeSet<String>> entry : map.entrySet()) {
            String value = entry.getValue().toString();
            value = value.substring(1, value.length() - 1);
            writer.write(entry.getKey() + " - " + value);
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException{
        BufferedReader reader = Files.newBufferedReader(Paths.get(inputName));
        List<Double> doubles = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            double d = Double.parseDouble(line);
            if (d > 500.0 || d < -273.0) throw new IllegalArgumentException();
            doubles.add(d);
            line = reader.readLine();
        }
        double[] doublesA = doubles.stream().mapToDouble(d -> d).toArray();
        Arrays.sort(doublesA);
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName));
        for (double d : doublesA) {
            writer.write(String.valueOf(d));
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
