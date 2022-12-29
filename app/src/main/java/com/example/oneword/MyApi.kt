package com.example.oneword

interface MyApi {
    fun login(login: String, password: String): User?
    fun logout(login: String): Boolean
    fun register(login: String, password: String): Boolean
    fun getStoryByUser(user: User): ArrayList<ResultGame>
    fun addToStory(user: User, resultGame: ResultGame): Boolean
    fun getResWords(indexOfLevel: Int): String
    fun getLevel(id: Int): String
    fun processWord(wordInputText: List<String>, resWord: String): ArrayList<LetterClass>
}

class MyApiImpl : MyApi {
    override fun login(login: String, password: String): User? {
        val users = usersDataList
        for (user in users) {
            if (user.login == login && user.password == password) {
                return User(password, login)
            }
        }
        return null
    }

    override fun logout(login: String): Boolean {
        val users = usersDataList
        for (user in users) {
            if (user.login == login) {
                return true
            }
        }
        return false
    }

    override fun register(login: String, password: String): Boolean {
        val users = usersDataList
        for (user in users) {
            if (user.login == login) {
                return false
            }
        }
        usersDataList.add(User(password, login))
        return true
    }

    override fun getStoryByUser(user: User): ArrayList<ResultGame> {
        val storyList = storyDataList
        for (story in storyList) {
            if (story.user.login == user.login) {
                return story.resultsGames
            }
        }
        return arrayListOf()
    }

    override fun addToStory(user: User, resultGame: ResultGame): Boolean {
        val storyList = storyDataList
        for (story in storyList) {
            if (story.user.login == user.login) {
                story.resultsGames.add(resultGame)
                return true
            }
        }
        storyList.add(Story(user, arrayListOf(resultGame)))
        return true
    }

    override fun getResWords(indexOfLevel: Int): String {
        val words = wordsDataList
        return Utils().randomWord(words[indexOfLevel]).uppercase()
    }

    override fun getLevel(id: Int): String {
        val levels = levelsDataList
        return levels[id].toString()
    }

    override fun processWord(wordInputText: List<String>, resWord: String): ArrayList<LetterClass> {
        return Utils().processWord(wordInputText, resWord)
    }


}