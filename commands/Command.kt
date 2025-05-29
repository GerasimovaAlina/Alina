package commands

interface Command {
    fun execute(args: List<String>)
    fun help(): String
}
