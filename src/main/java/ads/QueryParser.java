package ads;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;


public class QueryParser {
    private static QueryParser instance = null;

    protected QueryParser() {

    }

    public static QueryParser getInstance() {
        if(instance == null) {
            instance = new QueryParser();
        }
        return instance;
    }

    public List<String> QueryUnderstand(String query) {
        List<String> tokens = Utility.cleanedTokenize(query);
        return tokens;
    }

    // 对应进来的query，根据里面的term找对应的近义词
    public List<List<String>> QueryRewrite(String query, String memcachedServer,int memcachedPortal) {
        List<List<String>> res = new ArrayList<>();
        List<String> tokens = Utility.cleanedTokenize(query);
        // 把query分词并用'_'连起来，作为key去memcached找近义词
        String query_key = Utility.strJoin(tokens, "_");
        try {
            MemcachedClient cache = new MemcachedClient(new InetSocketAddress(memcachedServer, memcachedPortal));
            if(cache.get(query_key) instanceof List) {
                @SuppressWarnings("unchecked")
                List<String>  synonyms = (ArrayList<String>)cache.get(query_key);
                for(String synonym : synonyms) {
                    List<String> token_list = new ArrayList<>();
                    // 把rewritten query再分开
                    String[] s = synonym.split("_");
                    for(String w : s) {
                        token_list.add(w);
                    }
                    res.add(token_list);
                }
            }
            else {
                res.add(tokens);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
