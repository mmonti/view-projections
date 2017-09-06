package com.mmonti.view.model.providers;

public interface Provider<S, T, R> {

    /**
     *
     * @param rootEntity
     * @param target
     * @return
     */
    R provide(final S rootEntity, final T target);

}
