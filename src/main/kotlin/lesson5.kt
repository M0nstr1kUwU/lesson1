import javax.management.Descriptor
import kotlin.random.Random


class Quest(
    val id: String,
    val name: String,
    val description: String,
    val requiredItemId: String? = null,
    val rewardGold: Int = 0,
    val rewardItem: Item? = null
){
    var isCompleted: Boolean = false
    var isActive: Boolean = false


    fun checkCompletion(player: Player): Boolean{
        if (!isCompleted && isActive){
            if (requiredItemId != null && player.inventory.hasItem(requiredItemId))
                completeQuest(player)
            return true
        }
        return false
    }

    fun completeQuest(player: Player){
        isCompleted = true
        isActive = false

        println("\n КВЕСТ ВЫПОЛНЕНУСПЕШНО: $name")
        println("Награда:")

        if (rewardGold > 0){
            println("- Золото: $rewardGold")
            // Реализация золота в кошелёк игрока
        }

        if (rewardItem != null){
            println("- Предмет: ${rewardItem.name}")
            player.inventory.addItem(rewardItem)
        }
    }

    fun displayInfo(){
        val status = when{
            isCompleted -> "ВЫПОЛНЕН"
            isActive -> "АКТИВЕН"
            else -> "НЕАКТИВЕН"
        }
        println("[$status] $name: $description")
    }
}


class QuestManager{
    // mutableMapOf - изменяемый словарь где будет храниться квесты игрока
    // String - тип ключа (id квеста)
    // Quest - тип значения (объекта квеста)
    private val quests = mutableMapOf<String, Quest>()


    fun addQuest(quest: Quest){
        quests[quest.id] = quest
    }

    fun startQuest(questId: String): Boolean{
        val quest = quests[questId]
        if (quest != null && !quest.isCompleted){
            quest.isActive = true
            println("Квест активирован!")
            return true
        }
        return false
    }

    fun checkAllQuests(player: Player){
        quests.values.filter { it.isActive }.forEach{ quest ->
            quest.checkCompletion(player)
        }
    }

    fun displayQuests(){
        if (quests.isEmpty()){
            println("Нет доступных квестов")
        }else{
            println("\n>>> ЖУРНАЛ КВЕСТОВ <<<")
            quests.values.forEach{ quest ->
                quest.displayInfo()
            }
        }
    }

    fun qetActiveQuests(): List<Quest>{
        // .toList() - преобразует результат в неизменяемый список
        return quests.values.filter { it.isActive }.toList()
    }
}

class NPC(val name: String, val description: String){
    private  val dialogues = mutableMapOf<String, String>()

    fun addDialoque(playerPrase: String, npcResponse: String){
        dialogues[playerPrase] = npcResponse
    }
    fun talk(){
        println("\n=== РАЗГОВОР С $name ===")
        println("$name: $description")

        if (dialogues.isEmpty()){
            println("$name не о чем с вами сейчас говорить")
            return // немедленный выход из метода
        }

        println("\nВарианты Диалога:")
        dialogues.keys.forEachIndexed {index, phrase ->
            println("${index + 1}. $phrase")
        }
        println("${dialogues.size + 1}. Уйти")

        println("Выберите Реплику:")
        val choise = readln().toIntOrNull() ?: 0

        if (choise in 1..dialogues.size){
            val playerPhrase = dialogues.keys.toList()[choise - 1]
            val npcResponse = dialogues[playerPhrase]


            println("\nВы: $playerPhrase")
            println("$name: $npcResponse")
        }else{
            println("Вы прощаетесь с $name")
        }
    }
}




