import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day7 : Day<Long, Long> {

    val tree get() = Input.inputLines(2022, 7).parse()

    override fun part1(): Long = tree
        .flattened
        .filterIsInstance<Directory>()
        .filter { node -> node.size <= 100000 }
        .sumOf { it.size }

    override fun part2(): Long = tree.let { root ->
        root.flattened
        .filterIsInstance<Directory>()
        .filter { it.size >= 30000000L -  (70000000L - root.size) }
        .minByOrNull { it.size }!!
        .size
    }


    fun Sequence<String>.parse() = with(TreeBuilder()) {
        this@parse.forEach { line ->
            when {
                line.startsWith("$ cd") -> cd(line.substring(5))
                line.startsWith("dir") -> add(Directory(line.substring(4)))
                line.first().isDigit() -> line
                    .split(" ")
                    .also { (size, name) ->
                        add(File(name, size.toLong()))
                    }
                else -> {}
            }
        }
        root.contents.first() as Directory
    }

    sealed interface Node {
        val size: Long
    }

    data class File(val name: String, override val size: Long): Node
    class Directory(val name: String, val contents: MutableList<Node> = mutableListOf()) : Node {
        override val size: Long
            get() = contents.sumOf { it.size }

        val flattened get() : List<Node> = listOf(this) + contents.flatMap {
            when (it) {
                is Directory -> it.flattened
                else -> listOf(it)
            }
        }
    }

    class TreeBuilder {

        private val directoryStack = ArrayDeque(
            listOf(Directory("<root>", contents = mutableListOf(Directory("/"))))
        )

        val root get() = directoryStack.last()
        val currentDir get() = directoryStack.first()

        fun add(node: Node) = currentDir.contents.add(node)

        fun cd(dirName: String) {
            when (dirName) {
                ".." -> directoryStack.removeFirst()
                else -> {
                    directoryStack.addFirst(directoryStack.first().contents.filterIsInstance<Directory>().first { it.name == dirName })
                }
            }
        }
    }
}