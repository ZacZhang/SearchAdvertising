package adindex;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class AdsIndexClientWorker extends Thread{
    private static final Logger logger = Logger.getLogger(AdsIndexClientWorker.class.getName());
    protected AdsSelectionResult result = null;
    private String adsIndexServer;
    private int adsIndexServerPortal;
    private List<adindex.Query> queryList;

    public AdsIndexClientWorker(List<adindex.Query> queryList, String adsIndexServer, int adsIndexServerPortal,
                                AdsSelectionResult result) {
        this.result = result;
        this.queryList = queryList;
        this.adsIndexServer = adsIndexServer;
        this.adsIndexServerPortal = adsIndexServerPortal;
    }

    public void start()   {
        adindex.AdsIndexClient adsIndexClient = new adindex.AdsIndexClient(adsIndexServer,adsIndexServerPortal);
        //TODO : timeout and return partial list of ads 可以在这里设置timeout，时间到了如果没有得到全部ads，就先返会部分ads
        // 当前index server取回来的ad list
        List<adindex.Ad> adsList = adsIndexClient.GetAds(queryList);
        result.add(adsList);
    }
}
