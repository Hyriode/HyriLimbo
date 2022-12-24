package fr.hyriode.limbo.util;

/**
 * Created by AstFaster
 * on 21/12/2022 at 22:54
 */
public interface Cast<T> {

    @SuppressWarnings("unchecked")
    default <C extends T> C cast() {
        return (C) this;
    }

}
