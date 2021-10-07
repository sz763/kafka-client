package com.github.svart63.kc.core

import java.util.function.Predicate

@FunctionalInterface
interface DataFilter<E> : Predicate<E>
