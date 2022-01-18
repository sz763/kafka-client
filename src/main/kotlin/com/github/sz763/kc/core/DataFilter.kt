package com.github.sz763.kc.core

import java.util.function.Predicate

@FunctionalInterface
interface DataFilter<E> : Predicate<E>
