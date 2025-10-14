//import kotlin.math.min
//import kotlin.random.Random
//import kotlin.system.exitProcess
//
//// класс с конструктором
//// Внутри () - параметры класса пишем - эти параметры (главного конструктора) будут использоваться внутри класса
//class Character(
//    // val - параметры который автоматом станут свойствами класса
//    val name: String,
//    maxHealth: Int, // без val - это параметр конструктора (не свойство класса)
//    baseAttack: Int,
//    maxMane: Int
//){
//    // ИНКАПСУЛЯЦИЯ - делает внутренние свойства приватными (private)
//    // private - модификатор доступа. Означает, что свойство доступно только внутри класса
//    // (не может быть вызвано другим классом)
//    private var _health = maxHealth.coerceAtLeast(1) // Гарантировано округлит до 1, если значение меньше
//    private var _mane = maxMane.coerceAtLeast(0)
//
//    // Публичное свойство health (только для чтения) val
//    // Другие классы могут узнать его значения, но не могут изменить его напрямую
//    val health: Int
//        get() = _health // get() - возвращает значение приватного _health
//
//    val mane: Int
//        get() = _mane
//
//    private val _maxHealth = maxHealth.coerceAtLeast(1)
//    val maxHealth: Int
//        get() = _maxHealth
//
//    private val _maxMane = maxMane.coerceAtLeast(0)
//    val maxMane: Int
//        get() = _maxMane
//
//    private val _attack = baseAttack.coerceAtLeast(1)
//    val attack: Int
//        get() = _attack
//
//    // Вычисляемое свойство (не хранится, а вычисляется при каждом обращении к нему)
//    val isAlive: Boolean
//        get() = _health > 0
//
//    // Блок init - выполняется при создании объекта (после инициализации свойств)
//    init {
//        println("Создан новый персонаж: $name (HP: $_health/$_maxHealth, ATK: $_attack)")
//    }
//
//    fun takeDamage(damage: Int){
//        if (!isAlive){
//            println("$name уже скороспостижился и не может получить урон")
//            return // прервёт и выйдет из метода
//        }
//        val actualDamage = damage.coerceAtLeast(1) // урон не может быть отрицательным
//
//        _health -= actualDamage
//        println("$name получает $actualDamage урона! Осталось HP: $_health")
//
//        if (_health <= 0){
//            println("$name пал в бою!")
//        }
//    }
//
//    fun heal(amount: Int){
//        if (!isAlive){
//            println("$name уже скороспостижился и не может быть похилен")
//            return // прервёт и выйдет из метода
//        }
//        val healAmount = amount.coerceAtLeast(0)
//
//        _health = (_health + healAmount).coerceAtMost(_maxHealth)
//        println("$name похилился на $healAmount | HP: $_health")
//    }
//
//    fun attack(target: Character){
//        if (!isAlive){
//            println("$name уже скороспостижился и не атаковать")
//            return // прервёт и выйдет из метода
//        }
//        if (!target.isAlive){
//            println("${target.name} уже мёртв, хватит его пинать")
//            return // прервёт и выйдет из метода
//        }
//
//        val damage = calculateDamage(_attack)
//        println("$name атакует ${target.name}")
//        target.takeDamage(damage)
//    }
//
//    fun fireball(target: Character){
//        if (!isAlive){
//            println("$name уже скороспостижился и не атаковать")
//            return // прервёт и выйдет из метода
//        }
//        if (_mane - 10 < 0){
//            println("$name, ты слишком устал чтобы кастовать это!")
//        }
//        if (!target.isAlive){
//            println("${target.name} уже мёртв, хватит его пинать")
//            return
//        }
//
//        val damage = calculateDamage(_attack) + Random.nextInt(100, 200)
//        println("$name кастует FIREBALL на ${target.name}")
//        calculateMane("-", 10)
//        target.takeDamage(damage)
//    }
//
//    fun printStatus(){
//        val status = if (isAlive) "Жив" else "Мёртв"
//        println("$name: $_health/$_maxHealth HP | ATK: $_attack ($status)")
//    }
//
//    fun calculateDamage(baseAttack: Int): Int{
//        val variation = Random.nextInt(80, 121)
//        return (baseAttack * variation) / 100
//    }
//
//    fun calculateMane(action: String, mane: Int): Int{
//        if (action == "+"){
//            _mane = (_mane + mane).coerceAtLeast(_maxMane)
//        }
//        else if (action == "-"){
//            _mane = (_mane - mane).coerceAtLeast(0)
//        }
//        return _mane
//    }
//}
//
//
//fun main(){
//    val player = Character("M0nstr1k", 100, 35, 100)
//    val enemy = Character("Lord Demons", 1000, 35, 1000)
//
//    println(">>> НАЧАЛО БОЯ <<<")
//    var turns = 1
//    println("\n     Turns: $turns")
//    println(">==================================<")
//    player.printStatus()
//    enemy.printStatus()
//    println(">==================================<\n")
//
//    while (player.isAlive and enemy.isAlive){
//        println("\nActions: 1-Attack, 2-Heal, 3-Fireball - ")
//        val action_player = readln().toInt()
//        println()
//        if (action_player == 1){
//            player.attack(enemy)
//        }
//        else if (action_player == 2){
//            player.heal(100)
//        }
//        else if (action_player == 3){
//            player.fireball(enemy)
//        }
//        else{
//            println("Incorrect action!")
//            continue
//        }
//        println()
//
//        val action_enemy = Random.nextInt(1, 3)
//        if (action_enemy == 1){
//            enemy.attack(player)
//        }
//        else if (action_enemy == 2){
//            enemy.heal(25)
//        }
//        else if (action_enemy == 3){
//            enemy.fireball(player)
//        }
//
//        println("\n>==================================<")
//        player.printStatus()
//        enemy.printStatus()
//        println(">==================================<\n")
//
//    turns += 1
//    }
//}
//
//
