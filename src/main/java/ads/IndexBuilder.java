package ads;

import ads.models.Ad;
import ads.models.Campaign;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


// indexBuilder -> inverted index & forward index
public class IndexBuilder {
    private int EXP = 0; // 0: never expire
    private String mMemcachedServer;
    private int mMemcachedPortal;
    private String mysql_host;
    private String mysql_db;
    private String mysql_user;
    private String mysql_pass;
    private MemcachedClient cache;

    public IndexBuilder(String memcachedServer,int memcachedPortal,String mysqlHost,String mysqlDb,String user,String pass) {
        mMemcachedServer = memcachedServer;
        mMemcachedPortal = memcachedPortal;
        mysql_host = mysqlHost;
        mysql_db = mysqlDb;
        mysql_user = user;
        mysql_pass = pass;
        String address = mMemcachedServer + ":" + mMemcachedPortal;
        try {
            cache = new MemcachedClient(new ConnectionFactoryBuilder()
                                            .setDaemon(true)
                                            .setFailureMode(FailureMode.Retry)
                                            .build(), AddrUtil.getAddresses(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getRandPrice() {
        Random r = new Random();
        int Low = 10;
        int High = 100;
        return r.nextInt(High - Low) + Low;
    }

    // key: term
    // value: list of ad id
    public Boolean buildInvertIndex(Ad ad) {
        String keyWords = Utility.strJoin(ad.keyWords, ",");
        List<String> tokens = Utility.cleanedTokenize(keyWords);

        for (String key : tokens) {
            if (cache.get(key) instanceof Set) {
                @SuppressWarnings("unchecked")
                Set<Long> adIdList = (Set<Long>) cache.get(key);
                adIdList.add(ad.adId);
                cache.set(key, EXP, adIdList);
            } else {
                Set<Long> adIdList = new HashSet<>();
                adIdList.add(ad.adId);
                cache.set(key, EXP, adIdList);
            }
        }
        return true;
    }

    // save the detail of ad
    public Boolean buildForwardIndex(Ad ad) {
        try {
            MySQLAccess mysql = new MySQLAccess(mysql_host, mysql_db, mysql_user, mysql_pass);
            if(ad.price == 0) {
                ad.price = getRandPrice();
            }
            mysql.addAdData(ad);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean updateBudget(Campaign camp) {
        try {
            MySQLAccess mysql = new MySQLAccess(mysql_host, mysql_db, mysql_user, mysql_pass);
            mysql.addCampaignData(camp);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
