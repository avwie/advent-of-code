package nl.avwie.aoc.common.statemachine

import kotlin.reflect.KClass

open class State<E, C> {
    private var stateMachine: StateMachine<E, C>? = null

    val context : C get() = stateMachine!!.context

    fun setStateMachine(stateMachine: StateMachine<E, C>) {
        this.stateMachine = stateMachine
    }

    fun transition(newState: State<E, C>) {
        stateMachine!!.transition(newState)
    }

    open fun onEnter(previousState: State<E, C>) {}
    open fun handleEvent(event: E) {}
    open fun onExit(newState: State<E, C>) {}

    class Builder<E : Any, C> {
        private var onEnterBlock: (State<E, C>.(prev: State<E, C>) -> Unit)? = null
        private var onExitBlock: (State<E, C>.(next: State<E, C>) -> Unit)? = null
        private val eventHandlers = mutableMapOf<KClass<*>, EventHandler<E, C, *>>()

        fun enter(block: State<E, C>.(prev: State<E, C>) -> Unit) {
            onEnterBlock = block
        }

        fun exit(block: State<E, C>.(next: State<E, C>) -> Unit) {
            onExitBlock = block
        }

        fun <Event : E> on(klass: KClass<Event>, block: State<E, C>.(event: Event) -> Unit) {
            eventHandlers[klass] = EventHandler(block)
        }

        inline fun <reified Event : E> on(noinline block: State<E, C>.(event: Event) -> Unit) {
            on(Event::class, block)
        }

        fun build(): State<E, C> = object : State<E, C>() {
            override fun onEnter(previousState: State<E, C>) {
                onEnterBlock?.invoke(this, previousState)
            }

            override fun onExit(newState: State<E, C>) {
                onExitBlock?.invoke(this, newState)
            }

            override fun handleEvent(event: E) {
                val klass = event::class
                eventHandlers[klass]?.invoke(this, event)
            }
        }

        data class EventHandler<E : Any, C, Event : E>(private val block: State<E, C>.(event: Event) -> Unit) {

            @Suppress("UNCHECKED_CAST")
            operator fun invoke(state: State<E, C>, event: E) {
                block.invoke(state, event as Event)
            }
        }
    }
}

fun <E : Any, C> state(block: State.Builder<E, C>.() -> Unit): State<E, C> {
    val builder = State.Builder<E, C>()
    block(builder)
    return builder.build()
}