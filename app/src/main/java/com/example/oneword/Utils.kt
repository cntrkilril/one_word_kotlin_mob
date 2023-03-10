package com.example.oneword


class Utils {

    fun randomWord(words: ArrayList<String>): String {
        return words.shuffled()[0]
    }

    fun processWord(word: List<String>, resWords: String): ArrayList<LetterClass> {
        val res = arrayListOf<LetterClass>()
        for (position in word.indices) {
            val letter = word[position].uppercase()
            if (letter in resWords) {
                if (letter == resWords[position].toString()) {
                    res.add(LetterClass(letter, StatusLetterType.CORRECT))
                } else {
                    res.add(LetterClass(letter, StatusLetterType.EXIST))
                }
            } else {
                res.add(LetterClass(letter, StatusLetterType.NOT_EXIST))
            }
        }
        return res
    }

}