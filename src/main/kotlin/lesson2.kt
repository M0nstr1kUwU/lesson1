import kotlin.random.Random
import kotlin.system.exitProcess

// класс с конструктором
// Внутри () - параметры класса пишем - эти параметры (главного конструктора) будут использоваться внутри класса
class Character(
    // val - параметры который автоматом станут свойствами класса
    val name: String,
    maxHealth: Int, // без val - это параметр конструктора (не свойство класса)
    baseAttack: Int
){
    // ИНКАПСУЛЯЦИЯ - делает внутренние свойства приватными (private)
    // private - модификатор доступа. Означает, что свойство доступно только внутри класса
    // (не может быть вызвано другим классом)
    private var _health = maxHealth.coerceAtLeast(1) // Гарантировано округлит до 1, если значение меньше

    // Публичное свойство health (только для чтения) val
    // Другие классы могут узнать его значения, но не могут изменить его напрямую
    val health: Int
        get() = _health // get() - возвращает значение приватного _health

    private val _maxHealth = maxHealth.coerceAtLeast(1)
    val maxHealth: Int
        get() = _maxHealth

    private val _attack = baseAttack.coerceAtLeast(1)
    val attack: Int
        get() = _attack

    // Вычисляемое свойство (не хранится, а вычисляется при каждом обращении к нему)
    val isAlive: Boolean
        get() = _health > 0

    // Блок init - выполняется при создании объекта (после инициализации свойств)
    init {
        println("Создан новый персонаж: $name (HP: $_health/$_maxHealth, ATK: $_attack)")
    }

    fun takeDamage(damage: Int){
        if (!isAlive){
            println("$name уже скороспостижился и не может получить урон")
            return // прервёт и выйдет из метода
        }
        val actualDamage = damage.coerceAtLeast(1) // урон не может быть отрицательным

        _health -= actualDamage
        println("$name получает $actualDamage урона! Осталось HP: $_health")

        if (_health <= 0){
            println("$name пал в бою!")
        }
    }

    fun heal(amount: Int){
        if (!isAlive){
            println("$name уже скороспостижился и не может быть похилен")
            return // прервёт и выйдет из метода
        }
        val healAmount = amount.coerceAtLeast(0)

        _health = (_health + healAmount).coerceAtLeast(_maxHealth)
        println("$name похилился на $healAmount | HP: $_health")
    }

    fun attack(target: Character){
        if (!isAlive){
            println("$name уже скороспостижился и не атаковать")
            return // прервёт и выйдет из метода
        }
        if (!target.isAlive){
            println("${target.name} уже мёртв, хватит его пинать")
            return // прервёт и выйдет из метода
        }

        val damage = calculateDamage(_attack)
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }

    fun fireball(target: Character){
        if (!isAlive){
            println("$name уже скороспостижился и не атаковать")
            return // прервёт и выйдет из метода
        }
        if (_health - 10 <= 0){
            println("$name, ты скорее скоропостижишся, чем твой противник, если закастуешь это!")
            return
        }
        if (!target.isAlive){
            println("${target.name} уже мёртв, хватит его пинать")
            return
        }

        val damage = calculateDamage(_attack) + Random.nextInt(10, 20)
        println("$name кастует FIREBALL на ${target.name}")
        _health -= 10
        target.takeDamage(damage)
    }

    fun printStatus(){
        val status = if (isAlive) "Жив" else "Мёртв"
        println("$name: $_health/$_maxHealth HP | ATK: $_attack ($status)")
    }

    fun calculateDamage(baseAttack: Int): Int{
        val variation = Random.nextInt(80, 121)
        return (baseAttack * variation) / 100
    }
}


fun main(){
    val player = Character("Олег", 100, 15)
    val enemy = Character("ТунТунТунТун Сахур", 60, 20)

    println(">>> НАЧАЛО БОЯ <<<")

    player.attack(enemy)
    enemy.attack(player)

    player.heal(20)

    player.fireball(enemy)
}


