package org.adamalang.gossip;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GarbageMap<T> implements Iterable<T> {

    private class AgingItem {
        public final T item;
        public final long time;

        public AgingItem(T item, long now) {
            this.item = item;
            this.time = now;
        }

    }

    private final HashMap<String, AgingItem> items;

    public GarbageMap() {
        this.items = new HashMap<>();
    }

    public Collection<String> keys() {
        return items.keySet();
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<AgingItem> it = items.values().iterator();
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return it.next().item;
            }
        };
    }

    public void put(String key, T value, long now) {
        items.put(key, new AgingItem(value, now));
    }

    public T get(String key) {
        AgingItem item = items.get(key);
        if (item != null) {
            return item.item;
        }
        return null;
    }

    public T remove(String key) {
        AgingItem item = items.remove(key);
        if (item != null) {
            return item.item;
        }
        return null;
    }

    public int gc(long now) {
        int removals = 0;
        Iterator<Map.Entry<String, AgingItem>> it = items.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, AgingItem> entry = it.next();
            long age = now - entry.getValue().time;
            if (age > Constants.MILLISECONDS_TO_SIT_IN_GARBAGE_MAP) {
                it.remove();
                removals++;
            }
        }
        return removals;
    }
}
