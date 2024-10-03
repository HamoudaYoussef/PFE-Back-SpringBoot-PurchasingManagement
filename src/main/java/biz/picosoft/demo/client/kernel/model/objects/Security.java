package biz.picosoft.demo.client.kernel.model.objects;

import java.util.Set;

public class Security {
    private Integer level;
    private Set<String> readers;
    private Set<String> authors;
    private Set<String> tempreaders;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<String> getReaders() {
        return readers;
    }

    public void setReaders(Set<String> readers) {
        this.readers = readers;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public Set<String> getTempreaders() {
        return tempreaders;
    }

    public void setTempreaders(Set<String> tempreaders) {
        this.tempreaders = tempreaders;
    }
}
