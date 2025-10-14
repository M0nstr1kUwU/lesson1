import kotlin.random.Random

// БАЗОВЫЙ КЛАСС (РОДИТЕЛЬСКИЙ) - делаем его open для наследования
open class Character(
    val name: String,
    maxHealth: Int,
    baseAttack: Int
){
    // protected - делает его доступным для класса и его наследников
    protected var _health = maxHealth.coerceAtLeast(1)
    protected val _maxHealth = maxHealth.coerceAtLeast(1)
    protected val _attack = baseAttack.coerceAtLeast(1)

    // open - разрешает преобразование в наследниках
    open val health: Int
        get() = _health

    open val maxHealth: Int
        get() = _maxHealth

    open val attack: Int
        get() = _attack

    open val isAlive: Boolean
        get() = _health <= 0

    init {
        println("Создан персонаж $name")
    }

    open fun takeDamage(damage: Int){
        if (!isAlive) return

        val actualDamage = damage.coerceAtLeast(0)
        _health -= actualDamage
        println("$name получил $actualDamage урона")

        if (_health <= 0){
            println("$name погиб")
            return
        }
    }

    open fun heal(amount: Int){
        if (!isAlive) return
        val healAmount = amount.coerceAtLeast(0)
        _health = (_health + healAmount).coerceAtMost(_maxHealth)
        println("$name похилился на $healAmount HP")
    }

    open fun attack(target: Character){
        if (!isAlive || !target.isAlive) return
        val damage = calculateDamage(_attack)
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }

    fun printStatus(){
        val status = if (isAlive) "Жив" else "Мёртв"
        println("$name: $_health/$_maxHealth HP | ATK: $_attack ($status)")
    }
}


// КЛАСС-НАСЛЕДНИК WARRRIOR
class Warrior(name: String, maxHealth: Int, baseAttack: Int): Character(name, maxHealth, baseAttack){
    var armor: Int = 5

    // Перезапишем стандартный метод получения урона
    override fun takeDamage(damage: Int) {
        if (!isAlive) return
        val reducedDamage = (damage - armor).coerceAtLeast(0)
        println("$name поглощает $armor урона!")
        super.takeDamage(reducedDamage) // super - вызов метода родительского класса
    }

    fun berserk(target: Character){
        if (!isAlive) return
        val cost = 10
        if (_health - cost >= 1){
            _health -= cost
            val damage = calculateDamage(_attack * 2)
            println("$name входит в режим Берсерка!")
            println("$name атакует ${target.name} с удвоенным уроном")
            target.takeDamage(damage)
        }else{
            println("$name, а ниче тот факт хп не хватает?")
            attack(target)
        }
    }
}

// КЛАСС-НАСЛЕДНИК WARRRIOR
class Mage(name: String, maxHealth: Int, baseAttack: Int): Character(name, maxHealth, baseAttack){
    private val maxMana: Int = 100
    private var mana: Int = maxMana

    // Перезапишем стандартный метод получения урона
    override fun takeDamage(damage: Int) {
        if (!isAlive) return
        val actualDamage = (damage + 10).coerceAtLeast(0)
        println("$name получает увеличенный урон: -$actualDamage HP!")
        super.takeDamage(actualDamage) // super - вызов метода родительского класса
    }

    fun amplifierDamage(target: Character){
        if (!isAlive) return
        val cost = 5
        if (mana - cost >= 0){
            mana -= cost
            val damage = calculateDamage(_attack + 5)
            println("$name вкладывает ману в атаку!")
            println("$name атакует ${target.name} с увеличенным уроном уроном: $damage")
            target.takeDamage(damage)
        }else{
            println("$name ты слишком устал для усиленной атаки!")
            attack(target)
        }
    }

    fun fireball(target: Character){
        if (!isAlive) return
        val cost = 10
        if (mana - cost >= 0){
            mana -= cost
            val damage = calculateDamage(_attack + 5)
            println("$name кастует в ${target.name} FIREBALL!")
            println("$name нанёс ${target.name} $damage урона")
            target.takeDamage(damage)
        }else{
            println("$name ты слишком устал для такой атаки!")
            attack(target)
        }
    }
}


fun calculateDamage(baseAttack: Int): Int{
    val variation = Random.nextInt(80, 121)
    return (baseAttack * variation) / 100
}

fun main(){
    println(">>>!БОЙ НАЧИНАЕТСЯ!<<<")
    val player = Mage("M0nstr1k", 100, 20)
    val enemy = Warrior("Lord", 100, 20)
    println("\n>==================================<")
    player.printStatus()
    enemy.printStatus()
    println(">==================================<\n")

    player.attack(enemy)
    enemy.attack(player)
    player.amplifierDamage(enemy)
    enemy.berserk(player)
    player.fireball(enemy)
    enemy.heal(50)
}