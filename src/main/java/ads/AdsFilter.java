package ads;

import java.util.ArrayList;
import java.util.List;

public class AdsFilter {
    private static AdsFilter instance = null;
    private static double pClickThreshold = 0.0;
    private static double relevanceScoreThreshold = 0.01;
    private static int mimNumOfAds = 4;

    protected AdsFilter() {

    }

    public static AdsFilter getInstance() {
        if(instance == null) {
            instance = new AdsFilter();
        }
        return instance;
    }

    public List<Ad> LevelZeroFilterAds(List<Ad> adsCandidates) {
        // 如果candidate个数少于mimNumOfAds，则不过滤，直接返回
        if(adsCandidates.size() <= mimNumOfAds) {
            return adsCandidates;
        }

        List<Ad> unfilteredAds = new ArrayList<>();
        for(Ad ad : adsCandidates) {
            if(ad.pClick >= pClickThreshold && ad.relevanceScore > relevanceScoreThreshold) {
                unfilteredAds.add(ad);
            }
        }
        return unfilteredAds;
    }

    // get top k
    public List<Ad> LevelOneFilterAds(List<Ad> adsCandidates,int k) {
        if(adsCandidates.size() <= mimNumOfAds)
            return adsCandidates;

        List<Ad> unfilteredAds = new ArrayList<>();
        for(int i = 0; i < Math.min(k, adsCandidates.size());i++) {
            unfilteredAds.add(adsCandidates.get(i));
        }
        return unfilteredAds;
    }
}
