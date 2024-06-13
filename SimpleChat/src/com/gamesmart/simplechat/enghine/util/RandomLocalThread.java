package com.gamesmart.simplechat.enghine.util;

import java.security.SecureRandom;

public class RandomLocalThread {
    static final String BadRange = "bound must be greater than origin";
    // SecureRandom is thread-safe, but we can improve efficiency by giving one to each thread
    // because they are both subjected to synchronization
    private static final ThreadLocal<SecureRandom> random = new ThreadLocal<SecureRandom>(){
        @Override
        protected SecureRandom initialValue() {
            return new SecureRandom();
        }
    };

    /**
     * remove current thread value of SecureRandom after use get()
     * */
    public static SecureRandom get() {
        SecureRandom secureRandom = random.get();
        random.remove();
        return secureRandom;
    }

    /**
     * Returns a pseudorandom {@code int} value between the specified
     * origin (inclusive) and the specified bound (exclusive).
     *
     * @param origin the least value returned
     * @param bound the upper bound (exclusive)
     * @return a pseudorandom {@code int} value between the origin
     *         (inclusive) and the bound (exclusive)
     */
    public static int nextInt(int origin, int bound){
        if (origin >= bound){
            throw new IllegalArgumentException(BadRange);
        }
        bound -=1;
        return get().nextInt(bound+1-origin)+origin;
    }
    /**
     *  Returns a pseudorandom {@code double} value between the specified
     *  origin (inclusive) and the specified bound (exclusive).
     *
     *  @param origin the least value returned
     *  @param bound the upper bound (exclusive)
     *  @return a pseudorandom {@code double} value between the origin
     *  (inclusive) and the bound (exclusive)
     */
    public static double nextDouble(double origin, double bound) {
        if (origin >= bound){
            throw new IllegalArgumentException(BadRange);
        }
        return get().nextDouble()*(bound-origin)+origin;
    }
}
