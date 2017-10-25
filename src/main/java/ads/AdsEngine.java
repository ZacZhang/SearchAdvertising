package ads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import ads.models.Ad;
import ads.models.Campaign;
import org.json.*;

import adindex.AdsIndexClientWorker;
import adindex.AdsSelectionResult;

public class AdsEngine {
    private String mAdsDataFilePath;
    private String mBudgetFilePath;
    private String m_logistic_reg_model_file;
    private String m_gbdt_model_path;
    private IndexBuilder indexBuilder;
    private String mMemcachedServer;
    private int mMemcachedPortal;
    private int mFeatureMemcachedPortal;
    private int mSynonymsMemcachedPortal;
    private int mTFMemcachedPortal;
    private int mDFMemcachedPortal;
    private String mysql_host;
    private String mysql_db;
    private String mysql_user;
    private String mysql_pass;
    private Boolean enable_query_rewrite;
    private int indexServerTimeout; //ms

    public AdsEngine(String adsDataFilePath, String budgetDataFilePath, String logistic_reg_model_file,
                     String gbdt_model_path, String memcachedServer, int memcachedPortal, int featureMemcachedPortal,
                     int synonymsMemcachedPortal, int tfMemcachedPortal, int dfMemcachedPortal,
                     String mysqlHost, String mysqlDb, String user, String pass) {
        mAdsDataFilePath = adsDataFilePath;
        mBudgetFilePath = budgetDataFilePath;
        m_logistic_reg_model_file = logistic_reg_model_file;
        m_gbdt_model_path = gbdt_model_path;
        mMemcachedServer = memcachedServer;
        mMemcachedPortal = memcachedPortal;
        mTFMemcachedPortal = tfMemcachedPortal;
        mDFMemcachedPortal = dfMemcachedPortal;
        mFeatureMemcachedPortal = featureMemcachedPortal;
        mSynonymsMemcachedPortal = synonymsMemcachedPortal;
        mysql_host = mysqlHost;
        mysql_db = mysqlDb;
        mysql_user = user;
        mysql_pass = pass;

        //enable_query_rewrite = false;
        enable_query_rewrite = true;

        indexServerTimeout = 1000;
        indexBuilder = new IndexBuilder(memcachedServer, memcachedPortal, mysql_host, mysql_db, mysql_user, mysql_pass);
    }

