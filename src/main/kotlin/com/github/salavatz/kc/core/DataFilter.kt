package com.github.salavatz.kc.core

import java.util.function.Predicate

@FunctionalInterface
interface DataFilter<E> : Predicate<E>
