package ads;

import ads.utils.Utility;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class QueryParser {
    private static QueryParser instance = null;

    // DFS to build rewrite query (too slow)
    private void QueryRewriteHelper(int index,
                                    int len,
                                    ArrayList<String> queryTermsTemp,
                                    List<List<String>> allSynonymList,
                                    List<List<String>> res) {
        if (index == len) {
            res.add(queryTermsTemp);
            return;
        }

        // allSynonymList.get(index) -> query中第index+1个term对应的所有近义词
        List<String> synonyms = allSynonymList.get(index);
        for (int i = 0; i < synonyms.size(); i++) {
            ArrayList<String> queryTerms = (ArrayList<String>) queryTermsTemp.clone();
            queryTerms.add(synonyms.get(i));
            QueryRewriteHelper(index + 1, len, queryTerms, allSynonymList, res);
        }
    }

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

    // 对应进来的query，根据里面的term找对应的近义词，把所有rewritten query和query本身一起返回
    // raw query: nike running shoe
    // running == jogging
    // nike running shoe => nike jogging shoe
    // ...

//    public List<List<String>> QueryRewrite(String query, String memcachedServer,int memcachedPortal) {
//        List<List<String>> res = new ArrayList<>();
//        // 把query拆成一个个term - 分词
//        List<String> tokens = Utility.cleanedTokenize(query);
//        // 把query分词并用'_'连起来，作为key去memcached找近义词
//        String query_key = Utility.strJoin(tokens, "_");
//        try {
//            MemcachedClient cache = new MemcachedClient(new InetSocketAddress(memcachedServer, memcachedPortal));
//            if(cache.get(query_key) instanceof List) {
//                @SuppressWarnings("unchecked")
//                List<String> synonyms = (ArrayList<String>)cache.get(query_key);
//                for(String synonym : synonyms) {
//                    List<String> token_list = new ArrayList<>();
//                    // 把rewritten query再分开
//                    String[] s = synonym.split("_");
//                    Collections.addAll(token_list, s);
//                    res.add(token_list);
//                }
//            }
//            else {
//                res.add(tokens);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("query_rewrite = " + res);
//        return res;
//    }

    public List<List<String>> QueryRewrite(String query, String memcachedServer, int synonymMemcachedPortal) {

        List<String> queryTerms = Utility.cleanedTokenize(query);
        System.out.println("come on!");
        List<List<String>> res = new ArrayList<>();
        List<List<String>> allSynonymList = new ArrayList<>();

        try{
            MemcachedClient cache = new MemcachedClient(new InetSocketAddress(memcachedServer, synonymMemcachedPortal));
            for (String queryTerm : queryTerms) {

                System.out.println(cache.get(queryTerm));
                String item = (String)cache.get(queryTerm);

                if (item != null) {
                    List<String> synonymList = new ArrayList<>(Arrays.asList(item.split("_")));
                    System.out.println("myList" + synonymList);
                    allSynonymList.add(synonymList);
                } else {
                    List<String> synonymList = new ArrayList<>();
                    synonymList.add(queryTerm);
                    allSynonymList.add(synonymList);
                }

//                if (cache.get(queryTerm) instanceof List) {
//                    List<String> synonymList = (List<String>)cache.get(queryTerm);
//                    System.out.println("synonymList from memcached 333 = " + cache.get(queryTerm));
//                    allSynonymList.add(synonymList);
//                } else {
//                    List<String> synonymList = new ArrayList<>();
//                    synonymList.add(queryTerm);
//                    allSynonymList.add(synonymList);
//                }

            }
            int len = queryTerms.size();
            System.out.println("len of queryTerms = " + len);
            ArrayList<String> queryTermsTemp = new ArrayList<>();
            QueryRewriteHelper(0, len, queryTermsTemp, allSynonymList, res);
        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("query_rewrite = " + res);
        return res;
    }
}
