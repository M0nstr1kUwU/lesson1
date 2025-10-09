import kotlin.random.Random

class Character{
    var name: String = "Безымянный герой"
    var health: Int = 100
    val maxHealth: Int = 100
    var attack: Int = 15
    var isAlive: Boolean = true
    var potion_count: Int = 2

    fun takeDamage(damage: Int){
        health -= damage
        println("$name получает $damage урона, HP: $health/$maxHealth")

        if (this.health <= 0){
            isAlive = false
            println("$name жидко пукнул в бою")
        }
    }

    fun heal(amount: Int){
        if (isAlive){
            health = minOf(health+amount, maxHealth)
            println("$name похилился на $amount, HP: $health/$maxHealth")
        }else{
            println("Чел... Ты мёртв и не можешь похилиться...")
        }
    }

    fun attack(target: Character){
        if (!isAlive){
            println("Чел... Ты мёртв и не можешь сражаться...")
            return
        }

        val damage = calculateDamage(attack)
        println("$name атакует ${target.name}!")
        target.takeDamage(damage)
    }

    fun use_potion(count: Int){
        if (isAlive){
            if (count > 0){
                var amount = Random.nextInt(20, 40)
                health = minOf(health+amount, maxHealth)
                potion_count -= 1
                println("$name похилился от зелья на $amount, HP: $health/$maxHealth")
            }else{
                println("У вас нет зелий!")
            }
        }else{
            println("Чел... Ты же мёртв...")
        }
    }
}

fun calculateDamage(baseAttack: Int): Int{
    val variation = Random.nextInt(80, 121)
    return (baseAttack * variation) / 100
}

fun main(){
    println("==== СОЗДАНИЕ ПЕРСОНАЖЕЙ ЧЕРЕЗ ОБЪЕКТЫ КЛАССОВ ====")

    // СОЗДАНИЕ ОБЪЕКТОВ
    val player = Character()
    val monster = Character()

    // НАСТРОЙКА ОБЪЕКТОВ
    player.name = "Aleg"
    player.health = 100
    player.attack = 10

    monster.name = "Патапим-Патапум"
    monster.health = 100
    monster.attack = 10


    // ВЫЗОВ МЕТОДОМ ОБЪЕКТОВ
    println(">>> Вы вступаете в бой с ${monster.name} <<<")
    var action: Int = 0

    while (player.health > 0 && monster.health > 0){
        println("Действия: 1-Атака, 2-Лечиться, 3-Зелье")
        action = readln().toInt()
        if (action == 1) {
            player.attack(monster)
        }
        else if (action == 2){
            player.heal(20)
        }
        else if (action == 3){
            player.use_potion(player.potion_count)
        }

        monster.attack(player)

        println("${player.name} HP: ${player.health} ТЫ ЖИВОЙ? (${player.isAlive})")
        println("${monster.name} HP: ${monster.health} ТЫ ЖИВОЙ? (${monster.isAlive})")

        // player.heal(25)
        player.use_potion(player.potion_count)
    }

}



//ДЗ: ДОБАВЬТЕ СВОЙСТВО КОЛ-ВА ЗЕЛИЙ, КОТОРЫЕ МОЖНО ВЫПИТЬ
// СОЗДАЙТЕ НОВЫЙ МЕТОД ПИТЬЯ ЗЕЛЬЯ
// ПИТЬЕ ЗЕЛЬЯ, ВОССТАНАВЛИВАЕТ СЛУЧАЙНО ОТ 20 ДО 40 HP (НЕ ЗАБЫВАЙТЕ ЧТО МЕТОД ХИЛА У ВАС УЖЕ ЕСТЬ)