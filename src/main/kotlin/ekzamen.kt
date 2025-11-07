import java.sql.DatabaseMetaData
import java.util.stream.Stream

// 1) конечна)
open class Weapon(
    val name: String,
    val damage: Int,
)

class MagicSword(
    name: String,
    damage: Int,
    val manaCost: Int
): Weapon(
    name,
    damage
)

fun main() {
    // 2)
    val chLvL: Int = readln().toInt()
    val chName: String = "M0nstr1k"

    if (chLvL > 5) {
        println("$chName - опытный Патапим!\n")
    } else {
        println("$chName - ещё зелёный\n")
    }


    // 3)
    for (i in (1..10)) {
        println("${i}...")
    }


    // 4)
    calculateExp(level = readln().toInt())


    // 5)
    val sword = Weapon("Magic Sword", 13)
    displayInfo(sword.name, sword.damage)


    // 6)
    val magicSword = MagicSword("SW0RD1", 13, 10)
    displayInfo(magicSword.name, magicSword.damage, magicSword.manaCost)
}


fun calculateExp(level: Int){
    println("Exp до следующего уровня надо: ${level * 100}\n")
}

fun displayInfo(name: String, damage: Int){
    println("$name(Damage: $damage)")
}

fun displayInfo(name: String, damage: Int, manaCost: Int){
    println("$name(Damage: $damage, Mana: $manaCost)")
}
