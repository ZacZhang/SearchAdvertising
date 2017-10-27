package ads;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

import ads.models.Ad;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

public class AdsSelector {
    private static AdsSelector instance = null;
    //private int EXP = 7200;
    private int numDocs = 10840;
    private String mMemcachedServer;
    private int mMemcachedPortal;
    private int mFeatureMemcachedPortal;
    private int mTFMemcachedPortal;
    private int mDFMemcachedPortal;
    private String m_logistic_reg_model_file;
    private String m_gbdt_model_path;
    private String mysql_host;
    private String mysql_db;
    private String mysql_user;
    private String mysql_pass;
    private Boolean enable_pClick;
    private Boolean enableTFIDF;
    MemcachedClient tfCacheClient;
    MemcachedClient dfCacheClient;

    protected AdsSelector(String memcachedServer, int memcachedPortal, int featureMemcachedPortal,int tfMemcachedPortal,
                          int dfMemcachedPortal, String logistic_reg_model_file, String gbdt_model_path, String mysqlHost,
                          String mysqlDb,String user,String pass)
    {
        mMemcachedServer = memcachedServer;
        mMemcachedPortal = memcachedPortal;
        mTFMemcachedPortal = tfMemcachedPortal;
        mDFMemcachedPortal = dfMemcachedPortal;
        mFeatureMemcachedPortal = featureMemcachedPortal;
        mysql_host = mysqlHost;
        mysql_db = mysqlDb;
        mysql_user = user;
        mysql_pass = pass;
        m_logistic_reg_model_file = logistic_reg_model_file;
        m_gbdt_model_path = gbdt_model_path;
        //enable_pClick = false;
        enable_pClick = true;
        enableTFIDF = true;
        String tf_address = mMemcachedServer + ":" + mTFMemcachedPortal;
        String df_address = mMemcachedServer + ":" + mDFMemcachedPortal;
        try {
            //tfCacheClient = new MemcachedClient(new ConnectionFactoryBuilder().setDaemon(true).setFailureMode(FailureMode.Retry).build(), AddrUtil.getAddresses(tf_address));
            tfCacheClient = new MemcachedClient(new InetSocketAddress(mMemcachedServer,mTFMemcachedPortal));

            dfCacheClient = new MemcachedClient(new ConnectionFactoryBuilder()
                                                .setDaemon(true)
                                                .setFailureMode(FailureMode.Retry)
                                                .build(), AddrUtil.getAddresses(df_address));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AdsSelector getInstance(String memcachedServer, int memcachedPortal, int featureMemcachedPortal,
                                          int tfMemcachedPortal, int dfMemcachedPortal, String logistic_reg_model_file,
                                          String gbdt_model_path, String mysqlHost, String mysqlDb, String user, String pass) {
        if(instance == null) {
            instance = new AdsSelector(memcachedServer, memcachedPortal, featureMemcachedPortal, tfMemcachedPortal,
                    dfMemcachedPortal, logistic_reg_model_file, gbdt_model_path, mysqlHost, mysqlDb, user,pass);
        }
        return instance;
    }

    // TF*IDF = log(numDocs / (docFreq + 1)) * sqrt(tf) * (1/sqrt(docLength))
    private double calculateTFIDF(Long adId, String term, int docLength) {
        String tfKey = adId.toString() + "_" + term;

        String tf = (String)tfCacheClient.get(tfKey);
        String df =  (String)dfCacheClient.get(term);

        if(tf != null && df != null) {
            int tfVal = Integer.parseInt(tf);
            int dfVal = Integer.parseInt(df);
            double dfScore = Math.log10(numDocs * 1.0 / (dfVal + 1));
            double tfScore = Math.sqrt(tfVal);
            double norm = Math.sqrt(docLength);
            return (dfScore * tfScore) / norm;
        }
        return 0.0;
    }

    private double getRelevanceScoreByTFIDF(Long adId, int numOfKeyWords, List<String> queryTerms) {
        double relevanceScore = 0.0;
        // 把query中每个term跟对应adId的TF-IDF值加起来 (因为TF-IDF指的是一个term和某个adId的关联度，一个query有多个term，故加起来)
        for(String term : queryTerms) {
            relevanceScore += calculateTFIDF(adId, term, numOfKeyWords);
        }
        return relevanceScore;
    }

    // 去inverted index中选广告，所以要用到memcached
    public List<Ad> selectAds(List<String> queryTerms, String device_id, String device_ip, String query_category)
    {
        List<Ad> adList = new ArrayList<>();
        // key: ad id value: matched term count
        HashMap<Long, Integer> matchedAds = new HashMap<>();
        try {
            MemcachedClient cache = new MemcachedClient(new InetSocketAddress(mMemcachedServer, mMemcachedPortal));

            // 针对query中的每个term，都要去访问inverted index
            // keywords term跟query term中是否有一样的，有则把那个ad作为一个candidate
            for(String queryTerm : queryTerms) {
                System.out.println("selectAds queryTerm = " + queryTerm);
                @SuppressWarnings("unchecked")
                Set<Long> adIdList = (Set<Long>)cache.get(queryTerm);
                // 对于每一个query term, 去cache里找到ad list, ad list不为空则说明matched
                if(adIdList != null && adIdList.size() > 0) {
                    for(Object adId : adIdList) {
                        Long key = (Long)adId;
                        if(matchedAds.containsKey(key)) {
                            int count = matchedAds.get(key) + 1;
                            matchedAds.put(key, count);
                        } else {
                            matchedAds.put(key, 1);
                        }
                    }
                }
            }

            // 去mysql取detail信息, 就算只有一个term match也把ad拿过来
            for(Long adId : matchedAds.keySet()) {
                //System.out.println("selectAds adId = " + adId);
                MySQLAccess mysql = new MySQLAccess(mysql_host, mysql_db, mysql_user, mysql_pass);
                Ad ad = mysql.getAdData(adId);
                double relevanceScore = matchedAds.get(adId) * 1.0 / ad.keyWords.size();
                double relevanceScoreTFIDF = getRelevanceScoreByTFIDF(adId, ad.keyWords.size(), queryTerms);

                if(enableTFIDF) {
                    ad.relevanceScore = relevanceScoreTFIDF;
                } else {
                    ad.relevanceScore = relevanceScore;
                }

                // System.out.println("selectAds relevanceScore = " + ad.relevanceScore);
                ad.pClick = 0.0;
                adList.add(ad);
            }

            System.out.println("selected " + adList.size() + " ads in L0");

            if (enable_pClick) {
                // featureMemcachedPortal - 11218
                MemcachedClient featureCacheClient = new MemcachedClient(new InetSocketAddress(mMemcachedServer,
                        mFeatureMemcachedPortal));
                // System.out.println("mFeatureMemcachedPortal = " + mFeatureMemcachedPortal);
                // calculate pClick
                for(Ad ad : adList) {
                    predictCTR(ad, queryTerms, device_id, device_ip, query_category, featureCacheClient);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return adList;
    }

    // 预测当前用户是否会点这个广告，每个feature都从memcached读出来，然后把feature送到CTRModel里面进行预测
    private void predictCTR(Ad ad, List<String> queryTerms, String device_id, String device_ip, String query_category,
                            MemcachedClient featureCacheClient) {
        // construct features, note that the order of features must be as follows
        ArrayList<Double> features = new ArrayList<>();

        // device_ip_click
        String device_ip_click_key = "dipc_" + device_ip;
        @SuppressWarnings("unchecked")
        String device_ip_click_val_str = (String)featureCacheClient.get(device_ip_click_key);
        Double device_ip_click_val = 0.0;
        if (device_ip_click_val_str != null && !Objects.equals(device_ip_click_val_str, "")) {
            device_ip_click_val = Double.parseDouble(device_ip_click_val_str);
        }
        features.add(device_ip_click_val);

        // System.out.println("device_ip_click_key = " + device_ip_click_key);
        // System.out.println("device_ip_click_val = " + device_ip_click_val);

        // device_ip_impression
        String device_ip_impression_key = "dipi_" + device_ip;
        @SuppressWarnings("unchecked")
        String device_ip_impression_val_str = (String)featureCacheClient.get(device_ip_impression_key);
        Double device_ip_impression_val = 0.0;
        if (device_ip_impression_val_str != null && !Objects.equals(device_ip_impression_val_str, "")) {
            device_ip_impression_val = Double.parseDouble(device_ip_impression_val_str);
        }
        features.add(device_ip_impression_val);
        // System.out.println("device_ip_impression_val = " + device_ip_impression_val);


        // device_id_click
        String device_id_click_key = "didc_" + device_id;
        @SuppressWarnings("unchecked")
        String device_id_click_val_str = (String)featureCacheClient.get(device_id_click_key);
        Double device_id_click_val = 0.0;
        if (device_id_click_val_str != null && !Objects.equals(device_id_click_val_str, "")) {
            device_id_click_val = Double.parseDouble(device_id_click_val_str);
        }
        features.add(device_id_click_val);
        // System.out.println("device_id_click_key = " + device_id_click_key);
        // System.out.println("device_id_click_val = " + device_id_click_val);
        // System.out.println("device_id_click_val_str = " + device_id_click_val_str);

        // device_id_impression
        String device_id_impression_key = "didi_" + device_id;
        @SuppressWarnings("unchecked")
        String device_id_impression_val_str = (String)featureCacheClient.get(device_id_impression_key);
        Double device_id_impression_val = 0.0;
        if (device_id_impression_val_str != null && !Objects.equals(device_id_impression_val_str, "")) {
            device_id_impression_val = Double.parseDouble(device_id_impression_val_str);
        }
        features.add(device_id_impression_val);
        // System.out.println("device_id_impression_key = " + device_id_impression_key);
        // System.out.println("device_id_impression_val = " + device_id_impression_val);

        // ad_id_click
        String ad_id_click_key = "aidc_" + ad.adId.toString();
        @SuppressWarnings("unchecked")
        String ad_id_click_val_str = (String)featureCacheClient.get(ad_id_click_key);
        Double ad_id_click_val = 0.0;
        if (ad_id_click_val_str != null && !Objects.equals(ad_id_click_val_str, "")) {
            ad_id_click_val = Double.parseDouble(ad_id_click_val_str);
        }
        features.add(ad_id_click_val);

        // ad_id_impression
        String ad_id_impression_key = "aidi_" + ad.adId.toString();
        @SuppressWarnings("unchecked")
        String ad_id_impression_val_str = (String)featureCacheClient.get(ad_id_impression_key);
        Double ad_id_impression_val = 0.0;
        if (ad_id_impression_val_str != null && !Objects.equals(ad_id_impression_val_str, "")) {
            ad_id_impression_val = Double.parseDouble(ad_id_impression_val_str);
        }
        features.add(ad_id_impression_val);

        String query = Utility.strJoin(queryTerms, "_");
        // query_campaign_id_click
        String query_campaign_id_click_key = "qcidc_" + query + "_" + ad.campaignId.toString();
        @SuppressWarnings("unchecked")
        String query_campaign_id_click_val_str = (String)featureCacheClient.get(query_campaign_id_click_key);
        Double query_campaign_id_click_val = 0.0;
        if (query_campaign_id_click_val_str != null && !Objects.equals(query_campaign_id_click_val_str, "")) {
            query_campaign_id_click_val = Double.parseDouble(query_campaign_id_click_val_str);
        }
        features.add(query_campaign_id_click_val);

        // query_campaign_id_impression
        String query_campaign_id_impression_key = "qcidi_" + query + "_" + ad.campaignId.toString();
        @SuppressWarnings("unchecked")
        String query_campaign_id_impression_val_str = (String)featureCacheClient.get(query_campaign_id_impression_key);
        Double query_campaign_id_impression_val = 0.0;
        if (query_campaign_id_impression_val_str != null && !Objects.equals(query_campaign_id_impression_val_str, "")) {
            query_campaign_id_impression_val = Double.parseDouble(query_campaign_id_impression_val_str);
        }
        features.add(query_campaign_id_impression_val);

        // query_ad_id_click
        String query_ad_id_click_key = "qaidc_" + query + "_" + ad.adId.toString();
        @SuppressWarnings("unchecked")
        String query_ad_id_click_val_str = (String)featureCacheClient.get(query_ad_id_click_key);
        Double query_ad_id_click_val = 0.0;
        if (query_ad_id_click_val_str != null && !Objects.equals(query_ad_id_click_val_str, "")) {
            query_ad_id_click_val = Double.parseDouble(query_ad_id_click_val_str);
        }
        features.add(query_ad_id_click_val);

        // query_ad_id_impression
        String query_ad_id_impression_key = "qaidi_" + query + "_" + ad.adId.toString();
        @SuppressWarnings("unchecked")
        String query_ad_id_impression_val_str = (String)featureCacheClient.get(query_ad_id_impression_key);
        Double query_ad_id_impression_val = 0.0;
        if (query_ad_id_impression_val_str != null && !Objects.equals(query_ad_id_impression_val_str, "")) {
            query_ad_id_impression_val = Double.parseDouble(query_ad_id_impression_val_str);
        }
        features.add(query_ad_id_impression_val);

        // query_ad_category_match scale to 1000000 if match
        double query_ad_category_match = 0.0;
        if(Objects.equals(query_category, ad.category)) {
            query_ad_category_match = 1000000.0;
        }
        features.add(query_ad_category_match);

        ad.pClick = CTRModel
                .getInstance(m_logistic_reg_model_file, m_gbdt_model_path)
                .predictCTRWithLogisticRegression(features);
        // System.out.println("ad.pClick = " + ad.pClick);
    }
}
