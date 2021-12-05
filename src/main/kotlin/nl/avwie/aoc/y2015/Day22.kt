package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day

object Day22 : Day<Int, Int> {
    override fun part1(): Int {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    data class Entity(val hitPoints: Int, val armor: Int, val mana: Int)

    data class Magic(
        val cost: Int,
        val timer: Int,
        val applySelf: Magic.(Entity) -> Entity = { it },
        val applyOther: Magic.(Entity) -> Entity = { it }
    ) {
        fun next(): Magic? = when (timer) {
            0 -> null
            else -> copy(timer = timer - 1)
        }
    }

    val MagicMissile = Magic(53, 0,
        applyOther = { it.copy(hitPoints = it.hitPoints - 4) }
    )
    val Drain = Magic(73, 0,
        applySelf = { it.copy(hitPoints = it.hitPoints + 2) },
        applyOther = { it.copy(hitPoints = it.hitPoints - 2)}
    )

    val Shield = Magic(113, 7,
        applySelf = {
            when (timer) {
                6 -> it.copy(armor = it.armor + 7)
                0 -> it.copy(armor = it.armor - 7)
                else -> it
            }
        }
    )

    val Poison = Magic(173, 6,
        applyOther = { if (timer < 6) it.copy(hitPoints = it.hitPoints - 3) else it }
    )
    val Recharge = Magic(229, 5,
        applySelf = { if (timer < 5) it.copy(mana = it.mana + 101) else it }
    )

    data class GameState(
        val player: Entity,
        val boss: Entity,
        val activeMagic: List<Magic>,
        val bossDamage: Int
    ) {
        val bossWins: Boolean = boss.hitPoints > 0 && player.hitPoints <= 0
        val playerWins: Boolean = boss.hitPoints <= 0 && player.hitPoints > 0
        val undecided: Boolean = (!bossWins && !playerWins) || (bossWins && playerWins)

        fun turn(playerAction: Magic?): GameState {

            // first apply the current effects and the new one and subtract the mana
            val currentMagic = (activeMagic + playerAction).filterNotNull()
            val actualBoss = currentMagic.fold(boss) { b, m -> m.applyOther(m, b) }
            val actualPlayer = currentMagic.fold(player) { p, m -> m.applySelf(m, p) }.let { player ->
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
}