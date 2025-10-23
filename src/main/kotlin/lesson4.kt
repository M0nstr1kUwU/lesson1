import java.security.cert.TrustAnchor
import kotlin.random.Random
import kotlin.math.roundToInt

class Item(
    val id: String,
    val name: String,
    val desccription: String,
    val value: Int = 0,
    val useEffect: (Player) -> Unit = {}
){
    fun use(player: Player){
        println("Использован: $name")
       useEffect(player)
    }

    fun displayInfo(){
        println("$name: $desccription (Ценность: $value)")
    }
}


class Inventory{
    // mutableListOf() - создаёт пустой изменяемый список (для хранения предметов ТОЛЬКО типа данных Item)
    private val items = mutableListOf<Item>()

    fun addItem(item: Item){
        // .add(элемент) - метод добавляющий элемент в конец списка
        items.add(item)
        println("Предмет: ${item.name} добавлен в инвентарь")
    }

    fun removeItem(index: Int): Boolean{
        // index in 0 until items.size - проверяет находится ли индекс в диапазоне
        if (index in 0 until items.size){
            val removedItem = items.removeAt(index) // Удаляет элемент по индексу
            println("Предмет ${removedItem.name} удалён из инвентаря")
            return true
        }
        println("Предмет не найден (по индексу)")
        return false
    }

    fun useItem(index: Int, player: Player): Boolean{
        if (index in 0 until items.size){
            val item = items[index]
            item.use(player)
            items.removeAt(index)
            return true
        }
        println("Предмет не найден")
        return false
    }

    fun display(){
        if (items.isEmpty()){
            println("Инвентарь пуст")
        }
        else{
            println("\b === Инвентарь === ")
            items.forEachIndexed {index, item ->
                println("${index + 1}. ${item.name} - ${item.desccription}")
            }
            println("Всего предметов: ${items.size}")
        }
    }

    fun actionsInventory(action: Int, player: Player){
        val actionIndex = action - 1

        if (actionIndex in 0 until items.size) {
            val item = items[actionIndex]
            item.displayInfo()
            println("\t1.Использовать |" +
                    "\t2.Выбросить |" +
                    "\t0.Назад")
            val action_use = readln().toInt()
            when (action_use) {
                1 -> {
                    useItem(actionIndex, player)
                }
                2 -> {
                    removeItem(actionIndex)
                }
                0 -> return
            }
        } else {
            println("Предмет не найден")
        }
    }

    // Item? - возможность возврата null значения
    fun findItemId(itemId: String): Item?{
        return items.find{it.id == itemId}
    }

    fun hasItem(itemId: String): Boolean{
        return items.any{it.id == itemId}
    }

    fun countItems(itemId: String): Int{
        return items.count{it.id == itemId}
    }
}


open class Character(
    val name: String,
    maxHealth: Int,
    baseAttack: Int
){
    // protected - делает его доступным для класса и его наследников
    protected var _health = maxHealth.coerceAtLeast(1)
    protected val _maxHealth = maxHealth.coerceAtLeast(1)
    protected var _attack = baseAttack.coerceAtLeast(1)

    // open - разрешает преобразование в наследниках
    open val health: Int
        get() = _health

    open val maxHealth: Int
        get() = _maxHealth

    open val attack: Int
        get() = _attack

    open val isAlive: Boolean
        get() = _health > 0

    init {
        println("Создан персонаж $name")
    }

    open fun takeDamage(damage: Int){
        if (!isAlive) return

        val actualDamage = damage.coerceAtLeast(0)
        _health -= actualDamage
        println("> $name получил $actualDamage урона")

        if (_health <= 0){
            println("> $name погиб")
            return
        }
    }

    open fun heal(amount: Int){
        if (!isAlive) return
        val healAmount = amount.coerceAtLeast(0)
        _health = (_health + healAmount).coerceAtMost(_maxHealth)
        // println("> $name похилился на $healAmount HP")
    }

    open fun attack(target: Character){
        if (!isAlive || !target.isAlive) return
        val damage = calculateDamage(_attack)
        println("> $name атакует ${target.name}")
        target.takeDamage(damage)
    }

    open fun shield(target: Character){ //
        if (!isAlive || !target.isAlive) return
        val damage = calculateDamage(target._attack) / 2
        _health -= damage
        println("$name выставляет щит и получает в 2 раза меньше изначального урона ${target.name}!")
    }

    open fun printStatus(){
        val status = if (isAlive) "Жив" else "Мёртв"
        println("| $name: $_health/$_maxHealth HP | ATK: $_attack ($status)")
    }
}


class Player(name: String, health: Int, attack: Int, start_money: Int): Character(name, health, attack){
    var damage = attack
    val inventory = Inventory()
    val questManager = QuestManager()
    var money = start_money
    var count_shield = 1

    fun pickUpItem(item: Item){
        inventory.addItem(item)
    }

    fun showInventory(){
        inventory.display()
    }
}


fun calculateDamage(baseAttack: Int): Int{
    val variation = Random.nextInt(80, 121)
    return (baseAttack * variation) / 100
}


fun inventoryPlayer(player: Player){
    while (true){
        player.showInventory()
        val action = readln().toInt()
        if (action == 0){
            break
        }
        player.inventory.actionsInventory(action, player)
    }
}

class Enemy(name: String, health: Int, attack: Int): Character(name, health, attack){
    var damage = attack
}

fun battle(player: Player, enemy: Enemy){

}



fun main(){
    println("\n === ИГРА С СИСТЕМОЙ ИНВЕНТАРЯ ===")

    val player = Player("M0nstr1k", 100, 10, 0)

    val healthPotion = Item(
        "health_potion",
        "Зелье здоровья",
        "Восстанавливает 10 HP",
        25,
        { player ->
            player.heal(10)
            println("${player.name} восстановил 10 HP")
        }
    )

    val strengthPotion = Item(
        "strength_potion",
        "Зелье силы",
        "Усиливает атаку в двое",
        52,
        { player ->
            player.damage *= 2
            println("${player.name} стал в двое сильнее!")
        }
    )

    val oldKey = Item(
        "old_key",
        "Старый ключ",
        "Что-то явно открывает",
        5
    )


    println("Игрок находит предметы: ")
    player.pickUpItem(healthPotion)
    player.pickUpItem(strengthPotion)
    player.pickUpItem(oldKey)
    player.pickUpItem(healthPotion)


    player.inventory.useItem(0, player)

    println("${player.name} набрёл на дверь!")
    if (player.inventory.hasItem("old_key")){
        player.inventory.removeItem(player.inventory.countItems("old_key"))
        println("${player.name} использует ${oldKey.name} чтобы открыть дверь!")
    }else{
        println("Заперто. Нужно найти ключ от этой двери!")
    }

    player.pickUpItem(oldKey)

    while (true){
        println("Действия:\n" +
                "1.Инвентарь\n" +
                "2.В бой!\n" +
                "3.beta\n" +
                "4.beta\n" +
                "0.Выход"
        )
        val actions = readln().toInt()
        when (actions){
            1 -> {
                inventoryPlayer(player)
            }
            0 -> break
        }
    }
}

