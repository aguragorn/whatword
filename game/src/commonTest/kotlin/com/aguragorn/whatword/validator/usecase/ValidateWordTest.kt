package com.aguragorn.whatword.validator.usecase
//
//import com.aguragorn.whatword.grid.model.Word
//import com.aguragorn.whatword.keyboard.model.Letter
//import com.aguragorn.whatword.validator.model.IncorrectLengthException
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.fail
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class ValidateWordTest {
//
//    @Test
//    fun letters_are_marked_correct_only_when_in_right_position() = runTest {
//        val mysteryWord = "qwerty"
//        val word = Word(
//            letters = mutableListOf(
//                Letter('q'),
//                Letter('w'),
//                Letter('r'),
//                Letter('e'),
//                Letter('t'),
//                Letter('g'),
//            )
//        )
//
//        val validateWord = ValidateWord(mysteryWordDataStore = mockk {
//            coEvery { getMysteryWords(any(), any()) } returns listOf(mysteryWord)
//        })
//        val validatedWord = validateWord(
//            attempt = word,
//            mysteryWord = mysteryWord,
//            language = "english"
//        )
//
//        assertEquals(Letter.Status.CORRECT, validatedWord.letters[0].status, "q status is wrong")
//        assertEquals(Letter.Status.CORRECT, validatedWord.letters[1].status, "w status is wrong")
//        assertEquals(Letter.Status.CORRECT, validatedWord.letters[4].status, "t status is wrong")
//    }
//
//    @Test
//    fun letters_are_marked_incorrect_only_when_not_existing() = runTest {
//        val mysteryWord = "qwerty"
//        val word = Word(
//            letters = mutableListOf(
//                Letter('q'),
//                Letter('w'),
//                Letter('r'),
//                Letter('e'),
//                Letter('t'),
//                Letter('g'),
//            )
//        )
//
//        val validateWord = ValidateWord(mysteryWordDataStore = mockk {
//            coEvery { getMysteryWords(any(), any()) } returns listOf(mysteryWord)
//        })
//        val validatedWord = validateWord(
//            attempt = word,
//            mysteryWord = mysteryWord,
//            language = "english"
//        )
//
//        assertEquals(Letter.Status.INCORRECT, validatedWord.letters[5].status)
//    }
//
//    @Test
//    fun letters_are_marked_misplaced_only_when_not_in_position() = runTest {
//        val mysteryWord = "qwerty"
//        val word = Word(
//            letters = mutableListOf(
//                Letter('q'),
//                Letter('w'),
//                Letter('r'),
//                Letter('e'),
//                Letter('t'),
//                Letter('g'),
//            )
//        )
//
//        val validateWord = ValidateWord(mysteryWordDataStore = mockk {
//            coEvery { getMysteryWords(any(), any()) } returns listOf(mysteryWord)
//        })
//        val validatedWord = validateWord(
//            attempt = word,
//            mysteryWord = mysteryWord,
//            language = "english"
//        )
//
//        assertEquals(Letter.Status.MISPLACED, validatedWord.letters[2].status)
//        assertEquals(Letter.Status.MISPLACED, validatedWord.letters[3].status)
//    }
//
//    @Test
//    fun throws_exception_when_different_lenght() = runTest {
//        val mysteryWord = "qwerty"
//        val shorter = Word(
//            letters = mutableListOf(
//                Letter('q'),
//                Letter('w'),
//                Letter('r'),
//                Letter('e'),
//                Letter('t'),
//            )
//        )
//        val longer = Word(
//            letters = mutableListOf(
//                Letter('q'),
//                Letter('w'),
//                Letter('r'),
//                Letter('e'),
//                Letter('t'),
//                Letter('y'),
//                Letter('u'),
//            )
//        )
//        val empty = Word()
//
//        val validate = ValidateWord(mysteryWordDataStore = mockk {
//            coEvery { getMysteryWords(any(), any()) } returns listOf(mysteryWord)
//        })
//
//        try {
//            validate(
//                attempt = shorter,
//                mysteryWord = mysteryWord,
//                language = "english"
//            )
//            fail("validate did not throw on shorter attempt")
//        } catch (e: IncorrectLengthException) {
//        }
//
//        try {
//            validate(
//                attempt = longer,
//                mysteryWord = mysteryWord,
//                language = "english"
//            )
//            fail("validate did not throw on longer attempt")
//        } catch (e: IncorrectLengthException) {
//        }
//
//        try {
//            validate(
//                attempt = empty,
//                mysteryWord = mysteryWord,
//                language = "english"
//            )
//            fail("validate did not throw on empty attempt")
//        } catch (e: IncorrectLengthException) {
//        }
//    }
//}