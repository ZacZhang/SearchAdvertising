package ads;

import java.util.List;

public class AdPricing {
    private static AdPricing instance = null;
    protected AdPricing() {

    }

    public static AdPricing getInstance() {
        if(instance == null) {
            instance = new AdPricing();
        }
        return instance;
    }

    public void setCostPerClick(List<Ad> adsCandidates) {
        for(int i = 0; i < adsCandidates.size();i++) {
            if(i < adsCandidates.size() - 1) {
                adsCandidates.get(i).costPerClick = adsCandidates.get(i + 1).rankScore / adsCandidates.get(i).qualityScore + 0.01;
            } else {
                // 最后一个广告就算bidPrice, 最后一个广告不会显示出来
                adsCandidates.get(i).costPerClick = adsCandidates.get(i).bidPrice;
            }
        }
    }
}
