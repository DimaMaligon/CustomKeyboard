package com.example.customkeyboard.keyboard

class KeyboardTemplates {

    fun getTempleKeyboardEn(keyboardState: KeyboardState): Array<Array<String>> {
        val keysMatrix = when (keyboardState) {
            KeyboardState.CAPS -> arrayOf(
                arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
                arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
                arrayOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageRu", "enter")
            )

            KeyboardState.NOCAPS -> arrayOf(
                arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
                arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
                arrayOf("shift", "z", "x", "c", "v", "b", "n", "m", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageRu", "enter")
            )

            KeyboardState.DOUBLECAPS -> arrayOf(
                arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
                arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
                arrayOf("SHIFT", "Z", "X", "C", "V", "B", "N", "M", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageRu", "enter")
            )

            KeyboardState.NUMBER -> arrayOf(
                arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
                arrayOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
                arrayOf("=", "*", "\"", "'", ":", ";", "!", "?", "delete"),
                arrayOf("ABC", ",", "_", " ", "/", ".", "enter")
            )

            KeyboardState.EMOJI -> arrayOf(
                arrayOf("back", "😁", "😂", "😃", "😆", "😇", "😈", "😉", "😊", "😋", "😌", "😍", "😎"),
                arrayOf("😓", "😔", "😖", "😘", "😚", "😜", "😝", "😞", "😠", "😡", "😢", "😣", "😤"),
                arrayOf("😥", "😨", "😩", "😪", "😫", "😭", "😰", "😐", "😒", "😱", "😲", "😳", "😵"),
                arrayOf("😶", "😷", "😸", "😹", "😺", "😻", "😼", "😽", "😾", "😿", "🙀", "🙅", "😏"),
                arrayOf("🙇", "🙈", "🙉", "🙊", "🙋", "🙌", "🙍", "🙎", "✋", "✋", "🐲", "👀", "🐝"),
            )

            else -> arrayOf(
                arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
                arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
                arrayOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageRu", "enter")
            )
        }
        return keysMatrix
    }

    fun getTempleKeyboardRus(keyboardState: KeyboardState): Array<Array<String>> {
        val keysMatrix = when (keyboardState) {
            KeyboardState.CAPS -> arrayOf(
                arrayOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х", "Ъ"),
                arrayOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"),
                arrayOf("Shift", "Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageEn", "enter")
            )

            KeyboardState.NOCAPS -> arrayOf(
                arrayOf("й", "ц", "у", "к", "е", "н", "г", "ш", "щ", "з", "х", "ъ"),
                arrayOf("ф", "ы", "в", "а", "п", "р", "о", "л", "д", "ж", "э"),
                arrayOf("shift", "я", "ч", "с", "м", "и", "т", "ь", "б", "ю", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageEn", "enter")
            )

            KeyboardState.DOUBLECAPS -> arrayOf(
                arrayOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х", "Ъ"),
                arrayOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"),
                arrayOf("SHIFT", "Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageEn", "enter")
            )

            KeyboardState.NUMBER -> arrayOf(
                arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
                arrayOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
                arrayOf("=", "*", "\"", "'", ":", ";", "!", "?", "delete"),
                arrayOf("ABC", ",", "_", " ", "/", ".", "enter")
            )

            KeyboardState.EMOJI -> arrayOf(
                arrayOf("back", "😁", "😂", "😃", "😆", "😇", "😈", "😉", "😊", "😋", "😌", "😍", "😎"),
                arrayOf("😓", "😔", "😖", "😘", "😚", "😜", "😝", "😞", "😠", "😡", "😢", "😣", "😤"),
                arrayOf("😥", "😨", "😩", "😪", "😫", "😭", "😰", "😐", "😒", "😱", "😲", "😳", "😵"),
                arrayOf("😶", "😷", "😸", "😹", "😺", "😻", "😼", "😽", "😾", "😿", "🙀", "🙅", "😏"),
                arrayOf("🙇", "🙈", "🙉", "🙊", "🙋", "🙌", "🙍", "🙎", "✋", "✋", "🐲", "👀", "🐝"),
            )

            else -> arrayOf(
                arrayOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х", "Ъ"),
                arrayOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"),
                arrayOf("SHIFT", "Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "delete"),
                arrayOf("123", "emoji", ",", " ", ".", "languageEn", "enter")
            )
        }
        return keysMatrix
    }
}