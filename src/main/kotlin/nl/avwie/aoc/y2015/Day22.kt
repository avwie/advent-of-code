package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.search.Dijkstra
import nl.avwie.aoc.common.search.Implementation
import nl.avwie.aoc.common.search.WithCost

object Day22 : Day<Int, Int> {

    override fun part1(): Int = simulate(0)

    override fun part2(): Int = simulate(1)

    fun simulate(hardMode: Int): Int {
        val boss = Entity(55, 0, 0)
        val player = Entity(50, 0, 500)
        val initialState = GameState(player, boss, listOf(), 8, hardMode)
        val search = Dijkstra(SearchImplementation())
        val initialSearchNode = SearchImplementation.Node(initialState, listOf())
        val result = search.search(initialSearchNode)
        return result?.magicUsed?.sumOf { it.cost } ?: 0
    }

    data class Entity(val hitPoints: Int, val armor: Int, val mana: Int)

    data class Magic(
        val name: String,
        val cost: Int,
        val timer: Int,
        val applySelf: Magic.(Entity) -> Entity = { it },
        val applyOther: Magic.(Entity) -> Entity = { it }
    ) {
        fun next(): Magic? = when {
            timer <= 0 -> null
            else -> copy(timer = timer - 1)
        }
    }

    val MagicMissile = Magic("Magic Missile",53, 0,
        applyOther = { it.copy(hitPoints = it.hitPoints - 4) }
    )
    val Drain = Magic("Drain", 73, 0,
        applySelf = { it.copy(hitPoints = it.hitPoints + 2) },
        applyOther = { it.copy(hitPoints = it.hitPoints - 2)}
    )

    val Shield = Magic("Shield", 113, 7,
        applySelf = {
            when (timer) {
                6 -> it.copy(armor = it.armor + 7)
                0 -> it.copy(armor = it.armor - 7)
                else -> it
            }
        }
    )

    val Poison = Magic("Poison", 173, 6,
        applyOther = { if (timer < 6) it.copy(hitPoints = it.hitPoints - 3) else it }
    )
    val Recharge = Magic("Recharge", 229, 5,
        applySelf = { if (timer < 5) it.copy(mana = it.mana + 101) else it }
    )

    data class GameState(
        val player: Entity,
        val boss: Entity,
        val activeMagic: List<Magic>,
        val bossDamage: Int,
        val hardMode: Int
    ) {
        val bossWins: Boolean = player.hitPoints <= 0
        val playerWins: Boolean = boss.hitPoints <= 0

        fun turn(playerAction: Magic?): GameState {
            // in hard mode we substract on player turn
            var actualPlayer = if (playerAction == null) player else player.copy(hitPoints = player.hitPoints - hardMode)
            if (actualPlayer.hitPoints <= 0 && playerAction == null)
                return copy(player = actualPlayer)

            // first apply the current effects and the new one and subtract the mana
            val currentMagic = (activeMagic + playerAction).filterNotNull()
            val actualBoss = currentMagic.fold(boss) { b, m -> m.applyOther(m, b) }
            actualPlayer = currentMagic.fold(actualPlayer) { p, m -> m.applySelf(m, p) }.let { player ->
                player.copy(mana = player.mana - (playerAction?.cost ?: 0))
            }

            // boss turn
            val playerDamage = when {
                (playerAction == null && actualBoss.hitPoints > 0) -> (bossDamage - actualPlayer.armor).coerceAtLeast(1)
                else -> 0
            }

            return this.copy(
                player = actualPlayer.copy(hitPoints = actualPlayer.hitPoints - playerDamage),
                boss = actualBoss,
                activeMagic = currentMagic.mapNotNull { it.next() }
            )
        }
    }

    class SearchImplementation : Implementation<SearchImplementation.Node>, WithCost<SearchImplementation.Node> {

        class Node(val state: GameState, val magicUsed: List<Magic>) {
            val magicAvailable = listOf(MagicMissile, Drain, Shield, Poison, Recharge)
                .filter { it.cost <= state.player.mana }
                .filter { magic -> state.activeMagic.filter { it.timer > 0 }.none { it.name == magic.name } }
        }

        override fun isFound(item: Node): Boolean = !item.state.bossWins && item.state.playerWins

        override fun next(item: Node): Iterable<Node> {
            if (item.state.bossWins)
                return listOf()

            return item.magicAvailable.map { magic ->
                Node(item.state.turn(magic).turn(null), item.magicUsed + magic)
            }
        }

        override fun cost(item: Node): Double {
            return item.magicUsed.sumOf { it.cost }.toDouble()
        }
    }
}