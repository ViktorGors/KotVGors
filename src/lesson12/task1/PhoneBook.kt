@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {

    var PhoneBook = mutableMapOf<String, MutableSet<String>>()
    //var PhoneBook = HashMap<String, MutableSet<String>>()

    private fun СorrectIncorrectNum(num: String) {
        if (!num.matches(Regex("[0-9+*\\-#]+"))) throw IllegalArgumentException("Incorrect number! Please check the data entry.")
    }

    private fun СorrectIncorrectName(name: String) {
        if (!name.matches(Regex("[А-ЯЁ][а-яё]+\\s[А-ЯЁ][а-яё]+"))) throw IllegalArgumentException("Incorrect name! Please check the data entry.")
    }

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {

        СorrectIncorrectName(name) // проверочка
        if (PhoneBook.containsKey(name)) return false // человек с таким именем уже есть
        PhoneBook[name] = mutableSetOf()
        return true

    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        СorrectIncorrectName(name) // проверочка
        if (!PhoneBook.containsKey(name)) return false // проверка,есть ли вообще такой контакт
        PhoneBook.remove(name)
        return true

    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {

        СorrectIncorrectNum(phone) // проверочка
        СorrectIncorrectName(name) // проверочка

        when {
            (!PhoneBook.containsKey(name)) -> return false
            (PhoneBook[name]!!.contains(phone)) -> return false //проверяем наличие номера у человека
        }
        for (personNum in PhoneBook.values) { //проверка на наличие этого номера у других людей
            if (personNum.contains(phone)) return false
        }
        PhoneBook[name]!!.add(phone)
        return true
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {

        СorrectIncorrectNum(phone) // проверочка
        СorrectIncorrectName(name) // проверочка

        when {
            (!PhoneBook.containsKey(name)) -> return false // проверяем, есть ли такой человек
            (!PhoneBook[name]!!.contains(phone)) -> return false // проверяем, есть ли такой номер
        }

        PhoneBook[name]!!.remove(phone)
        return true
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        СorrectIncorrectName(name) // проверочка
        return PhoneBook[name] ?: setOf() //пустое множество при отсутсвие номеров
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        СorrectIncorrectNum(phone) // проверочка
        for ((name, phones) in PhoneBook) {
            if (phones.contains(phone)) return name
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (other is PhoneBook) {
            if (other.PhoneBook.size != this.PhoneBook.size) return false
            for ((name, num) in PhoneBook) {
                if (other.PhoneBook[name] != num) return false
            }
        }
        return true
    }

    override fun hashCode(): Int = PhoneBook.hashCode()
}