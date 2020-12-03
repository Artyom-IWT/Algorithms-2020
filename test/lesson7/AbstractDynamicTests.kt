package lesson7

import java.util.*
import kotlin.test.assertEquals

abstract class AbstractDynamicTests {
    fun longestCommonSubSequence(longestCommonSubSequence: (String, String) -> String) {
        assertEquals("", longestCommonSubSequence("мой мир", "я"))
        assertEquals("1", longestCommonSubSequence("1", "1"))
        assertEquals("13", longestCommonSubSequence("123", "13"))
        assertEquals("здс", longestCommonSubSequence("здравствуй мир", "мы здесь"))
        assertEquals("emt ole", longestCommonSubSequence("nematode knowledge", "empty bottle"))
        val expectedLength = "e kerwelkkd r".length
        assertEquals(
            expectedLength, longestCommonSubSequence(
                "oiweijgw kejrhwejelkrw kjhdkfjs hrk",
                "perhkhk lerkerorwetp lkjklvvd durltr"
            ).length, "Answer must have length of $expectedLength, e.g. 'e kerwelkkd r' or 'erhlkrw kjk r'"
        )
        val expectedLength2 = """ дд саы чтых,
евшнео ваа се сви дн.
        """.trimIndent().length
        assertEquals(
            expectedLength2, longestCommonSubSequence(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
                """.trimIndent()
            ).length, "Answer must have length of $expectedLength2"
        )
        assertEquals("мони", longestCommonSubSequence("Схемотехника", "Программирование"))
        assertEquals(4, longestCommonSubSequence("Схемотехника", "Программирование").length)
        assertEquals("ие", longestCommonSubSequence("Бинарное", "бизе"))
        assertEquals(2, longestCommonSubSequence("Бинарное", "Дерево").length)
    }

    fun longestIncreasingSubSequence(longestIncreasingSubSequence: (List<Int>) -> List<Int>) {
        assertEquals(listOf(), longestIncreasingSubSequence(listOf()))
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1)))
        assertEquals(listOf(1, 2), longestIncreasingSubSequence(listOf(1, 2)))
        assertEquals(listOf(2), longestIncreasingSubSequence(listOf(2, 1)))
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            longestIncreasingSubSequence(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )
        assertEquals(listOf(2, 8, 9, 12), longestIncreasingSubSequence(listOf(2, 8, 5, 9, 12, 6)))
        assertEquals(
            listOf(23, 34, 56, 87, 91, 98, 140, 349), longestIncreasingSubSequence(
                listOf(
                    23, 76, 34, 93, 123, 21, 56, 87, 91, 12, 45, 98, 140, 12, 5, 38, 349, 65, 94,
                    45, 76, 15, 99, 100, 88, 84, 35, 88
                )
            )
        )
        val random = Random()
        for (i in 0..9) {
            val l = mutableListOf<Int>()
            for (j in 0..19) l.add(random.nextInt(20))
            assertEquals(longestIncreasingSubSequence(l), longestIncreasingSubSequence(l))
        }
    }

    fun shortestPathOnField(shortestPathOnField: (String) -> Int) {
        assertEquals(1, shortestPathOnField("input/field_in2.txt"))
        assertEquals(12, shortestPathOnField("input/field_in1.txt"))
        assertEquals(43, shortestPathOnField("input/field_in3.txt"))
        assertEquals(28, shortestPathOnField("input/field_in4.txt"))
        assertEquals(222, shortestPathOnField("input/field_in5.txt"))
        assertEquals(15, shortestPathOnField("input/field_in6.txt"))
    }

}