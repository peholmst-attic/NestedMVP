package org.vaadin.samples.nestedmvp.api;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

public final class PresenterViewFactory {

    private PresenterViewFactory() {
    }

    public static <V extends View, P extends Presenter<V>> PresenterView<V, P> createPresenterView(
        Class<? extends P> presenterClass, Optional<Presenter<?>> parent) {
        Objects.requireNonNull(presenterClass);
        Objects.requireNonNull(parent);
        try {
            P presenter;
            if (parent.isPresent()) {
                presenter = presenterClass.getConstructor(Presenter.class).newInstance(parent.get());
            } else {
                presenter = presenterClass.newInstance();
            }
            ServiceLoader<V> viewLoader = ServiceLoader.load(presenter.getViewClass());
            Iterator<V> iterator = viewLoader.iterator();
            if (!iterator.hasNext()) {
                throw new IllegalArgumentException("Could not find implementation of view interface " + presenter.getViewClass().getName());
            }
            V view = iterator.next();
            presenter.setView(view);
            return new PresenterView<>(view, presenter);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create PresenterView for presenter class " + presenterClass.getName(), ex);
        }
    }

    public static <V extends View, P extends Presenter<V>> PresenterView<V, P> createPresenterView(
        Class<? extends P> presenterClass, Presenter<?> parent) {
        return createPresenterView(presenterClass, Optional.of(parent));
    }

    public static <V extends View, P extends Presenter<V>> PresenterView<V, P> createPresenterView(
        Class<? extends P> presenterClass) {
        return createPresenterView(presenterClass, Optional.empty());
    }

    public static class PresenterView<V extends View, P extends Presenter<V>> {
        private final V view;
        private final P presenter;

        public PresenterView(V view, P presenter) {
            this.view = view;
            this.presenter = presenter;
        }

        public V getView() {
            return view;
        }

        public P getPresenter() {
            return presenter;
        }
    }
}
