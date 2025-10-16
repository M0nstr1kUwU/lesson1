//import kotlin.random.Random
//import kotlin.math.roundToInt
//
//// БАЗОВЫЙ КЛАСС (РОДИТЕЛЬСКИЙ) - делаем его open для наследования
//open class Character(
//    val name: String,
//    maxHealth: Int,
//    baseAttack: Int
//){
//    // protected - делает его доступным для класса и его наследников
//    protected var _health = maxHealth.coerceAtLeast(1)
//    protected val _maxHealth = maxHealth.coerceAtLeast(1)
//    protected val _attack = baseAttack.coerceAtLeast(1)
//
//    // open - разрешает преобразование в наследниках
//    open val health: Int
//        get() = _health
//
//    open val maxHealth: Int
//        get() = _maxHealth
//
//    open val attack: Int
//        get() = _attack
//
//    open val isAlive: Boolean
//        get() = _health > 0
//
//    init {
//        println("Создан персонаж $name")
//    }
//
//    open fun takeDamage(damage: Int){
//        if (!isAlive) return
//
//        val actualDamage = damage.coerceAtLeast(0)
//        _health -= actualDamage
//        println("> $name получил $actualDamage урона")
//
//        if (_health <= 0){
//            println("> $name погиб")
//            return
//        }
//    }
//
//    open fun heal(amount: Int){
//        if (!isAlive) return
//        val healAmount = amount.coerceAtLeast(0)
//        _health = (_health + healAmount).coerceAtMost(_maxHealth)
//        println("> $name похилился на $healAmount HP")
//    }
//
//    open fun attack(target: Character){
//        if (!isAlive || !target.isAlive) return
//        val damage = calculateDamage(_attack)
//        println("> $name атакует ${target.name}")
//        target.takeDamage(damage)
//    }
//
//    open fun printStatus(){
//        val status = if (isAlive) "Жив" else "Мёртв"
//        println("| $name: $_health/$_maxHealth HP | ATK: $_attack ($status)")
//    }
//}
//
//
//// КЛАСС-НАСЛЕДНИК WARRRIOR
//class Warrior(name: String, maxHealth: Int, baseAttack: Int, maxStamina: Int, armor: Int): Character(name, maxHealth, baseAttack){
//    private var _armor: Int = armor
//    private var _maxStamina: Int = maxStamina
//    private var stamina: Int = _maxStamina
//
//    // Перезапишем стандартный метод получения урона
//    override fun takeDamage(damage: Int) {
//        if (!isAlive) return
//        val reducedDamage = (damage - _armor).coerceAtLeast(0)
//        println("> $name поглощает $_armor урона!")
//        super.takeDamage(reducedDamage) // super - вызов метода родительского класса
//    }
//
//    override fun heal(amount: Int) {
//        super.heal(amount)
//        val staminaPlus = 20
//        stamina = (stamina + 20).coerceAtMost(_maxStamina)
//        println("\t> Пополнена стамина! +$staminaPlus ST")
//    }
//
//    fun amplifierDamage(target: Character){
//        if (!isAlive) return
//        val cost = 50
//        if (stamina - cost >= 0){
//            stamina -= cost
//            val damage = calculateDamage((_attack * Random.nextDouble(1.5, 3.5)).roundToInt())
//            _health = (_health + (damage * 0.5).roundToInt()).coerceAtMost(_maxHealth)
//            println("> $name вкладывает энергию в атаку!")
//            println("\t> $name атакует ${target.name} с увеличенным уроном: $damage\n" +
//                    "\t> $name восстанавливает ${(damage * 0.5).roundToInt()} HP от особой атаки!")
//            target.takeDamage(damage)
//        }else{
//            _armor += 5
//            stamina = (stamina + 25).coerceAtMost(_maxStamina)
//            println("> $name ты слишком устал для усиленной атаки!\n" +
//                    "\t> $name усиливает защиту $_armor AR и пополняет стамину $stamina ST!")
//            attack(target)
//
//        }
//    }
//
//    fun berserk(target: Character){
//        if (!isAlive) return
//        val cost = 20
//        if (_health - cost >= 1){
//            _health -= cost
//            val damage = calculateDamage(_attack * 2)
//            println("> $name входит в режим Берсерка!")
//            println("\t> $name атакует ${target.name} с удвоенным уроном")
//
//            target.takeDamage(damage)
//        }else{
//            println("> $name, а ниче тот факт, что хп не хватает?")
//            attack(target)
//        }
//    }
//
//    override fun printStatus() {
//        super.printStatus()
//        println("\t|-> AR: $_armor | ST: $stamina/$_maxStamina")
//    }
//}
//
//// КЛАСС-НАСЛЕДНИК MAGE
//class Mage(name: String, maxHealth: Int, baseAttack: Int): Character(name, maxHealth, baseAttack){
//    private val maxMana: Int = 100
//    private var mana: Int = maxMana
//
//    // Перезапишем стандартный метод получения урона
//    override fun takeDamage(damage: Int) {
//        if (!isAlive) return
//        val actualDamage = (damage + 10).coerceAtLeast(0)
//        println("> $name получает увеличенный урон: -$actualDamage HP!")
//        super.takeDamage(actualDamage) // super - вызов метода родительского класса
//    }
//
//    fun amplifierDamage(target: Character){
//        if (!isAlive) return
//        val cost = 5
//        if (mana - cost >= 0){
//            mana -= cost
//            val damage = calculateDamage(_attack + 5)
//            println("> $name вкладывает ману в атаку!")
//            println("\t> $name атакует ${target.name} с увеличенным уроном: $damage")
//            target.takeDamage(damage)
//        }else{
//            println("> $name ты слишком устал для усиленной атаки!")
//            attack(target)
//        }
//    }
//
//    fun fireball(target: Character){
//        if (!isAlive) return
//        val cost = 10
//        if (mana - cost >= 0){
//            mana -= cost
//            val damage = calculateDamage((_attack * Random.nextDouble(1.0, 3.0)).roundToInt())
//            println("> $name кастует в ${target.name} FIREBALL!")
//            println("\t> $name нанёс ${target.name} $damage урона")
//            target.takeDamage(damage)
//        }else{
//            println("> $name ты слишком устал для такой атаки!")
//            attack(target)
//        }
//    }
//}
//
//
//fun calculateDamage(baseAttack: Int): Int{
//    val variation = Random.nextInt(80, 121)
//    return (baseAttack * variation) / 100
//}
//
//fun main(){
//    println(">>>!БОЙ НАЧИНАЕТСЯ!<<<")
//    val player = Warrior("M0nstr1k", 100, 20, 100, 10)
//    val enemy = Warrior("Lord Demons", 200, 15, 100, 0)
//    var turns = 1
//
//    while (player.isAlive || enemy.isAlive){
//        println("\nTurns: $turns")
//
//        println("\n>=============================================<")
//        player.printStatus()
//        enemy.printStatus()
//        println(">=============================================<\n")
//        // println("Actions: 1-Attack, 2-Heal, 3-Amplifier-Attack, 4-Fireball: ")
//        println("Actions: 1-Attack, 2-Heal, 3-Amplifier-Attack, 4-Berserk: ")
//
//        val action_player = readln().toInt()
//        println(">------------------------<")
//        if (action_player == 1){
//            player.attack(enemy)
//        }
//        else if (action_player == 2){
//            player.heal(50)
//        }
//        else if (action_player == 3){
//            player.amplifierDamage(enemy)
//        }
//        else if (action_player == 4){
//            player.berserk(enemy)
//        }
//        else{
//            println("Неверный выбор!")
//            continue
//        }
//
//        if (!enemy.isAlive){
//            println("${player.name} выиграл в этой схватке!")
//            break
//        }
//
//        println()
//
//        val action_enemy = Random.nextInt(1, 5)
//        if (action_enemy == 1){
//            enemy.attack(player)
//        }
//        else if (action_enemy == 2){
//            enemy.heal(50)
//        }
//        else if (action_enemy == 3){
//            enemy.amplifierDamage(player)
//        }
//        else if (action_enemy == 4){
//            enemy.berserk(player)
//        }
//        println(">------------------------<")
//
//        if (!player.isAlive){
//            println("${enemy.name} выиграл в этой схватке!")
//            break
//        }
//
//        turns += 1
//    }
//}