    public Boolean init() {
        //load ads data
        try (BufferedReader brAd = new BufferedReader(new FileReader(mAdsDataFilePath))) {
            String line;
            while ((line = brAd.readLine()) != null) {
                JSONObject adJson = new JSONObject(line);
                Ad ad = new Ad();
                if(adJson.isNull("adId") || adJson.isNull("campaignId")) {
                    continue;
                }
                ad.adId = adJson.getLong("adId");
                ad.campaignId = adJson.getLong("campaignId");
                ad.brand = adJson.isNull("brand") ? "" : adJson.getString("brand");
                ad.price = adJson.isNull("price") ? 100.0 : adJson.getDouble("price");
                ad.thumbnail = adJson.isNull("thumbnail") ? "" : adJson.getString("thumbnail");
                ad.title = adJson.isNull("title") ? "" : adJson.getString("title");
                ad.detail_url = adJson.isNull("detail_url") ? "" : adJson.getString("detail_url");
                ad.bidPrice = adJson.isNull("bidPrice") ? 1.0 : adJson.getDouble("bidPrice");
                ad.pClick = adJson.isNull("pClick") ? 0.0 : adJson.getDouble("pClick");
                ad.category =  adJson.isNull("category") ? "" : adJson.getString("category");
                ad.description = adJson.isNull("description") ? "" : adJson.getString("description");
                ad.keyWords = new ArrayList<>();
                JSONArray keyWords = adJson.isNull("keyWords") ? null : adJson.getJSONArray("keyWords");
                for(int j = 0; j < (keyWords != null ? keyWords.length() : 0); j++) {
                    ad.keyWords.add(keyWords.getString(j));
                }
//				if(!indexBuilder.buildInvertIndex(ad)) {
//
//				}

				if(!indexBuilder.buildInvertIndex(ad) || !indexBuilder.buildForwardIndex(ad))
				{
					//log
				}

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // load budget data
        try (BufferedReader brBudget = new BufferedReader(new FileReader(mBudgetFilePath))) {
            String line;
            while ((line = brBudget.readLine()) != null) {
                JSONObject campaignJson = new JSONObject(line);
                Long campaignId = campaignJson.getLong("campaignId");
                double budget = campaignJson.getDouble("budget");
                Campaign camp = new Campaign();
                camp.campaignId = campaignId;
                camp.budget = budget;
                if(!indexBuilder.updateBudget(camp)) {
                    //log
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Ad CloneAd(adindex.Ad ad){
        Ad result = new Ad();
        result.adId = ad.getAdId();
        result.campaignId = ad.getCampaignId();
        int keyWordsSize = ad.getKeyWordsList().size();
        result.keyWords = ad.getKeyWordsList().subList(0, keyWordsSize);
        result.relevanceScore = ad.getRankScore();
        result.pClick = ad.getPClick();
        result.bidPrice = ad.getBidPrice();
        result.rankScore = ad.getRankScore();
        result.qualityScore = ad.getQualityScore();
        result.costPerClick = ad.getCostPerClick();
        result.position = ad.getPosition();
        result.title = ad.getTitle();
        result.price = ad.getPrice();
        result.thumbnail = ad.getThumbnail();
        result.description = ad.getDescription();
        result.brand = ad.getBrand();
        result.detail_url = ad.getDetailUrl();
        result.query = ad.getQuery();
        result.category = ad.getCategory();
        return result;
    }

    private AdsSelectionResult getAdsFromIndexServer(List<String> queryTerms) {
        AdsSelectionResult adsResult = new AdsSelectionResult();
        adindex.Query.Builder _query =  adindex.Query.newBuilder();
        for (String queryTerm : queryTerms) {
            System.out.println("term = " + queryTerm);
            _query.addTerm(queryTerm);
        }
        System.out.println("term count= " + _query.getTermCount());
        java.util.List<adindex.Query> queryList = new ArrayList<>();
        queryList.add(_query.build());

        // design choice
        // #1 sequentially call index server 1, 2, 3... 串行的往两个index server取数据
        // #2 parallel call index server 1, 2, 3... 并行的往两个index server取数据
        // 选择是并行，生成两个client，client1跟50051的server取数据，client2跟50052的server取数据，
        AdsIndexClientWorker adsIndexClient1 = new AdsIndexClientWorker(queryList, "127.0.0.1",
                                                                        50051,adsResult);
        AdsIndexClientWorker adsIndexClient2 = new AdsIndexClientWorker(queryList, "127.0.0.1",
                                                                        50052,adsResult);
        adsIndexClient1.start();
        adsIndexClient2.start();

        try {
            // client最多等server 1s
            adsIndexClient1.join(indexServerTimeout);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        try {
            adsIndexClient2.join(indexServerTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adsResult;
    }


    public List<Ad> selectAds(String query, String device_id, String device_ip, String query_category)
    {
        List<Ad> adsCandidates = new ArrayList<>();
        if (enable_query_rewrite) {
            // list of rewritten term
            List<List<String>> rewrittenQuery =  QueryParser.getInstance().QueryRewrite(query, mMemcachedServer,
                    mSynonymsMemcachedPortal);

            //select ads candidates 海选
            for (List<String> queryTerms : rewrittenQuery) {
                List<Ad> adsCandidates_temp = AdsSelector.getInstance(mMemcachedServer, mMemcachedPortal,
                        mFeatureMemcachedPortal, mTFMemcachedPortal, mDFMemcachedPortal, m_logistic_reg_model_file,
                        m_gbdt_model_path, mysql_host, mysql_db, mysql_user, mysql_pass)
                        .selectAds(queryTerms,device_id, device_ip, query_category);
                // 去掉重复的广告
                Set<Long> uniqueAds = new HashSet<>();
                for(Ad ad : adsCandidates_temp) {
                    if (!uniqueAds.contains(ad.adId)) {
                        adsCandidates.add(ad);
                    }
                }
            }
            //TODO: give ads selected by rewritten query lower rank score

        } else {
            // 用rpc的方式去调用request，从index server上调用ads
            List<String> queryTerms = QueryParser.getInstance().QueryUnderstand(query);
            AdsSelectionResult adsResult = getAdsFromIndexServer(queryTerms);
            System.out.println("Number of  ads from index server = " + adsResult.getAdsList().size());
            //convert ads
            for(adindex.Ad _ad : adsResult.getAdsList()) {
                System.out.println("relevance score = " + _ad.getRelevanceScore());
                Ad ad = new Ad();
                ad.CloneAd(_ad);
                System.out.println("relevance score = " + ad.relevanceScore);
                adsCandidates.add(ad);
            }
            System.out.println("Number of adsCandidates = " + adsCandidates.size());

            //adsCandidates = AdsSelector.getInstance(mMemcachedServer, mMemcachedPortal,mFeatureMemcachedPortal,mTFMemcachedPortal, mDFMemcachedPortal,
            //m_logistic_reg_model_file,m_gbdt_model_path, mysql_host, mysql_db,mysql_user, mysql_pass).selectAds(queryTerms,device_id, device_ip, query_category);
        }

        // TODO : L0 filter by pClick, relevance score 根据pClick和relevance过滤广告 (这步可以移到index server)
        List<Ad> L0unfilteredAds = AdsFilter.getInstance().LevelZeroFilterAds(adsCandidates);
        System.out.println("L0unfilteredAds ads left = " + L0unfilteredAds.size());

        // rank 根据quality score对候选广告进行排序
        List<Ad> rankedAds = AdsRanker.getInstance().rankAds(adsCandidates);
        System.out.println("rankedAds ads left = " + rankedAds.size());

        //L1 filter by relevance score : select top K ads
        int k = 20;
        List<Ad> unfilteredAds = AdsFilter.getInstance().LevelOneFilterAds(rankedAds,k);
        System.out.println("unfilteredAds ads left = " + unfilteredAds.size());

        // Dedupe ads per campaign (每个campaign最多返回一个ad，为了多样性，不能每次返回的广告都是属于一个campaign的)
        List<Ad> dedupedAds = AdsCampaignManager.getInstance(mysql_host, mysql_db,mysql_user, mysql_pass)
                                .DedupeByCampaignId(unfilteredAds);
        System.out.println("dedupedAds ads left = " + dedupedAds.size());

        // pricing： next rank score/current score * current bid price 定价
        AdPricing.getInstance().setCostPerClick(dedupedAds);

        // filter last one, ad without budget, ads with CPC < minReservePrice (开始算钱，budget该扣的扣，budget用光了的ads就踢掉)
        List<Ad> ads = AdsCampaignManager.getInstance(mysql_host, mysql_db,mysql_user, mysql_pass).ApplyBudget(dedupedAds);
        System.out.println("AdsCampaignManager ads left = " + ads.size());

        // allocation (给广告分配位置)
        AdsAllocation.getInstance().AllocateAds(ads);
        return ads;
    }
}
