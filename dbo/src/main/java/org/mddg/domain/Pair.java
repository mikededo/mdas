package org.mddg.domain;

public class Pair<L, R> {

    private L left;
    private R right;

    public static <L, R> Pair<L, R> of(L left, R right) {
        Pair<L, R> pair = new Pair<>();
        pair.left = left;
        pair.right = right;
        return pair;
    }

    public L left() {
        return this.left;
    }

    public R right() {
        return this.right;
    }
}
