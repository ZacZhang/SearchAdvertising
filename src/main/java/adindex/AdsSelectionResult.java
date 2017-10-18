package adindex;

import java.util.List;

// 搜集从各个index server上返回的广告
public class AdsSelectionResult {
    private java.util.List<adindex.Ad> adsList;

    public AdsSelectionResult() {
        adsList = new java.util.ArrayList<>();
    }

    // synchronized保证每次只有一个thread在调用add function，相当于lock的作用
    public synchronized void add(java.util.List<adindex.Ad> _adsList){
        for(adindex.Ad ad : _adsList) {
            adsList.add(ad);
        }
    }

    // synchronized保证每次只有一个thread在调用getAdsList function，相当于lock的作用, 保证读的时候没有其他thread正在写
    public synchronized java.util.List<adindex.Ad> getAdsList(){
        return adsList;
    }
}
