package com.mmonti.view.model.providers;

import java.util.HashMap;
import java.util.Map;

public class ProviderRegistry {

    private Map<Class, Provider> providers = new HashMap<>();

    public void register(Provider provider) {
        providers.put(provider.getClass(), provider);
    }

    public Provider get(Class type) {
        return providers.get(type);
    }
}
