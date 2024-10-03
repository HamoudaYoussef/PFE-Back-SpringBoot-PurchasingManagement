package biz.picosoft.demo.Workflow.helper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("op")
@Component("op")
public class Operation
{
    public <T> T[] asArray(T... objects)
    {
        return objects;
    }

    public <T> List<T> asList(T... objects)
    {
        return Arrays.asList(objects);
    }

    public <K, V> MapBuilder<K, V> asMap()
    {
        return new MapBuilder();
    }
}



class MapBuilder<K, V>
{
    private Map<K, V> map = new HashMap<K, V>();

    public MapBuilder<K, V> put(K key, V value)
    {
        map.put(key, value);
        return this;
    }

    public Map<K, V> done()
    {
        return map;
    }
}
