package ads;

import ads.models.Ad;
import ads.utils.MySQLAccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AdsCampaignManager {
    private static AdsCampaignManager instance = null;
    private String mysql_host;
    private String mysql_db;
    private String mysql_user;
    private String mysql_pass;
    private static double minPriceThreshold = 0.1;

    protected AdsCampaignManager(String mysqlHost,String mysqlDb,String user,String pass) {
        mysql_host = mysqlHost;
        mysql_db = mysqlDb;
        mysql_user = user;
        mysql_pass = pass;
    }

    public static AdsCampaignManager getInstance(String mysqlHost,String mysqlDb,String user,String pass) {
        if(instance == null) {
            instance = new AdsCampaignManager(mysqlHost,mysqlDb,user,pass);
        }
        return instance;
    }

    // 每个campaign最多返回一个ad，为了多样性，不能每次返回的广告都是属于一个campaign的
    public  List<Ad> DedupeByCampaignId(List<Ad> adsCandidates) {
        List<Ad> dedupedAds = new ArrayList<>();
        HashSet<Long> campaignIdSet = new HashSet<>();
        for(Ad ad : adsCandidates) {
            if(!campaignIdSet.contains(ad.campaignId)) {
                dedupedAds.add(ad);
                campaignIdSet.add(ad.campaignId);
            }
        }
        return dedupedAds;
    }

    public List<Ad> ApplyBudget(List<Ad> adsCandidates) {
        List<Ad> ads = new ArrayList<>();
        try {
            MySQLAccess mysql = new MySQLAccess(mysql_host, mysql_db, mysql_user, mysql_pass);
            // 注意最后一个广告不会显示
            for(int i = 0; i < adsCandidates.size() - 1; i++) {
                Ad ad = adsCandidates.get(i);
                Long campaignId = ad.campaignId;
                System.out.println("campaignId: " + campaignId);
                Double budget = mysql.getBudget(campaignId);
                System.out.println("AdsCampaignManager ad.costPerClick= " + ad.costPerClick);
                System.out.println("AdsCampaignManager campaignId= " + campaignId);
                System.out.println("AdsCampaignManager budget left = " + budget);
                //design choice (如果有多个web server同时工作，则需要使用数据库的transaction，或者offline处理)
                //#1 transaction (qps小的时候)
                //#2 offline process (qps大的时候)
                //ApplyBudget log: tractionId(sessionID), campaignId, ad.costPerClick
                //1.set budget buffer and alert on 10% of budget left on campaignId 9999 -> offline process campaignId 9999
                //2.reduce offline process frequency

                // 扣钱
                if(ad.costPerClick <= budget && ad.costPerClick >= minPriceThreshold) {
                    ads.add(ad);
                    budget = budget - ad.costPerClick;
                    mysql.updateCampaignData(campaignId, budget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ads;
    }

}
